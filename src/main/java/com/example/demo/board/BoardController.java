package com.example.demo.board;

import com.example.demo.file.FileDTO;
import com.example.demo.file.FileUploadService;
import com.example.demo.file.FileUploadUtil;
import com.example.demo.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    private final FileUploadService fileUploadService;

    private final BoardValidator boardValidator;

    private final FileUploadUtil fileUploadUtil;

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);


    @GetMapping("board")
    public String board(@RequestParam(name = "kind",required = true) String kind, @RequestParam(name = "page",
            defaultValue = "1",required = false) int page, Model model){

        String paramKind = kind;

        int totalPage = 5;

        int prePage = page-1 < 1 ? 1 : page-1;

        int nextPage = page + 1 > totalPage  ? totalPage : page+1;

        if(page <1){
            page = 1;
        }
        kind = Kind.getBoardKind(kind);

        List<BoardDTO> boardList = boardService.getBoard(kind,page);

        model.addAttribute("boardList" , boardList);

        model.addAttribute("totalPages",5);

        model.addAttribute("kind", paramKind);

        model.addAttribute("prePage",prePage);

        model.addAttribute("nextPage",nextPage);

        model.addAttribute("korKind",Kind.getKorBoardKind(paramKind));

        return "board";

    }

    @Getter
    enum Kind{
            NOTICE("공지사항","NOTICE");

            private final String kor;
            private final String eng;

            Kind(String kor, String eng) {
            this.kor =kor;
            this.eng =eng;
        }
        private static String getBoardKind(String kind){
            switch (kind){
                case "notice" : return Kind.NOTICE.getEng();
            }
            return "error";
        }
        private static String getKorBoardKind(String kind){
            switch (kind){
                case "notice" : return Kind.NOTICE.getKor();
            }
            return "error";
        };

    }

    @GetMapping("board/create")
    public String createPage(@RequestParam("kind") String kind,Model model){

        String kindResult =  Kind.getBoardKind(kind);

        if(kindResult.equals("error")){
            return "error";
        }

        model.addAttribute("kind", kind);

        model.addAttribute("korKind",Kind.getKorBoardKind(kind));

        return "create";
    }

    @PostMapping("board/create")
    public String create(@RequestParam("kind") String kind, @ModelAttribute BoardCreateRequest param, Authentication authentication,Model model){
        String userId = getUserId(authentication);

        param.setId(userId);
        if(!boardValidator.attachmentCheck(param)){
            model.addAttribute("status" , 400);
            model.addAttribute("message", "업로드한 파일에 문제가 있습니다.");
            return "error";
        }

        boardService.createBoard(param,Kind.getBoardKind(kind));

        return "redirect:/board?kind="+kind;
    }


    @PostMapping("board/file")
    @ResponseBody
    public ResponseEntity<Map<String,String>> boardFileUpload (@RequestParam("file") MultipartFile multipartFile){

        HashMap<String, String> fileData = new HashMap<>();

        String saveFileName = fileUploadService.fileUpload(multipartFile);

        fileData.put("orgFileName",multipartFile.getOriginalFilename());

        fileData.put("saveFileName",saveFileName);

        return ResponseEntity.ok().body(fileData);

    }

    @GetMapping("board/{number}")
    public String boardDetail(@PathVariable("number") int number, @RequestParam("kind") String kind, Model model, HttpSession session){


        Optional<List<DetailDTO>> optionalDetailDTOS  = boardService.boardDetail(number, kind, Optional.of(session));
        if(optionalDetailDTOS.isEmpty()){
            model.addAttribute("status",404);
            model.addAttribute("message","잘못된 요청입니다.");
            return "error";
        }

        List<DetailDTO> detailDTOS = optionalDetailDTOS.get();

        detailDTOS.forEach(detailDTO -> {
            if(detailDTO instanceof BoardDTO){
                model.addAttribute("board",detailDTO);
            } else if (detailDTO instanceof FileDTO) {
                model.addAttribute("file",detailDTO);
            }
        });
        model.addAttribute("kind" , kind);

        return "detail";
    }

    @DeleteMapping("board/{number}")
    @ResponseBody
    public ResponseEntity<String> boardDelete(@PathVariable("number") int number,@RequestParam("kind") String kind,Authentication authentication){
        String userId = getUserId(authentication);

        if(userId == null || !boardValidator.boardUserCheck(number,kind,userId)){
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }

        boardService.boardDelete(number,kind,userId);

        return ResponseEntity.ok("success");
    }

    @GetMapping("board/update/{number}")
    public String boardUpdate(@PathVariable("number") int number, @RequestParam("kind") String kind,Authentication authentication,Model model){
        String userId = getUserId(authentication);

        if(!boardValidator.boardUserCheck(number, kind, userId)){
            return "auth";
        }

        Optional<List<DetailDTO>> optionalDetailDTOS  = boardService.boardDetail(number, kind, Optional.empty());
        if(optionalDetailDTOS.isEmpty()){
            model.addAttribute("status",404);
            model.addAttribute("message","잘못된 요청입니다.");
            return "error";
        }

        List<DetailDTO> detailDTOS = optionalDetailDTOS.get();

        detailDTOS.forEach(detailDTO -> {
            if(detailDTO instanceof BoardDTO){
                model.addAttribute("board",detailDTO);
            } else if (detailDTO instanceof FileDTO) {
                model.addAttribute("file",detailDTO);
            }
        });


        if(model.getAttribute("file") == null){
            FileDTO fileDTO  = new FileDTO();
            fileDTO.setFileName("");
            fileDTO.setOrgFileName("");
            model.addAttribute("file", fileDTO);
        }

        model.addAttribute("kind",kind);

        return "update";
    }

    private static String getUserId(Authentication authentication) {
        String userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                userId = ((CustomUserDetails) principal).getUsername();  // 사용자 ID 혹은 이름 반환
            }
    }
        return userId;
    }

    @PutMapping("board/update/{number}")
    @ResponseBody
    public ResponseEntity<String> boardUpdate(@PathVariable("number") int number,@RequestParam("kind") String kind, @RequestBody BoardCreateRequest param, Authentication authentication){

        String userId = getUserId(authentication);
        if(!param.checkDeleteFile() || !boardValidator.attachmentCheck(param) ){
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

        }
        if(userId == null || !boardValidator.boardUserCheck(number,kind,userId)){
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }

        boardService.boardUpdate(number,kind,userId,param);

        return ResponseEntity.ok("success");
    }

    @ResponseBody

    @GetMapping("board/file/{number}")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("number") int fileCode) throws FileNotFoundException {

        FileDTO fileDTO = fileUploadService.fileDetail(fileCode);

        File file = new File(fileUploadUtil.getUploadPath() + File.separator + fileDTO.getFileName());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getOrgFileName()+ "\"")
                .contentLength(file.length())
                .body(resource);
    }

}
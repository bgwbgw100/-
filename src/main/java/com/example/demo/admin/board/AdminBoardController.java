package com.example.demo.admin.board;
import com.example.demo.board.BoardDTO;
import com.example.demo.menu.MenuDTO;
import com.example.demo.util.CustomMultiReturn;
import com.example.demo.util.CustomTwoReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/management/")
@RequiredArgsConstructor
public class AdminBoardController {

    private final AdminBoardService adminBoardService;

    @GetMapping("board")
    public String board(Model model, @RequestParam(name = "page",required = false,defaultValue = "1") int page,
                        @RequestParam(value = "kind",required = false) String kind){

        if(page <= 0){
            page = 1;
        }

        Optional<CustomMultiReturn<List<MenuDTO>, List<BoardDTO>, Integer, Optional, Optional>> optional = adminBoardService.getBoardMenuList(page,Optional.ofNullable(kind));

        if(optional.isEmpty()){
            model.addAttribute("menuList", Collections.emptyList());

            model.addAttribute("boardList", Collections.emptyList());

            return "admin/management/board";
        };
        CustomMultiReturn<List<MenuDTO>, List<BoardDTO>, Integer, Optional, Optional> multiReturn = optional.get();

        List<MenuDTO> menuDTOS = multiReturn.getA();

        List<BoardDTO> boardDTOS = multiReturn.getB();

        int count = multiReturn.getC();

        int totalPage = count/10 +1;

        model.addAttribute("menuList", menuDTOS);

        model.addAttribute("boardList", boardDTOS);

        model.addAttribute("totalPage",totalPage);

        model.addAttribute("page",page);

        model.addAttribute("kind",kind == null ? "" : kind);

        return "admin/management/board";
    }

    @PutMapping("board")
    @ResponseBody
    public ResponseEntity<String> blind(@RequestBody BoardDTO boardDTO){

        adminBoardService.blind(boardDTO);

        return ResponseEntity.ok("success");
    }




}

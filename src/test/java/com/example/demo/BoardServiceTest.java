package com.example.demo;

import com.example.demo.board.BoardCreateRequest;
import com.example.demo.board.BoardDTO;
import com.example.demo.board.BoardMapper;
import com.example.demo.board.BoardValidator;
import com.example.demo.file.FileDTO;
import com.example.demo.file.FileMapper;
import com.example.demo.file.FileUploadService;
import com.example.demo.file.FileUploadUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private BoardValidator boardValidator;

    @Autowired
    private FileMapper fileMapper;

    final static int NULL_NUMBER = 0;


    public List<BoardDTO> getBoard(String kind, int page){
        page = (page-1) *10;


        List<BoardDTO> boardList = boardMapper.getBoardList(kind,page);

        return boardList;
    }

    @Test
    public void testGetBoard(){
        List<BoardDTO> boardList = getBoard("NOTICE",1);
        assertThat(boardList.size()).isGreaterThan(0);
    }


    @Test
    @Rollback
    @Transactional
    void createBoard(){
        String kind = "test";
        String saveFileName = "testFile";
        String path = fileUploadUtil.getUploadPath();

        BoardCreateRequest param = new BoardCreateRequest();
        param.setTitle("test");
        param.setContent("test");
        param.setOrgFileName(saveFileName);
        param.setSaveFileName("save"+saveFileName);
        param.setAttachmentOx("O");
        param.setTitle("test");
        param.setContent("test");

        createTestFile(path+File.separator+saveFileName);

        Optional<String> attachmentOxOptional = Optional.ofNullable(param.getAttachmentOx());
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId("admin");
        boardDTO.setBoardKind(kind);
        boardDTO.setContent(param.getContent());
        boardDTO.setAttachmentOx(attachmentOxOptional.isPresent() ? attachmentOxOptional.get().toUpperCase() : null);
        boardDTO.setTitle(param.getTitle());
        boardDTO.setBoardNumber(1);
        //db 파일정보 저장
        if(boardDTO.getAttachmentOx() != null && boardDTO.getAttachmentOx().equalsIgnoreCase("O")){
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(param.getSaveFileName());
            fileDTO.setOrgFileName(param.getOrgFileName());
            fileUploadService.dbFileUpload(fileDTO);
            boardDTO.setAttachmentCode(fileDTO.getAttachmentCode());
        }
        boardMapper.createBoard(boardDTO);

        BoardDTO detail = boardMapper.detailBoard(boardDTO);

        assertThat(detail.getId()).isEqualTo(boardDTO.getId());
        assertThat(detail.getBoardKind()).isEqualTo(boardDTO.getBoardKind());
        assertThat(detail.getContent()).isEqualTo(boardDTO.getContent());
        assertThat(detail.getAttachmentOx()).isEqualTo(boardDTO.getAttachmentOx());
        assertThat(detail.getTitle()).isEqualTo(boardDTO.getTitle());
        assertThat(detail.getAttachmentCode()).isEqualTo(boardDTO.getAttachmentCode());
        assertThat(detail.getBoardNumber()).isEqualTo(boardDTO.getBoardNumber());

    }

    void createTestFile(String fileName){
        String content = "test";
        File file = new File(fileName);

        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Rollback
    @Transactional
    void boardDetail(){
        String kind = "detailTest";
        String id = "1";

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId("admin");
        boardDTO.setBoardKind(kind);
        boardDTO.setContent("detailTest");
        boardDTO.setAttachmentOx("X");
        boardDTO.setTitle("detailTest");
        boardMapper.createBoard(boardDTO);


        boardDTO.setBoardNumber(Integer.parseInt(id));
        BoardDTO detail = boardMapper.detailBoard(boardDTO);

        assertThat(detail.getBoardKind()).isEqualTo(kind);
        assertThat(detail.getBoardNumber()).isEqualTo(Integer.parseInt(id));
        assertThat(detail.getContent()).isEqualTo(boardDTO.getContent());

    }

    @Test
    @Transactional
    @Rollback
    void boardDelete(){
        String realUser = "admin";
        String fakeUser = "fake";
        String kind = "detailTest";
        int number = 1;

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(realUser);
        boardDTO.setBoardKind(kind);
        boardDTO.setContent("detailTest");
        boardDTO.setAttachmentOx("X");
        boardDTO.setTitle("detailTest");
        boardDTO.setBoardNumber(number);
        boardMapper.createBoard(boardDTO);

        assertThat(boardValidator.boardUserCheck(number,kind,fakeUser)).isFalse();
        assertThat(boardValidator.boardUserCheck(number,kind,realUser)).isTrue();

        BoardDTO detail = boardMapper.detailBoard(boardDTO);//파일 삭제용 detail

        boardDTO.setId(fakeUser);
        boardMapper.deleteBoard(boardDTO);
        BoardDTO realDeleteBefore = boardMapper.detailBoard(boardDTO);

        boardDTO.setId(realUser);
        boardMapper.deleteBoard(boardDTO);
        BoardDTO realDeleteAfter = boardMapper.detailBoard(boardDTO);




        assertThat(realDeleteBefore).isNotNull();
        assertThat(realDeleteAfter).isNull();

    }

    @Test
    @Transactional
    @Rollback
    void boardFileDelete(){

        String saveFileName = "testFile";

        String path = fileUploadUtil.getUploadPath();
        createTestFile(path+File.separator+saveFileName);
        String kind = "deleteFile";

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId("admin");
        boardDTO.setBoardKind(kind);
        boardDTO.setContent("Test");
        boardDTO.setAttachmentOx("O");
        boardDTO.setTitle("test");
        boardDTO.setBoardNumber(1);

        if(boardDTO.getAttachmentOx() != null && boardDTO.getAttachmentOx().equalsIgnoreCase("O")){
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName("saveTestFile");
            fileDTO.setOrgFileName("saveOrgTestFile");
            fileUploadService.dbFileUpload(fileDTO);
            boardDTO.setAttachmentCode(fileDTO.getAttachmentCode());
        }

        boardMapper.createBoard(boardDTO);
        BoardDTO detail = boardMapper.detailBoard(boardDTO);

        boardMapper.deleteBoard(boardDTO);
        BoardDTO delete = boardMapper.detailBoard(boardDTO);

        int code = detail.getAttachmentCode();
        FileDTO file = fileMapper.fileSelect(code);

        fileMapper.deleteFile(code);
        FileDTO fileDTO = fileMapper.fileSelect(code);

        assertThat(detail).isNotNull();
        assertThat(file).isNotNull();
        assertThat(fileDTO).isNull();
        assertThat(delete).isNull();

    }

    @Test
    @Transactional
    @Rollback
    void updateBoard(){
        createBoard();

        int number = 1;
        String kind = "test";
        BoardCreateRequest param;

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);

        BoardDTO boardDetail = boardMapper.detailBoard(boardDTO);
        FileDTO boardFile = fileMapper.fileSelect(boardDetail.getAttachmentCode());
        BoardDTO updateBoard = new BoardDTO();
        updateBoard.setId(boardDetail.getId());
        updateBoard.setBoardNumber(number);
        updateBoard.setBoardKind(kind);
        updateBoard.setTitle("testUpdate");
        updateBoard.setContent("testUpdate");
        updateBoard.setAttachmentOx("X");
        updateBoard.setAttachmentCode(NULL_NUMBER);

        boardMapper.updateBoard(updateBoard);
        BoardDTO updateBoardDetail = boardMapper.detailBoard(boardDTO);

        fileMapper.deleteFile(boardDetail.getAttachmentCode());
        FileDTO deleteFile = fileMapper.fileSelect(boardDetail.getAttachmentCode());

        assertThat(deleteFile).isNull();
        assertThat(boardDetail.getBoardNumber()).isEqualTo(updateBoardDetail.getBoardNumber());
        assertThat(boardDetail.getBoardKind()).isEqualTo(updateBoardDetail.getBoardKind());
        assertThat(boardDetail.getContent()).isNotEqualTo(updateBoardDetail.getContent());
        assertThat(boardDetail.getTitle()).isNotEqualTo(updateBoardDetail.getTitle());
        assertThat(updateBoardDetail.getAttachmentOx()).isEqualTo("X");
        assertThat(updateBoardDetail.getAttachmentCode()).isEqualTo(NULL_NUMBER);


    }


    @Test
    @Transactional
    @Rollback
    void updateFileBoard(){

        createBoard();

        int number = 1;
        String kind = "test";
        BoardCreateRequest param;


        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);

        BoardDTO boardDetail = boardMapper.detailBoard(boardDTO);
        FileDTO boardFile = fileMapper.fileSelect(boardDetail.getAttachmentCode());
        BoardDTO updateBoard = new BoardDTO();
        updateBoard.setId(boardDetail.getId());
        updateBoard.setBoardNumber(number);
        updateBoard.setBoardKind(kind);
        updateBoard.setTitle("testUpdate");
        updateBoard.setContent("testUpdate");
        updateBoard.setAttachmentOx("O");

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName("Test");
        fileDTO.setOrgFileName("orgTest");
        fileUploadService.dbFileUpload(fileDTO);

        updateBoard.setAttachmentCode(fileDTO.getAttachmentCode());

        boardMapper.updateBoard(updateBoard);
        BoardDTO updateBoardDetail = boardMapper.detailBoard(boardDTO);

        fileMapper.deleteFile(boardDetail.getAttachmentCode());
        FileDTO deleteFile = fileMapper.fileSelect(boardDetail.getAttachmentCode());

        assertThat(deleteFile).isNull();
        assertThat(boardDetail.getBoardNumber()).isEqualTo(updateBoardDetail.getBoardNumber());
        assertThat(boardDetail.getBoardKind()).isEqualTo(updateBoardDetail.getBoardKind());
        assertThat(boardDetail.getContent()).isNotEqualTo(updateBoardDetail.getContent());
        assertThat(boardDetail.getTitle()).isNotEqualTo(updateBoardDetail.getTitle());
        assertThat(updateBoardDetail.getAttachmentOx()).isEqualTo("O");
        assertThat(updateBoardDetail.getAttachmentCode()).isNotEqualTo(boardDetail.getAttachmentCode());


    }




}

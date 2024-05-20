package com.example.demo.board;

import com.example.demo.file.FileDTO;
import com.example.demo.file.FileMapper;
import com.example.demo.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    private final FileUploadService fileUploadService;

    private final FileMapper fileMapper;

    public List<BoardDTO> getBoard(String kind, int page){
        page = (page-1) *10;

        List<BoardDTO> boardList = boardMapper.getBoardList(kind,page);

        return boardList;
    }

    @Transactional
    public void createBoard(BoardCreateRequest param,String kind){
        Optional<String> attachmentOxOptional = Optional.ofNullable(param.getAttachmentOx());

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardKind(kind);
        boardDTO.setContent(param.getContent());
        boardDTO.setAttachmentOx(attachmentOxOptional.isPresent() ? attachmentOxOptional.get().toUpperCase() : null);
        boardDTO.setTitle(param.getTitle());
        boardDTO.setId(param.getId());
        //db 파일정보 저장
        if(boardDTO.getAttachmentOx() != null && boardDTO.getAttachmentOx().equalsIgnoreCase("O")){
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(param.getSaveFileName());
            fileDTO.setOrgFileName(param.getOrgFileName());
            fileUploadService.dbFileUpload(fileDTO);
            boardDTO.setAttachmentCode(fileDTO.getAttachmentCode());
        }
        boardMapper.createBoard(boardDTO);
    }

    public List<DetailDTO> boardDetail(int number, String kind){
        List<DetailDTO> resultList = new ArrayList<>();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);
        BoardDTO resultBoard = boardMapper.detailBoard(boardDTO);
        resultList.add(resultBoard);

        if(resultBoard.getAttachmentOx()!= null && resultBoard.getAttachmentOx().equalsIgnoreCase("O")){
            FileDTO resultFile = fileMapper.fileSelect(resultBoard.getAttachmentCode());
            resultList.add(resultFile);
        };


        return resultList;

    }

    @Transactional
    public void boardDelete(int number,String kind,String id){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);
        boardDTO.setId(id);

        BoardDTO detail =  boardMapper.detailBoard(boardDTO);

        boardMapper.deleteBoard(boardDTO);

        if(detail.getAttachmentOx() != null && detail.getAttachmentOx().equalsIgnoreCase("O")){
            fileMapper.deleteFile(detail.getAttachmentCode());
        }

    }

    @Transactional
    public void boardUpdate(int number,String kind,String id,BoardCreateRequest param){
        if(param.getDeleteFile().equalsIgnoreCase("O")){

        };

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(id);
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);
        boardDTO.setContent(param.getContent());
        boardDTO.setAttachmentOx(param.getAttachmentOx());
        boardDTO.setTitle(param.getTitle());
        if(boardDTO.getAttachmentOx().equalsIgnoreCase("O")){


        }

    }



}

package com.example.demo.board;

import com.example.demo.file.FileDTO;
import com.example.demo.file.FileMapper;
import com.example.demo.file.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardMapper boardMapper;

    private final FileUploadService fileUploadService;

    private final FileMapper fileMapper;

    private static final int FILE_NULL_NUMBER = 0;

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
        BoardDTO originalBoard = new BoardDTO();
        originalBoard.setBoardNumber(number);
        originalBoard.setBoardKind(kind);
        originalBoard.setId(id);
        originalBoard = boardMapper.detailBoard(originalBoard);

        BoardDTO updateBoardDTO = new BoardDTO();
        updateBoardDTO.setId(id);
        updateBoardDTO.setBoardNumber(number);
        updateBoardDTO.setBoardKind(kind);
        updateBoardDTO.setContent(param.getContent());
        updateBoardDTO.setAttachmentOx(param.getAttachmentOx());
        updateBoardDTO.setTitle(param.getTitle());
        updateBoardDTO.setAttachmentCode(originalBoard.getAttachmentCode());

        boolean fileDelete = originalBoard.getAttachmentOx().equalsIgnoreCase("O") && param.getDeleteFile().equalsIgnoreCase("O");

        updateBoardDTO.setAttachmentCode(fileDelete ? FILE_NULL_NUMBER :originalBoard.getAttachmentCode());

        if(param.getAttachmentOx().equalsIgnoreCase("O")){
            FileDTO fileDTO = new FileDTO();
            fileDTO.setOrgFileName(param.getOrgFileName());
            fileDTO.setFileName(param.getSaveFileName());
            fileMapper.fileInsert(fileDTO);
            updateBoardDTO.setAttachmentCode(fileDTO.getAttachmentCode());
        }

        boardMapper.updateBoard(updateBoardDTO);

        if(fileDelete){
            fileMapper.deleteFile(originalBoard.getAttachmentCode());
        }



    }



}

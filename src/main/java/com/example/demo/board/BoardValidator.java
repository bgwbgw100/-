package com.example.demo.board;

import com.example.demo.file.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Locale;


@Component
@RequiredArgsConstructor
public class BoardValidator {

    private final FileUploadUtil fileUploadUtil;
    private final BoardMapper boardMapper;

    boolean attachmentCheck(BoardCreateRequest boardCreateRequest){
        String path = fileUploadUtil.getUploadPath();
        String attachmentOx = boardCreateRequest.getAttachmentOx();

        if(attachmentOx != null && attachmentOx.equalsIgnoreCase("X")){
            return true;
        }else{ // 파일 저장을 한 경우
            String saveFileName = boardCreateRequest.getSaveFileName();
            File file = new File(path+File.separator + saveFileName);
            if (file.exists()) { // 파일이 존재
                return true;
            } else { // 파일이 없음
                return false;
            }
        }
    }
    public boolean boardUserCheck(int number,String kind,String id){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardNumber(number);
        boardDTO.setBoardKind(kind);
        BoardDTO detail = boardMapper.detailBoard(boardDTO);
        if(detail.getId().equals(id)){
            return true;
        }else {
            return false;
        }
    }


}

package com.example.demo.admin.board;

import com.example.demo.board.BoardDTO;
import com.example.demo.board.BoardMapper;
import com.example.demo.menu.MenuDTO;
import com.example.demo.menu.MenuMapper;
import com.example.demo.util.CustomMultiReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminBoardService {

    private final MenuMapper menuMapper;

    private final BoardMapper boardMapper;

    public Optional<CustomMultiReturn<List<MenuDTO>, List<BoardDTO>, Integer, Optional, Optional>> getBoardMenuList(int page, Optional<String> kindOptional){

        List<MenuDTO> boardMenuList = menuMapper.selectMenuByKind("BOARD");

        if (boardMenuList.isEmpty()){
            return Optional.empty();
        }

        page = (page-1) *10;

        String boardName;

        if(kindOptional.isEmpty()){
            MenuDTO menuDTO = boardMenuList.get(0);

            boardName = menuDTO.getName();
        }else {
            boardName = kindOptional.get();
        }


        List<BoardDTO> boardDTOS = boardMapper.selectBoardListByKind(boardName,page);

        int count = boardMapper.selectBoardCountByKind(boardName);

        CustomMultiReturn<List<MenuDTO>, List<BoardDTO>, Integer, Optional, Optional> multiReturn = new CustomMultiReturn<>();
        multiReturn.setA(boardMenuList);
        multiReturn.setB(boardDTOS);
        multiReturn.setC(count);

        return Optional.of(multiReturn);
    }

    public void blind(BoardDTO boardDTO){

        boardMapper.updateBoardBlind(boardDTO);
    }






}

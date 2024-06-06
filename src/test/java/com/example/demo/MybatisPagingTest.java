package com.example.demo;


import com.example.demo.board.BoardDTO;
import com.example.demo.board.BoardMapper;
import com.example.demo.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class MybatisPagingTest {
    @Autowired
    BoardMapper boardMapper;

    @Autowired
    DynamicMapper dynamicMapper;



    @Test
    public void interceptorTest(){

        CommonPagingExecuteImp<List<BoardDTO>> commonPagingExecuteImp = new CommonPagingExecuteImp<List<BoardDTO>>() {
            @Override
            public List<BoardDTO> execute(CommonPagingDTO commonPagingDTO) {
                return boardMapper.selectTestPagingInterceptor2(commonPagingDTO,"kind");
            }

        };
        CustomTwoReturn<List<BoardDTO>, CommonPagingDTO> execute = new CommonPagingExecute<List<BoardDTO>>().execute(commonPagingExecuteImp,dynamicMapper);
        CommonPagingDTO commonPagingDTO = execute.getType2();


    }



}

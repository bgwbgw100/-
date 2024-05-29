package com.example.demo;


import com.example.demo.board.BoardMapper;
import com.example.demo.util.CommonPaging;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPagingTest {
    @Autowired
    BoardMapper boardMapper;

    @Test
    public void languageDriverTest(){
        boardMapper.selectTestPaging(1,"notice");
    }

    @Test
    public void interceptorTest(){
        boardMapper.selectTestPagingInterceptor(new CommonPaging(1,10));
        boardMapper.selectTestPagingInterceptor2(new CommonPaging(1,10));

    }

}

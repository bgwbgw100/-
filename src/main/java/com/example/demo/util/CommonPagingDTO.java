package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonPagingDTO {
    private int perPage = 1;
    private int prePage;
    private int maxPage;
    private int nextPage;
    //보여줄 페이징 선택개수
    private int startDisplayPage;
    private int endDisplayPage;
    private int displayPage =5;
    private int postPage = 10;
    private boolean execute = false;
    private int cnt;
    private String query;

    public CommonPagingDTO(){

    };

    public void settingPaging(){
        query = null;
        if(cnt == 0){
            prePage = 1;
            nextPage = 1;
            maxPage = 1;
            displayPage =1;
            return;
        }
        //페이지 뒤로가기
        prePage = perPage-1 <= 0? 1 : perPage-1;
        //최대페이지
        maxPage = (cnt/postPage)+1;
        //다음페이지
        nextPage = Math.min(perPage + 1, maxPage);

        startDisplayPage = ((perPage/displayPage) * displayPage) + 1;

        endDisplayPage = Math.min(((perPage / displayPage) * displayPage) + displayPage + 1, maxPage);



    }








}

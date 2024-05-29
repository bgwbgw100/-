package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonPaging {
    private int perPage = 1;
    private int prePage;
    private int maxPage;
    private int nextPage;
    private int postPage = 10;

    public CommonPaging(){};

    public CommonPaging(int perPage, int postPage){
        this.perPage = perPage;
        this.postPage = postPage;
    }



}

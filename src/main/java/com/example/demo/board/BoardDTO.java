package com.example.demo.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BoardDTO implements DetailDTO{

    private int boardNumber;
    private String boardKind;
    private String id;
    private Date registTime;
    private Date deleteTime;
    private Date updateTime;
    private String title;
    private String content;
    private String attachmentOx;
    private int views;
    private int attachmentCode;
    private String deleteOx;




}

package com.example.demo.board;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private String blindOx;
    private Date blindTime;




}

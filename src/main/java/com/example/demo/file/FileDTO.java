package com.example.demo.file;

import com.example.demo.board.DetailDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class FileDTO implements DetailDTO {
    int attachmentCode;
    String fileName;
    String orgFileName;
    Date uploadDate;
}

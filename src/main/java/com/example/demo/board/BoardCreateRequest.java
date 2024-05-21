package com.example.demo.board;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@Validated
public class BoardCreateRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String saveFileName;
    private String orgFileName;
    private String attachmentOx;
    private String id;
    private String deleteFile = "none";

    public boolean checkDeleteFile(){

        return deleteFile.equalsIgnoreCase("none") || deleteFile.equals("O") || deleteFile.equals("X") ;
    }

}

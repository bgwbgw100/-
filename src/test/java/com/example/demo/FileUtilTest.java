package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class FileUtilTest {
    private static String os = System.getProperty("os.name").toLowerCase();

    @Value("${file.upload-dir-win}")
    String winFilePath;

    @Value("${file.upload-dir-linux}")
    String linuxFilePath;

    @Test
    public void pathTest(){
        assertThat(winFilePath).isNotEmpty();
    }

    @Test
    public void getUploadPath() {

        String filePath;
        if(os.equals("win")){
            filePath = winFilePath;
        }else{
            filePath = linuxFilePath;
        }
        pathDirCheck(filePath);


    }

    public void pathDirCheck(String path){
        Path rootLocation = Paths.get(path);

        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!", e);
        }

        Assertions.assertThat(Files.exists(rootLocation)).isTrue();

    }


}

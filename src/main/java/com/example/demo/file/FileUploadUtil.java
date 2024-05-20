package com.example.demo.file;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadUtil {
    private static String os = System.getProperty("os.name").toLowerCase();

    @Value("${file.upload-dir-win}")
    String winFilePath;

    @Value("${file.upload-dir-linux}")
    String linuxFilePath;

    public String getUploadPath() {

        String filePath;
        if(os.equals("win")){
            filePath = winFilePath;
        }else{
            filePath = linuxFilePath;
        }
        pathDirCheck(filePath);

        return filePath;
    }

    public void pathDirCheck(String path){
        Path rootLocation = Paths.get(path);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!", e);
        }

    }

}

package com.example.demo.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileUploadUtil fileUploadUtil;
    private final FileMapper fileMapper;
    private static final long BOARD_FILE_MAX_SIZE = 1024*1024*5; // 5MB

    public String fileUpload(MultipartFile file){
        // 파일저장경로
        String path = fileUploadUtil.getUploadPath();
        // UUID
        String uuid = UUID.randomUUID().toString();
        // 원본파일명
        String orgFileName = file.getOriginalFilename();
        // 저장할 파일명
        String saveFileName = uuid+"_"+orgFileName;
        // 파일 사이즈
        long fileSize = file.getSize();
        // 저장 경로
        Path savePath = Paths.get(path+ File.separator+saveFileName);

        if(fileSize>BOARD_FILE_MAX_SIZE){
            throw new RuntimeException("file size over");
        }

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException("file upload fail",e);
        }

        return saveFileName;

    }

    public void dbFileUpload(FileDTO fileDTO)  {
        fileMapper.fileInsert(fileDTO);
    }

    public FileDTO fileDetail(int fileCode){
        return fileMapper.fileSelect(fileCode);
    }





}

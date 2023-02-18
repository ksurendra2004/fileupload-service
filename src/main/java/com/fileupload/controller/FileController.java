package com.fileupload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    @PostMapping("/file_upload")
    public String processFileData(@RequestParam("file") MultipartFile file) {
        String uploadDirPath = "C://output//uploads//";
        if (file.isEmpty())
            return "Please select a file to upload";
        try {
            byte[] bytes = file.getBytes();
            Path of = Path.of(uploadDirPath);
            if(!Files.exists(of))
                Files.createDirectories(of);
            Path path = Paths.get(uploadDirPath + file.getOriginalFilename());
            Files.write(path, bytes);
            return "You successfully uploaded '" + file.getOriginalFilename() + "'";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

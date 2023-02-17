package com.fileupload.controller;

import com.fileupload.model.FileUploadModel;
import com.fileupload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @Value("${output.file.path}")
    private String outPutFilePath;

    @PostMapping("/fileupload")
    public String processFileData(@RequestParam("file") MultipartFile file) {

        FileUploadModel fileUploadModel = new FileUploadModel();
        String fileExtension = getFileExtension(file.getOriginalFilename());
        log.info("fileExtension {}", fileExtension);
        if (fileExtension.equals("txt")) {
            try {
                byte[] bytes = file.getBytes();
                log.info("file size in bytes: {}",  bytes.length);
                if(bytes.length < 1000000) {
                    fileUploadModel.setFileData(new String(bytes));
                    return fileUploadService.saveFileData(fileUploadModel);
                } else
                    return "File size should not exceed more than 1MB";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Unsupported file extension"; //"Unsupported file extension '" + file.getOriginalFilename() + "'";
    }

    @GetMapping("/fileGenerate")
    public String getFileData(@RequestParam("fileId") Long fileId) throws IOException {

        Optional<FileUploadModel> fileUploadModel = fileUploadService.getFileData(fileId);
        log.info("fileUploadModel: {}", fileUploadModel);

        if(fileUploadModel.isPresent()) {
            String fileData = fileUploadModel.stream()
                    .collect(Collectors.toList())
                    .get(0)
                    .getFileData();
            log.info("fileData: {}", fileData);
            if(!Files.exists(Path.of(outPutFilePath)))
                Files.createDirectory(Path.of(outPutFilePath));

            Files.writeString(Path.of(outPutFilePath + "output_"+ LocalDate.now() +".txt"), fileData);

            return "File downloaded successfully";
        }

        return "No Data found for the given id";
    }

    private String getFileExtension(String fileName) {
        if (fileName == null)
            throw new IllegalArgumentException("fileName must not be null!");

        String extension = "";
        int index = fileName.lastIndexOf('.');
        if (index > 0)
            extension = fileName.substring(index + 1);

        return extension;
    }

}

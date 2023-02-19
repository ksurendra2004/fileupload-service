package com.fileupload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    @Value("${output.file.path}") String uploadDirPath;
    @PostMapping("/file_upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) {
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

    @GetMapping("/file_download")
    public String fileDownload() {
        String fileLink = "file:/C:/output/uploads/Test.txt";
        String oppath = "C:/output/downloads/urlfile9.txt";
        try(ReadableByteChannel chh = Channels.newChannel(new URL(fileLink).openStream());
            FileOutputStream fos = new FileOutputStream(oppath)) {
            //URL link = new URL(fileLink);
            //InputStream ins = link.openStream();
            //ReadableByteChannel chh = Channels.newChannel(link.openStream());
            //FileOutputStream fos = new FileOutputStream(new File(oppath));
            fos.getChannel().transferFrom(chh, 0, Long.MAX_VALUE);
            //fos.close();
            //chh.close();
            return "You successfully downloaded the file";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

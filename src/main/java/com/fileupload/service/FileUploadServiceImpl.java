package com.fileupload.service;

import com.fileupload.dao.FileUploadDaoImpl;
import com.fileupload.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final FileUploadDaoImpl fileUploadDaoImpl;
    @Override
    public String saveFileData(FileUploadModel fileUploadModel) {
        fileUploadDaoImpl.save(fileUploadModel);
        return "File uploaded successfully";
    }

    @Override
    public Optional<FileUploadModel> getFileData(Long fileId) {

        return fileUploadDaoImpl.findById(fileId);
    }
}

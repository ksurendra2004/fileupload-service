package com.fileupload.service;

import com.fileupload.model.FileUploadModel;

import java.util.Optional;

public interface FileUploadService {

    public String saveFileData(FileUploadModel fileUploadModel);
    public Optional<FileUploadModel> getFileData(Long fileId);
}

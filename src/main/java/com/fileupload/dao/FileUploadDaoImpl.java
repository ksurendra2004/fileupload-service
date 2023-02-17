package com.fileupload.dao;

import com.fileupload.model.FileUploadModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadDaoImpl extends JpaRepository<FileUploadModel, Long> {
}

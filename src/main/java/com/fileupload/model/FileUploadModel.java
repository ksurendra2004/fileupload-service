package com.fileupload.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "file_details")
public class FileUploadModel {

    @Id
    @GeneratedValue
    private Long fileId;
    private String fileData;
    private LocalDateTime creationDate = LocalDateTime.now();
}

package com.bfi.ecm.Mappers;

import com.bfi.ecm.Entities.File;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Builder

public class FileDTO {
    private String parentFileId;
    private String fileName;
    private String path;
    private Date creationDate;
    private Date lastModifiedDate;
    private String Metadonne;

    public FileDTO fromEntity(File file) {
        FileDTO fileDTO = null;
        if (file == null) {
            return null;
        } else return fileDTO.builder()
                .parentFileId(file.getParentFileId())
                .fileName(file.getFileName())
                .path(file.getPath())
                .creationDate(file.getCreationDate())
                .lastModifiedDate(file.getLastModifiedDate())
                .Metadonne(file.getMetadonne())
                .build();
    }

    public File toEntity(FileDTO fileDTO) {
        File file = new File();
        if (fileDTO == null) {
            return null;
        } else {
            file.setParentFileId(fileDTO.getParentFileId());
            file.setFileName(fileDTO.getFileName());
            file.setPath(fileDTO.getPath());
            file.setCreationDate(fileDTO.getCreationDate());
            file.setLastModifiedDate(fileDTO.getLastModifiedDate());
            file.setMetadonne(fileDTO.getMetadonne());
            return file;
        }
    }

}

package com.bfi.ecm.Mappers;

import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Entities.File;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Builder
public class DirectoryDTO {
    private List<File> files = new ArrayList<>();
    private String name;
    private String path;

    public DirectoryDTO fromEntity(Directory directory) {
        DirectoryDTO directoryDTO = null;
        if (directory == null) {
            return null;
        } else return directoryDTO.builder()
                .name(directory.getName())
                .path(directory.getPath())
                .files(directory.getFiles())
                .build();
    }

    public Directory toEntity(DirectoryDTO directoryDTO) {
        Directory directory = new Directory();
        if (directoryDTO == null) {
            return null;
        } else {
            directory.setName(directoryDTO.getName());
            directory.setPath(directoryDTO.getPath());
            directory.setFiles(directoryDTO.getFiles());
            return directory;
        }
    }
}

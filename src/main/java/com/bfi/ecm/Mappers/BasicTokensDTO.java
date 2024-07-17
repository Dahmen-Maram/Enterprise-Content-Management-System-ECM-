package com.bfi.ecm.Mappers;

import com.bfi.ecm.Entities.BasicTokens;
import com.bfi.ecm.Entities.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BasicTokensDTO {
    private Set<File> files = new LinkedHashSet<>();

    @Builder
    public BasicTokensDTO(Set<File> files) {
        this.files = files;
    }

    public BasicTokensDTO fromEntity(BasicTokens basicTokens) {
        if (basicTokens == null) {
            return null;
        }
        return BasicTokensDTO.builder()
                .files(basicTokens.getFiles())
                .build();
    }

    public BasicTokens toEntity(BasicTokensDTO basicTokensDTO) {
        if (basicTokensDTO == null) {
            return null;
        }
        BasicTokens basicTokens = new BasicTokens();
        basicTokens.setFiles(basicTokensDTO.getFiles());
        return basicTokens;
    }
}

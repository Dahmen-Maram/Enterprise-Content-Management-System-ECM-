package com.bfi.ecm.Mappers;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Entities.Tokens;
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

    public BasicTokensDTO fromEntity(Tokens basicTokens) {
        if (basicTokens == null) {
            return null;
        }
        return BasicTokensDTO.builder()
                .files(basicTokens.getFiles())
                .build();
    }

    public Tokens toEntity(BasicTokensDTO basicTokensDTO) {
        if (basicTokensDTO == null) {
            return null;
        }
        Tokens basicTokens = new Tokens();
        basicTokens.setFiles(basicTokensDTO.getFiles());
        return basicTokens;
    }
}

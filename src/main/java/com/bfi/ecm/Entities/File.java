package com.bfi.ecm.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class File extends BaseEntity {
    private String parentFileId;
    private String fileName;
    private String path;
    private Date creationDate;
    private Date lastModifiedDate;
    private String Metadonne;

    @ManyToOne
    @JoinColumn(name = "directory_id")
    private Directory directory;

    @ManyToMany(mappedBy = "files")
    private Set<BasicTokens> basicTokenses = new LinkedHashSet<>();

}


package com.bfi.ecm.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Directory extends BaseEntity {
    @OneToMany(mappedBy = "directory", orphanRemoval = true)
    private List<File> files = new ArrayList<>();
    private String name;
    private String path;
}

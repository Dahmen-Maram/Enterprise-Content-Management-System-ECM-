package com.bfi.ecm.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicTokens extends BaseEntity {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "BasicTokens_files",
            joinColumns = @JoinColumn(name = "basicTokens_id"),
            inverseJoinColumns = @JoinColumn(name = "files_id"))
    private Set<File> files = new LinkedHashSet<>();

}

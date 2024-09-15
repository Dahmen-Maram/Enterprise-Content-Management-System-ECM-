package com.bfi.ecm.Repositories;

import com.bfi.ecm.Entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findAllById(Long id);


    @Modifying
    @Query("DELETE FROM File f WHERE f.name = :name")
    int deleteByName(@Param("name") String name);

    boolean existsByNameAndDirectory_Id(String name, Long parentId);

}
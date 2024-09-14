package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Repositories.DirectoryRepository;
import com.bfi.ecm.Repositories.FileRepository;
import com.bfi.ecm.Services.DirectoryServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DirectoryServiceImpl implements DirectoryServices {

    public final DirectoryRepository directoryRepository;
    public final FileRepository fileRepository;

    @Autowired
    public DirectoryServiceImpl(DirectoryRepository directoryRepository, FileRepository fileRepository) {
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Directory getDirectoryById(Long id) {
        return directoryRepository.findById(id).orElse(null);
    }

    public List<Directory> GetAllDirectories() {
        return directoryRepository.findAll();
    }

    public boolean isDirectoryExists(String name, Long parentId) {
        // Adjust this query according to your database schema
        return directoryRepository.existsByNameAndParentId(name, parentId);
    }

    public void updateDirectoryName(Long id, String name) {
        Directory directory = directoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Directory not found"));
        directory.setName(name);
        directoryRepository.save(directory);
    }

    public boolean existsByNameAndParentId(String name, Long parentId) {
        // Check if a directory or file with the given name exists under the given parent ID
        return directoryRepository.existsByNameAndParentId(name, parentId) ||
                fileRepository.existsByNameAndDirectory_Id(name, parentId);
    }

    @Transactional
    public Directory saveDirectory(Directory directory) {
        if (directory.getParent() != null) {
            Directory parent = directoryRepository.findById(directory.getParent().getId())
                    .orElseThrow(() -> new RuntimeException("Parent directory not found"));

            directory.setPath(parent.getPath() + "/" + directory.getName());
            parent.getChildren().add(directory);
            directory.setParent(parent);

            // Log the state
            System.out.println("Saving directory with parent ID: " + directory.getParent().getId());
        } else {
            directory.setPath(directory.getName());
        }

        // Save the directory to the database
        return directoryRepository.save(directory);
    }


    @Override
    public void updateDirectory(Directory directory) {
        directoryRepository.save(directory);
    }

    @Override
    public void deleteDirectory(Directory directory) {
        directoryRepository.delete(directory);

    }


}
package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Repositories.DirectoryRepository;
import com.bfi.ecm.Services.Serviceinterfaces.DirectoryServices;
import org.springframework.stereotype.Service;

@Service
public class DirectoryServiceImpl implements DirectoryServices {
    private DirectoryRepository directoryRepository;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public Directory getDirectoryById(Long id) {
        return directoryRepository.getById(id);
    }

    @Override
    public void saveDirectory(Directory directory) {
        directoryRepository.save(directory);
    }

    @Override
    public Directory updateDirectory(Directory directory) {
        Directory directory1 = directoryRepository.findById(directory.getId()).orElse(null);
        if (directory1 != null) {
            directory1 = directoryRepository.save(directory1);
        }
        return directory1;

    }

    @Override
    public void deleteDirectory(Directory directory) {
        Directory directory1 = directoryRepository.findById(directory.getId()).orElse(null);
        if (directory1 != null) {
            directoryRepository.delete(directory1);
        }

    }
}



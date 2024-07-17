package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Repositories.FileRepository;
import com.bfi.ecm.Services.Serviceinterfaces.FileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServicesImpl implements FileServices {
    private FileRepository fileRepository;

    @Autowired
    public FileServicesImpl(FileRepository _fileRepository) {
        this.fileRepository = _fileRepository;
    }


    @Override
    public File GetFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public void deleteFile(Long id) {
        var file = GetFileById(id);
        if (file != null) {
            fileRepository.delete(file);

        }


    }

    @Override
    public File updateFile(File f) {
        var file2 = fileRepository.findById(f.getId()).orElse(null);
        if (file2 != null) {
            file2 = fileRepository.save(file2);
        }
        return file2;
    }
}

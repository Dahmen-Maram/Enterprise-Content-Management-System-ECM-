package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Configurations.FileStorage;
import com.bfi.ecm.Services.FileStorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageServices {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorage fileStorage) {
        this.fileStorageLocation = Paths.get(fileStorage.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        Path targetLocation = this.fileStorageLocation.normalize();
        Path filePath = targetLocation.resolve(file.getOriginalFilename());
        file.transferTo(filePath);
        return filePath.getFileName().toString();
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }
}
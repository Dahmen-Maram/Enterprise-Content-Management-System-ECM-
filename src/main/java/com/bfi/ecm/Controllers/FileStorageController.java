package com.bfi.ecm.Controllers;


import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Mappers.DirectoryDTO;
import com.bfi.ecm.Mappers.FileDTO;
import com.bfi.ecm.Services.FileStorageServices;
import com.bfi.ecm.Services.ServiceImplementation.DirectoryServiceImpl;
import com.bfi.ecm.Services.ServiceImplementation.FileServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bfi/v1/files")
public class FileStorageController {

    @Autowired
    private FileStorageServices fileStorageService;

    @Autowired
    private FileServicesImpl fileServices;

    @Autowired
    private DirectoryServiceImpl directoryServices;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam(value = "id", required = false) Long parentFileId) {
        try {
            // Store the file and get the file name
            String fileName = fileStorageService.storeFile(file);
            String filetype = file.getContentType();
            Long fileSize = file.getSize();
            String filePath = directoryServices.getDirectoryById(parentFileId).getPath();

            // Create a FileDTO object
            FileDTO fileDTO = FileDTO.builder()
                    .name(fileName)
                    .parentFileId(parentFileId) // Use provided parentFileId if available
                    .fileType(filetype)
                    .size(fileSize)
                    .path(filePath)
                    .build();

            // Convert FileDTO to File entity and save
            File fileEntity = fileDTO.toEntity();

            // *Highlight: Set directory attribute of the fileEntity*
            Directory directory = directoryServices.getDirectoryById(parentFileId);
            fileEntity.setDirectory(directory); // This is crucial to set the relationship

            // Process the file if needed
            Path tempFile = Files.createTempFile("upload", ".tmp"); // Use a generic extension for temporary files
            file.transferTo(tempFile.toFile());
            String fileContent = fileServices.readFile(tempFile.toString());
            fileServices.saveToken(fileContent, fileEntity);
            fileServices.SaveFile(fileEntity);

            // *Highlight: Add fileEntity to the directory's files*
            directory.getFiles().add(fileEntity);

            return ResponseEntity.ok().body(Map.of("message", "File processed successfully"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file: " + e.getMessage());
        }
    }


    @GetMapping("all")
    public ResponseEntity<Map<String, Object>> getAllFilesAndDirectories() {
        List<File> files = fileServices.GetAllFiles();
        List<Directory> directories = directoryServices.GetAllDirectories();

        // Transforming entities to DTOs
        List<Map<String, Object>> fileDTOs = files.stream().map(file -> {
            Map<String, Object> fileMap = new HashMap<>();
            FileDTO fileDTO = FileDTO.fromEntity(file);
            fileMap.put("id", file.getId());
            fileMap.put("name", fileDTO.getName());
            fileMap.put("size", fileDTO.getSize());
            fileMap.put("filetype", fileDTO.getFileType());
            fileMap.put("path", fileDTO.getPath());
            if (file.getDirectory() != null) {
                fileMap.put("directory_id", file.getDirectory().getId());
            }
            return fileMap;
        }).toList();

        List<Map<String, Object>> directoryDTOs = directories.stream().map(directory -> {
            Map<String, Object> directoryMap = new HashMap<>();
            DirectoryDTO directoryDTO = DirectoryDTO.fromEntity(directory);
            directoryMap.put("id", directory.getId());
            directoryMap.put("name", directoryDTO.getName());
            directoryMap.put("path", directoryDTO.getPath());
            directoryMap.put("parent_id", directoryDTO.getParent().getId());
            if (directoryDTO.getParent() != null) {
                Map<String, Object> parentMap = new HashMap<>();
                parentMap.put("id", directoryDTO.getParent().getId());
                directoryMap.put("parent", parentMap);
            }

            return directoryMap;
        }).toList();

        // Creating the response map
        Map<String, Object> response = new HashMap<>();
        response.put("files", fileDTOs);
        response.put("directories", directoryDTOs);


        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
        try {
            // Delete the file from the database and storage
            fileServices.deleteFile(id);

            return ResponseEntity.ok("File deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting file: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFile(@PathVariable("id") Long id,
                                        @RequestParam(value = "file", required = false) MultipartFile newFile,
                                        @RequestParam(value = "newName", required = false) String newName,
                                        @RequestParam(value = "newParentFileId", required = false) Long newParentFileId) {
        try {
            // Retrieve the existing file entity
            File existingFile = fileServices.findFileById(id);
            if (existingFile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
            }

            // Update the file content if a new file is provided
            if (newFile != null) {
                // Store the new file and update the file name
                String newFileName = fileStorageService.storeFile(newFile);
                existingFile.setName(newFileName);
                existingFile.setSize(newFile.getSize());
                existingFile.setFileType(newFile.getContentType());

                // Update the file content
                Path tempFile = Files.createTempFile("upload", ".tmp"); // Temporary file path
                newFile.transferTo(tempFile.toFile());
                String newFileContent = fileServices.readFile(tempFile.toString());
                fileServices.saveToken(newFileContent, existingFile);
            }

            // Update the file name if provided
            if (newName != null && !newName.trim().isEmpty()) {
                existingFile.setName(newName.trim());
            }

            // Update the parent directory if provided
            if (newParentFileId != null) {
                Directory newParentDirectory = directoryServices.getDirectoryById(newParentFileId);
                if (newParentDirectory != null) {
                    existingFile.setDirectory(newParentDirectory);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parent directory ID");
                }
            }

            // Save the updated file entity
            fileServices.SaveFile(existingFile);

            return ResponseEntity.ok(Collections.singletonMap("message", "File updated successfully"));


        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error updating file: " + e.getMessage());
        }
    }

}
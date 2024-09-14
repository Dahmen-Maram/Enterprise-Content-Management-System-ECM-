package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Mappers.DirectoryDTO;
import com.bfi.ecm.Services.ServiceImplementation.DirectoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("bfi/v1/directories")
public class DirectoryController {

    private final DirectoryServiceImpl directoryServices;


    @Autowired
    public DirectoryController(DirectoryServiceImpl directoryServices) {
        this.directoryServices = directoryServices;
    }

    @GetMapping("getpath")
    public ResponseEntity<String> getPath(@RequestParam Long id) {
        try {
            String path = directoryServices.getDirectoryById(id).getPath();
            return ResponseEntity.ok(path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDirectory(@RequestBody DirectoryDTO directoryDTO) {
        // Validate the request
        if (directoryDTO.getName() == null || directoryDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Directory name cannot be empty");
        }

        // Check if directory or file with the same name exists
        Long parentId = directoryDTO.getParent() != null ? directoryDTO.getParent().getId() : null;
        boolean exists = directoryServices.existsByNameAndParentId(directoryDTO.getName(), parentId);

        if (exists) {
            // Provide a more detailed message depending on what exactly exists
            boolean isDirectory = directoryServices.isDirectoryExists(directoryDTO.getName(), parentId);
            if (isDirectory) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A directory with the same name already exists");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A file with the same name already exists");
            }
        }

        // Create and save the new directory
        try {
            Directory newDirectory = directoryServices.saveDirectory(DirectoryDTO.toEntity(directoryDTO));
            return ResponseEntity.ok("Directory created successfully");
        } catch (Exception e) {
            // Log the exception and return a generic error message
            // Adjust the logging level and message as needed
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the directory");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDirectoryName(
            @PathVariable("id") Long id,
            @RequestParam("name") String name) {
        try {
            directoryServices.updateDirectoryName(id, name);
            return ResponseEntity.ok("Directory updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update directory");
        }
    }


}
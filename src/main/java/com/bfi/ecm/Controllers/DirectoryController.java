package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.Directory;
import com.bfi.ecm.Mappers.DirectoryDTO;
import com.bfi.ecm.Services.Serviceinterfaces.DirectoryServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfi/v1/Directory")

public class DirectoryController {
    private DirectoryServices directoryServices;

    public DirectoryController(DirectoryServices directoryServices) {
        this.directoryServices = directoryServices;
    }

    @GetMapping("/savedirectory")
    public void saveDirectory(DirectoryDTO directoryDTO) {
        Directory directory = directoryDTO.toEntity(directoryDTO);
        directoryServices.saveDirectory(directory);

    }

    ;
}

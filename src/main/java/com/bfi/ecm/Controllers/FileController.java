package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Mappers.FileDTO;
import com.bfi.ecm.Services.Serviceinterfaces.FileServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfi/v1/file")
public class FileController {
    private FileServices fileServices;

    public FileController(FileServices personService) {
        this.fileServices = personService;
    }

    @GetMapping("/savefile")
    public void save(@RequestBody FileDTO fileDTO) {
        File file = fileDTO.toEntity(fileDTO);
        fileServices.saveFile(file);
    }
}

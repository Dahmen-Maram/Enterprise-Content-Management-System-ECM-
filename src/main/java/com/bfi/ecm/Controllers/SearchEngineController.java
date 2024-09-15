package com.bfi.ecm.Controllers;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Services.ServiceImplementation.SearchEngineServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bfi/v1/search")
public class SearchEngineController {

    private final SearchEngineServiceImpl searchEngineService;

    @Autowired
    public SearchEngineController(SearchEngineServiceImpl searchEngineService) {
        this.searchEngineService = searchEngineService;
    }

    @GetMapping
    public ResponseEntity<Map<Long, String>> rechercherFichiers(@RequestParam("mot") String mot) {
        List<File> fichiers = searchEngineService.rechercherFichiersParMot(mot);
        Map<Long, String> fileIdNameMap = fichiers.stream()
                .collect(Collectors.toMap(File::getId, File::getName));
        if (fichiers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fileIdNameMap);
    }
}

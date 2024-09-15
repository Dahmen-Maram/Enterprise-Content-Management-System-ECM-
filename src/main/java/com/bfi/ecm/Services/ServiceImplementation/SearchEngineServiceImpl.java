package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Entities.SearchEngine;
import com.bfi.ecm.Services.SearchEngineServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchEngineServiceImpl implements SearchEngineServices {

    private final SearchEngine searchEngine;

    @Autowired
    public SearchEngineServiceImpl(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }


    @Override
    public List<File> rechercherFichiersParMot(String mot) {
        return searchEngine.rechercherFichiersParMot(mot);
    }
}

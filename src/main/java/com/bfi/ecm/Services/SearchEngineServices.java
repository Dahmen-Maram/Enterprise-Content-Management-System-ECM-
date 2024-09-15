package com.bfi.ecm.Services;

import com.bfi.ecm.Entities.File;

import java.util.List;

public interface SearchEngineServices {
    public List<File> rechercherFichiersParMot(String mot);
}

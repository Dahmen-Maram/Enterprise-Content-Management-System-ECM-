package com.bfi.ecm.Entities;

import com.bfi.ecm.Repositories.FileRepository;
import com.bfi.ecm.Repositories.TokenRepository;
import org.apache.commons.codec.language.Soundex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SearchEngine {

    private final TokenRepository tokenRepository;
    private final FileRepository fileRepository;

    @Autowired
    public SearchEngine(TokenRepository tokenRepository, FileRepository fileRepository) {
        this.tokenRepository = tokenRepository;
        this.fileRepository = fileRepository;
    }

    public List<File> rechercherFichiersParMot(String mot) {
        // Trouver le code Soundex du mot
        String soundexCode = new Soundex().encode(mot);

        // Rechercher les tokens correspondant au code Soundex, au texte et au newToken
        List<Tokens> tokensBySoundex = tokenRepository.findBySoundexCode(soundexCode);
        List<Tokens> tokensByText = tokenRepository.findByTextContainingIgnoreCase(mot);
        List<Tokens> tokensByNewToken = tokenRepository.findByNewTokenContainingIgnoreCase(mot);

        // Combiner tous les tokens trouvés
        Set<Tokens> allTokens = new HashSet<>();
        allTokens.addAll(tokensBySoundex);
        allTokens.addAll(tokensByText);
        allTokens.addAll(tokensByNewToken);

        // Extraire les identifiants des fichiers à partir des tokens
        Set<Long> fileIds = allTokens.stream()
                .flatMap(token -> token.getFiles().stream())
                .map(File::getId)
                .collect(Collectors.toSet());

        // Trouver les fichiers par leurs identifiants
        return fileRepository.findAllById(fileIds);
    }
}

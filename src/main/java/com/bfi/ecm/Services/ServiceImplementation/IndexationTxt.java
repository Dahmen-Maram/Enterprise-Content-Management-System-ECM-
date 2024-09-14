package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.Tokens;
import com.bfi.ecm.Repositories.TokenRepository;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class IndexationTxt {
    private final TokenRepository tokenRepository;

    public IndexationTxt(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public void saveToken(String filepath) throws IOException {
        String text = readFile(filepath);
        Soundex soundex = new Soundex();
        Arrays.stream(text.split("\\s+")).forEach(word -> {
            String soundexCode = soundex.encode(word);

            Tokens token = new Tokens();
            token.setText(word);
            token.setSoundexCode(soundexCode);

            tokenRepository.save(token);
        });
    }


    public List<Tokens> findBySoundexCode(String soundexCode) {
        return tokenRepository.findBySoundexCode(soundexCode);
    }
}

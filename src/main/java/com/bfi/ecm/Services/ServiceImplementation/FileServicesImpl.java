package com.bfi.ecm.Services.ServiceImplementation;

import com.bfi.ecm.Entities.File;
import com.bfi.ecm.Entities.Tokens;
import com.bfi.ecm.Repositories.FileRepository;
import com.bfi.ecm.Repositories.TokenRepository;
import com.bfi.ecm.Services.FileServices;
import org.apache.commons.codec.language.Soundex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FileServicesImpl implements FileServices {

    public final FileRepository fileRepository;
    private final FileStorageServiceImpl fileStorageService;

    @Autowired
    public FileServicesImpl(FileRepository fileRepository, FileStorageServiceImpl fileStorageService) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public File GetFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public List<File> GetAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public void SaveFile(File file) {
        fileRepository.save(file);
    }

    @Value("${file.storage.location:src/Filesdirectory}")
    private String fileStorageLocation;

    @Override
    public void deleteFile(File fileEntity) {
        fileRepository.delete(fileEntity);
    }

    public File findFileById(Long id) {
        return fileRepository.findAllById(id);
    }


    public void deleteFile(Long id) {
        Optional<File> fileEntityOptional = fileRepository.findById(id);
        if (fileEntityOptional.isPresent()) {
            File fileEntity = fileEntityOptional.get();
            fileRepository.delete(fileEntity);
        } else {
            throw new RuntimeException("File not found for id: " + id);
        }
    }


    @Override
    public File updateFile(Long fileId, MultipartFile multipartFile, String newFileName) {
        // Retrieve the existing file entity
        File existingFile = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File with ID " + fileId + " not found"));

        // Update file metadata
        existingFile.setName(newFileName);
        existingFile.setFileType(multipartFile.getContentType());

        try {
            // Define the path to the file
            Path filePath = Paths.get(fileStorageLocation).resolve(newFileName).normalize();

            // Copy new content to the file, replacing existing content
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Optionally, you may want to update the file path if it's changed
            existingFile.setPath(filePath.toString());
        } catch (IOException ex) {
            throw new RuntimeException("Could not update file " + newFileName + ". Please try again!", ex);
        }

        // Save updated file entity
        fileRepository.save(existingFile);
        return existingFile;
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

    @Autowired
    private TokenRepository tokenRepository;

    public File saveToken(String text, File file) {
        Soundex soundex = new Soundex();
        Arrays.stream(text.split("\\s+")).forEach(word -> {
            String soundexCode = soundex.encode(word);
            String noVowel = removeVowels(word);
            List<Tokens> tokensList = tokenRepository.findByText(word);
            if (!tokensList.isEmpty()) {
                file.getTokens().addAll(tokensList);
            } else {
                Tokens token = new Tokens();
                token.setText(word);
                token.setSoundexCode(soundexCode);
                token.setNewToken(noVowel);

                token.getFiles().add(file);
                tokenRepository.save(token);
                file.getTokens().add(token);
            }
        });
        return file;
    }

    private String removeVowels(String word) {
        return word.replaceAll("[AEIOUaeiou]", "");
    }
}
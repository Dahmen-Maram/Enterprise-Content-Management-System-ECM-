package com.bfi.ecm.Services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageServices {
    public String storeFile(MultipartFile fil) throws IOException;

    public Resource loadFileAsResource(String fileName);
}

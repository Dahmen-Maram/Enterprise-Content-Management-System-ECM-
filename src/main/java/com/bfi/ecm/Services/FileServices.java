package com.bfi.ecm.Services;

import com.bfi.ecm.Entities.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileServices {
    public File updateFile(Long fileId, MultipartFile multipartFile, String newFileName);

    public File GetFileById(Long id);

    public void SaveFile(File file);

    public void deleteFile(File fileEntity);


}
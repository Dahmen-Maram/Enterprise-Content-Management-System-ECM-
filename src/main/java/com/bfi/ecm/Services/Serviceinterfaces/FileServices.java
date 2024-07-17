package com.bfi.ecm.Services.Serviceinterfaces;

import com.bfi.ecm.Entities.File;

public interface FileServices {
    public File GetFileById(Long id);

    public void saveFile(File file);

    public void deleteFile(Long id);

    public File updateFile(File file);

}

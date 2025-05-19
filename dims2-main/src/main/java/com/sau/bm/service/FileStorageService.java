package com.sau.bm.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStorageService {
    String saveFile(MultipartFile file) throws IOException;
    byte[] loadFile(String filename) throws IOException;
}

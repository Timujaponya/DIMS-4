package com.sau.bm.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR));
        Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
        Files.write(filePath, file.getBytes());
        return file.getOriginalFilename();
    }

    @Override
    public byte[] loadFile(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        return Files.readAllBytes(filePath);
    }
}

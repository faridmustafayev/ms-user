package com.example.ms.user.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
public class FileUtil {
    private final Base64.Encoder encoder;

    public FileUtil(Base64.Encoder encoder) {
        this.encoder = encoder;
    }

    public String encodeToBase64(MultipartFile file) {
        try {
            return encoder.encodeToString(file.getBytes());
        } catch (IOException ex) {
            log.error("Error encoding file to Base64: " + ex.getMessage());
            return null;
        }
    }

}

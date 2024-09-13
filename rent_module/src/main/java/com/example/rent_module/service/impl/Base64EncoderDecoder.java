package com.example.rent_module.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class Base64EncoderDecoder {

    public static String encode(MultipartFile multipartFile) throws IOException {

        return Base64.getEncoder().encodeToString(multipartFile.getBytes());
    }

    public static MultipartFile decode(String encodedString) throws IOException {

        return (MultipartFile) Files.write(Path.of("photo.jpeg"), Base64.getDecoder().decode(encodedString.getBytes()));
    }
}

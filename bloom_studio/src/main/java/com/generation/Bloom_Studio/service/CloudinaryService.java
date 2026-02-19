package com.generation.Bloom_Studio.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String subirImagen(MultipartFile archivo);
}

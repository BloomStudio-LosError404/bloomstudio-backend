package com.generation.Bloom_Studio.service;


import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    public List<Colors> findAll(){
        return etiquetaRepository.findAll();
    }
    public Optional<Colors> findById(Long id) {
        return etiquetaRepository.findById(id);
    }

    public Colors save(Colors colors) {
        return etiquetaRepository.save(colors);
    }

    public void delete(Long id) {
        etiquetaRepository.deleteById(id);
    }


}

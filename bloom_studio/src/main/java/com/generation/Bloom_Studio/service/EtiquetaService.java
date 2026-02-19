package com.generation.Bloom_Studio.service;


import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.model.Etiqueta;
import com.generation.Bloom_Studio.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    public List<Etiqueta> findAll(){
        return etiquetaRepository.findAll();
    }
    public Optional<Etiqueta> findById(Long id) {
        return etiquetaRepository.findById(id);
    }

    public Etiqueta save(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    public void delete(Long id) {

        Etiqueta etiqueta = etiquetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada con id: " + id));

        etiqueta.setEstatus(false); // Soft delete
        etiquetaRepository.save(etiqueta);
    }


}


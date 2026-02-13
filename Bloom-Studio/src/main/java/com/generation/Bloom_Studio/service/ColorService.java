package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.exceptions.ColorNotFoundException;
import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


public class ColorService {

    private final ColorsRepository colorsRepository;

    @Autowired
    public ColorService(ColorsRepository colorsRepository) {
        this.colorsRepository = colorsRepository;
    }

    // Metodo para recuperar todas las tallas
    public List<Colors> getTalla() {
        return colorsRepository.findAll();
    }
    //Metodo para recuperar todas las talals con el estatus en true
    public List<Colors> getTallatrue() {
        return colorsRepository.findByEstatusTrue();
    }

    // Metodo para crear nueva talla
    public Colors createTalla(Colors newTalla) {
        return colorsRepository.save(newTalla);
    }

    // findByNombreTalla
    public Colors findByNombreTalla(String nombreTalla) {
        return colorsRepository.findByNombreTalla(nombreTalla);
    }

    // findById
    public Talla findById(Long id) {
        return tallaRepository.findById(id)
                .orElseThrow(() -> new TallaNotFoundException(id));
    }

    // deleteTalla by ID
    public void deleteTalla(Long id) {
        Talla talla = tallaRepository.findById(id)
                .orElseThrow(() -> new TallaNotFoundException(id));

        talla.setEstatus(false);
        tallaRepository.save(talla);
    }

    // updateTalla
    public Talla updateTalla(Talla talla, Long id) {
        return tallaRepository.findById(id)
                .map(tallaData -> {
                    tallaData.setNombreTalla(talla.getNombreTalla());
                    tallaData.setEstatus(talla.getEstatus());
                    return tallaRepository.save(tallaData);
                })
                .orElseThrow(() -> new TallaNotFoundException(id));
    }


}

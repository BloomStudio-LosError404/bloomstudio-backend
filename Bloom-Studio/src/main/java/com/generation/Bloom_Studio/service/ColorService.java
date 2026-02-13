package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.exceptions.ColorNotFoundException;
import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class ColorService {

    private final ColorsRepository colorsRepository;

    @Autowired
    public TallaService(ColorsRepository colorsRepository) {
        this.tallaRepository = tallaRepository;
    }

    // Metodo para recuperar todas las tallas
    public List<Talla> getTalla() {
        return tallaRepository.findAll();
    }
    //Metodo para recuperar todas las talals con el estatus en true
    public List<Talla> getTallatrue() {
        return tallaRepository.findByEstatusTrue();
    }

    // Metodo para crear nueva talla
    public Talla createTalla(Talla newTalla) {
        return tallaRepository.save(newTalla);
    }

    // findByNombreTalla
    public Talla findByNombreTalla(String nombreTalla) {
        return tallaRepository.findByNombreTalla(nombreTalla);
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

package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.exceptions.ColorNotFoundException;
import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        return colorsRepository.findByEstatus(Boolean.TRUE);
    }

    // Metodo para crear nueva talla
    public Colors createTalla(Colors newTalla) {
        return colorsRepository.save(newTalla);
    }

    // findByNombreTalla
    public List<Colors> findByNombreColor(String nombreColor) {
        return colorsRepository.findByNombreColor(nombreColor);
    }

    // findById
    public Colors findById(Long id) {
        return colorsRepository.findById(id)
                .orElseThrow(() -> new ColorNotFoundException(id));
    }

    // deleteTalla by ID
    public void deleteTalla(Long id) {
        Colors talla = colorsRepository.findById(id)
                .orElseThrow(() -> new ColorNotFoundException(id));

        talla.setEstatus(false);
        colorsRepository.save(talla);
    }

    // updateTalla
    public Colors updateTalla(Colors colors, Long id) {
        return colorsRepository.findById(id)
                .map(tallaData -> {
                    tallaData.setNombre_color(colors.getNombre_color());
                    tallaData.setEstatus(colors.getEstatus());
                    return colorsRepository.save(tallaData);
                })
                .orElseThrow(() -> new ColorNotFoundException(id));
    }


}

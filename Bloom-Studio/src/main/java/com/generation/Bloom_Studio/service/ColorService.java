package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.exceptions.ColorNotFoundException;
import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {

    private final ColorsRepository colorsRepository;

    @Autowired
    public ColorService(ColorsRepository colorsRepository) {
        this.colorsRepository = colorsRepository;
    }

    // Metodo para recuperar todos los colores
    public List<Colors> getColor() {
        return colorsRepository.findAll();
    }

    //Metodo para recuperar todas los colores con el estatus en true
    public List<Colors> getColortrue() {
        return colorsRepository.findByEstatusTrue();
    }

    // Metodo para crear nueva talla
    public Colors createColor(Colors newColor) {
        return colorsRepository.save(newColor);
    }

    // findByNombreColor
    public List<Colors> findByNombreColor(String nombreColor) {
        return colorsRepository.findByNombreColor(nombreColor);
    }

    // findById
    public Colors findById(Long id) {
        return colorsRepository.findById(id)
                .orElseThrow(() -> new ColorNotFoundException(id));
    }

    // deleteColor by ID
    public void deleteColor(Long id) {
        Colors color = colorsRepository.findById(id)
                .orElseThrow(() -> new ColorNotFoundException(id));
        color.setEstatus(false);
        colorsRepository.save(color);
    }

    // updateColor
    public Colors updateColor(Colors color, Long id) {
        return colorsRepository.findById(id)
                .map(colorData -> {
                    colorData.setNombreColor(color.getNombreColor());
                    colorData.setEstatus(color.getEstatus());
                    return colorsRepository.save(colorData);
                })
                .orElseThrow(() -> new ColorNotFoundException(id));
    }


}

package com.generation.Bloom_Studio.model;

import jakarta.persistence.AttributeConverter;

public class EstadoProductoConverter implements AttributeConverter<EstadoProducto,String> {

    @Override
    public String convertToDatabaseColumn(EstadoProducto attribute){
        return attribute == null ? null : attribute.getDbValue();
    }

    @Override
    public EstadoProducto convertToEntityAttribute(String dbData){
        return dbData == null ? null : EstadoProducto.fromDbValue(dbData);
    }

}

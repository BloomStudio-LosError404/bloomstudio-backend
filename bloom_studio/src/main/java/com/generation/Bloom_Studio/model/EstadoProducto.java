package com.generation.Bloom_Studio.model;

public enum EstadoProducto {
    ACTIVO("activo"),
    AGOTADO("agotado"),
    DESCONTINUADO("descontinuado");

    private final String dbValue;

    EstadoProducto(String dbValue){
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static EstadoProducto fromDbValue(String value){
        for (EstadoProducto e : values()){
            if (e.dbValue.equalsIgnoreCase(value))
                return e;
        }
        throw new IllegalArgumentException("EstadoProducto invalido: " + value);
    }
}

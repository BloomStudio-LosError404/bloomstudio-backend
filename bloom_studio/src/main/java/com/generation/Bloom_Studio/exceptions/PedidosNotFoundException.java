package com.generation.Bloom_Studio.exceptions;

public class PedidosNotFoundException extends RuntimeException {
    public PedidosNotFoundException (Long idPedidos) {
        super("User not found with id: " + idPedidos);
    }
}

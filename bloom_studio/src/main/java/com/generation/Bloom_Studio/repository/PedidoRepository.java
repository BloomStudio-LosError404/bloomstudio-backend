package com.generation.Bloom_Studio.repository;

import com.generation.Bloom_Studio.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Para que el cliente vea SUS pedidos
    List<Pedido> findByUsuario_IdUsuario(Long idUsuario);
}

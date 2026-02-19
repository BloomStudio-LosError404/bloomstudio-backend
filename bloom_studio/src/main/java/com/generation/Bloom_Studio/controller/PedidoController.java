package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.dto.CrearPedidoDto;
import com.generation.Bloom_Studio.model.Pedido;
import com.generation.Bloom_Studio.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Endpoint para COMPRAR
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody CrearPedidoDto dto) {
        // Obtenemos el usuario automáticamente del Token JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Pedido nuevoPedido = pedidoService.crearPedido(email, dto);
        return ResponseEntity.ok(nuevoPedido);
    }

    // Endpoint para VER HISTORIAL
    @GetMapping("/mis-pedidos")
    public ResponseEntity<List<Pedido>> misPedidos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(pedidoService.misPedidos(auth.getName()));
    }

    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String, Object> data) {
        return ResponseEntity.ok("Producto removido del carrito");
    }
    @GetMapping("/cart")
    public ResponseEntity<?> getCart() {

        return ResponseEntity.ok("Carrito cargado");
    }
}

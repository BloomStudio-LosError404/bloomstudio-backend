package com.generation.Bloom_Studio.service;

import com.generation.Bloom_Studio.dto.CrearPedidoDto;
import com.generation.Bloom_Studio.model.DetallePedido;
import com.generation.Bloom_Studio.model.Inventory;
import com.generation.Bloom_Studio.model.Pedido;
import com.generation.Bloom_Studio.model.Usuario;
import com.generation.Bloom_Studio.repository.InventoryRepository;
import com.generation.Bloom_Studio.repository.PedidoRepository;
import com.generation.Bloom_Studio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final InventoryRepository inventoryRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         InventoryRepository inventoryRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.inventoryRepository = inventoryRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional // Si algo falla a la mitad, se deshacen los cambios (Rollback)
    public Pedido crearPedido(String emailUsuario, CrearPedidoDto dto) {

        // 1. OBTENER EL USUARIO
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + emailUsuario));

        // 2. CREAR LA CABECERA DEL PEDIDO
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setTotal(BigDecimal.ZERO); // Iniciamos en $0.00

        BigDecimal totalAcumulado = BigDecimal.ZERO;

        // 3. RECORRER LA LISTA DE PRODUCTOS DEL CARRITO
        for (CrearPedidoDto.ProductoPedidoDto item : dto.getProductos()) {

            // A. BUSCAR EN EL INVENTARIO
            Inventory inv = inventoryRepository.findById(item.getIdInventario())
                    .orElseThrow(() -> new RuntimeException("El producto con ID de inventario " + item.getIdInventario() + " no existe."));

            // B. RESTAR STOCK
            // Esto lanzará excepción si no hay suficiente stock
            inv.decrementar(item.getCantidad());
            inventoryRepository.save(inv); // Guardamos el nuevo stock reducido

            // C. OBTENER PRECIO (Navegamos: Inventory -> Products -> Precio)
            // Aquí cruzamos el puente hacia la tabla de Productos
            if (inv.getProducts() == null || inv.getProducts().getPrecio() == null) {
                throw new RuntimeException("El producto " + inv.getId() + " no tiene un precio asignado en el sistema.");
            }
            BigDecimal precioUnitario = inv.getProducts().getPrecio();

            // D. CÁLCULOS MATEMÁTICOS (Precio * Cantidad)
            BigDecimal cantidad = new BigDecimal(item.getCantidad());
            BigDecimal subtotal = precioUnitario.multiply(cantidad);

            totalAcumulado = totalAcumulado.add(subtotal);

            // E. CREAR EL DETALLE (Guardar el renglón en BD)
            DetallePedido detalle = new DetallePedido(inv, item.getCantidad(), precioUnitario);
            pedido.agregarDetalle(detalle);
        }

        // 4. GUARDAR EL PEDIDO FINAL
        pedido.setTotal(totalAcumulado);
        return pedidoRepository.save(pedido);
    }

    // Método para ver historial
    public List<Pedido> misPedidos(String email) {
        Usuario u = usuarioRepository.findByEmail(email).orElseThrow();
        return pedidoRepository.findByUsuario_IdUsuario(u.getIdUsuario());
    }
}

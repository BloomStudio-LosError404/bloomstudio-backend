package com.generation.Bloom_Studio.service.imp;

import com.generation.Bloom_Studio.dto.InventoryResponseDTO;
import com.generation.Bloom_Studio.exceptions.product.ProductNotFoundException;
import com.generation.Bloom_Studio.model.*;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import com.generation.Bloom_Studio.repository.InventoryRepository;
import com.generation.Bloom_Studio.repository.ProductoRepository;
import com.generation.Bloom_Studio.repository.TallaRepository;
import com.generation.Bloom_Studio.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryBadRequestException;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryConflictException;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryNotFoundException;
import com.generation.Bloom_Studio.model.EstadoProducto;


import java.util.List;

@Service
@Transactional
public class InventoryServiceImp implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductoRepository productsRepository;
    private final ColorsRepository colorsRepository;
    private final TallaRepository tallaRepository;

    public InventoryServiceImp(
            InventoryRepository inventoryRepository,
            ProductoRepository productsRepository,
            ColorsRepository colorsRepository,
            TallaRepository tallaRepository
    ) {
        this.inventoryRepository = inventoryRepository;
        this.productsRepository = productsRepository;
        this.colorsRepository = colorsRepository;
        this.tallaRepository = tallaRepository;
    }

    @Override
    public InventoryResponseDTO crearOActualizarCantidad(Long productoId, Long colorId, Long tallaId, Integer cantidad) {

        if (productoId == null || productoId <= 0) {
            throw new InventoryBadRequestException("productoId es obligatorio y debe ser mayor que cero.");
        }
        if (colorId == null || colorId <= 0) {
            throw new InventoryBadRequestException("colorId es obligatorio y debe ser mayor que cero.");
        }
        if (tallaId == null || tallaId <= 0) {
            throw new InventoryBadRequestException("tallaId es obligatorio y debe ser mayor que cero.");
        }
        if (cantidad == null || cantidad < 0) {
            throw new InventoryBadRequestException("La cantidad no puede ser nula ni negativa.");
        }

        Products producto = productsRepository.findById(productoId)
                .orElseThrow(() -> new InventoryNotFoundException("Producto no encontrado con id: " + productoId));

        if (producto.getEstadoProducto() == EstadoProducto.DESCONTINUADO) {
            throw new InventoryConflictException("No se puede gestionar inventario de un producto descontinuado.");
        }

        Colors color = colorsRepository.findById(colorId)
                .orElseThrow(() -> new InventoryNotFoundException("Color no encontrado con id: " + colorId));

        Talla talla = tallaRepository.findById(tallaId)
                .orElseThrow(() -> new InventoryNotFoundException("Talla no encontrada con id: " + tallaId));

        Inventory inv = inventoryRepository
                .findByProducts_IdAndColor_IdAndTallas_Id(productoId, colorId, tallaId)
                .orElseGet(() -> {
                    Inventory nuevo = new Inventory();
                    nuevo.setProducts(producto);
                    nuevo.setColor(color);
                    nuevo.setTallas(talla);
                    nuevo.setCantidad(0);
                    return nuevo;
                });

        inv.setCantidad(cantidad);
        Inventory guardado = inventoryRepository.save(inv);

        syncProductoPorStock(productoId);
        return toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponseDTO obtenerPorId(Long inventarioId) {

        if (inventarioId == null || inventarioId <= 0) {
            throw new InventoryBadRequestException("El id de inventario debe ser mayor que cero.");
        }


        Inventory inv = inventoryRepository.findById(inventarioId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventario no encontrado con id: " + inventarioId));

        return toDTO(inv);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> listarPorProducto(Long productoId) {

        if (productoId == null || productoId <= 0) {
            throw new InventoryBadRequestException("productoId debe ser mayor que cero.");
        }


        List<Inventory> inventarios = inventoryRepository.findAllByProductoIdWithJoins(productoId);

        return inventarios.stream().map(this::toDTO).toList();
    }

    @Override
    public InventoryResponseDTO incrementar(Long inventarioId, Integer delta) {

        if (inventarioId == null || inventarioId <= 0) {
            throw new InventoryBadRequestException("El id de inventario debe ser mayor que cero.");
        }
        if (delta == null || delta <= 0) {
            throw new InventoryBadRequestException("El delta debe ser mayor que cero.");
        }

        Inventory inv = inventoryRepository.findById(inventarioId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventario no encontrado con id: " + inventarioId));

        if (inv.getProducts().getEstadoProducto() == EstadoProducto.DESCONTINUADO) {
            throw new InventoryConflictException("No se puede modificar inventario de un producto descontinuado.");
        }


        inv.incrementar(delta);
        Inventory guardado = inventoryRepository.save(inv);
        syncProductoPorStock(inv.getProducts().getId());
        return toDTO(guardado);
    }

    @Override
    public InventoryResponseDTO decrementar(Long inventarioId, Integer delta) {

        if (inventarioId == null || inventarioId <= 0) {
            throw new InventoryBadRequestException("El id de inventario debe ser mayor que cero.");
        }
        if (delta == null || delta <= 0) {
            throw new InventoryBadRequestException("El delta debe ser mayor que cero.");
        }

        Inventory inv = inventoryRepository.findById(inventarioId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventario no encontrado con id: " + inventarioId));

        if (inv.getProducts().getEstadoProducto() == EstadoProducto.DESCONTINUADO) {
            throw new InventoryConflictException("No se puede modificar inventario de un producto descontinuado.");
        }

        try {
            inv.decrementar(delta);
        } catch (IllegalArgumentException ex) {
            throw new InventoryConflictException(ex.getMessage());
        }
        Inventory guardado = inventoryRepository.save(inv);
        syncProductoPorStock(inv.getProducts().getId());
        return toDTO(guardado);
    }

    private void syncProductoPorStock(Long productoId) {
        Products producto = productsRepository.findById(productoId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + productoId));

        long totalStock = inventoryRepository.sumCantidadByProductoId(productoId);

        if (producto.getEstadoProducto() == EstadoProducto.DESCONTINUADO) {
            producto.setEstatus(false);
            productsRepository.save(producto);
            return;
        }

        if (totalStock > 0) {
            producto.setEstatus(true);
            producto.setEstadoProducto(EstadoProducto.ACTIVO);
        } else {
            producto.setEstatus(false);
            producto.setEstadoProducto(EstadoProducto.AGOTADO);
        }

        productsRepository.save(producto);
    }


    private InventoryResponseDTO toDTO(Inventory inv) {

        InventoryResponseDTO dto = new InventoryResponseDTO();

        dto.setIdInventario(inv.getId());
        dto.setCantidad(inv.getCantidad());

        // Producto
        if (inv.getProducts() != null) {
            dto.setProductoId(inv.getProducts().getId());
            dto.setSku(inv.getProducts().getSku());
            dto.setNombreProducto(inv.getProducts().getNombre());
        }


        if (inv.getColor() != null) {
            dto.setColorId(inv.getColor().getId());
            dto.setNombreColor(inv.getColor().getNombreColor());
        }


        if (inv.getTallas() != null) {
            dto.setTallaId(inv.getTallas().getId());
            dto.setNombreTalla(inv.getTallas().getNombreTalla());
        }

        return dto;
    }
    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> listarTodos() {

        List<Inventory> inventarios = inventoryRepository.findAllWithJoins();

        return inventarios.stream()
                .map(this::toDTO)
                .toList();
    }

}

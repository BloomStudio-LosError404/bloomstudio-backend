package com.generation.Bloom_Studio.service.imp;

import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.model.Inventory;
import com.generation.Bloom_Studio.model.Products;
import com.generation.Bloom_Studio.model.Talla;
import com.generation.Bloom_Studio.repository.ColorsRepository;
import com.generation.Bloom_Studio.repository.InventoryRepository;
import com.generation.Bloom_Studio.repository.ProductoRepository;
import com.generation.Bloom_Studio.repository.TallaRepository;
import com.generation.Bloom_Studio.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryServiceImp implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductoRepository productRepository;
    private final ColorsRepository colorRepository;
    private final TallaRepository tallaRepository;

    public InventoryServiceImp(InventoryRepository inventoryRepository, ProductoRepository productRepository, ColorsRepository colorRepository, TallaRepository tallaRepository){
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.colorRepository = colorRepository;
        this.tallaRepository = tallaRepository;
    }

    @Override
    public Inventory crearOActualizarCantidad(Long productoId, Long colorId, Long tallaId, Integer cantidad ){
        if(cantidad == null || cantidad < 0) throw new IllegalArgumentException("La cantidad no puede ser nula ni negativa.");

        Products products = productRepository.findById(productoId).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + productoId));

        if(!Boolean.TRUE.equals(products.getEstatus())){
            throw new IllegalArgumentException("No se puede gestionar inventario de un producto desactivado.");
        }

        Colors color = colorRepository.findById(colorId).orElseThrow(() -> new EntityNotFoundException("Color no encontrado: " + colorId));

        Talla talla = tallaRepository.findById(tallaId).orElseThrow(() -> new EntityNotFoundException("Talla no encontrada: " + tallaId));

        Inventory inventory = inventoryRepository.findByProducts_IdAndColor_IdAndTallas_Id(productoId, colorId,tallaId).orElseGet(() -> {
            Inventory nuevo = new Inventory();
            nuevo.setProducts(products);
            nuevo.setColor(color);
            nuevo.setCantidad(0);
            nuevo.setTallas(talla);
            return nuevo;
        });

        inventory.setCantidad(cantidad);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory incrementar(Long inventarioId, Integer delta) {
        if (delta == null || delta <= 0) throw new IllegalArgumentException("El incremento debe ser mayor que cero.");

        Inventory inv = obtenerPorId(inventarioId);
        if (!Boolean.TRUE.equals(inv.getProducts().getEstatus())) {
            throw new IllegalArgumentException("No se puede modificar inventario de un producto desactivado.");
        }

        inv.incrementar(delta);
        return inventoryRepository.save(inv);
    }

        @Override
        public Inventory decrementar(Long inventarioId, Integer delta) {
            if (delta == null || delta <= 0) throw new IllegalArgumentException("El decremento debe ser mayor que cero.");

            Inventory inv = obtenerPorId(inventarioId);
            if (!Boolean.TRUE.equals(inv.getProducts().getEstatus())) {
                throw new IllegalArgumentException("No se puede modificar inventario de un producto desactivado.");
            }

            inv.decrementar(delta);
            return inventoryRepository.save(inv);
        }

        @Override
        @Transactional(readOnly = true)
        public Inventory obtenerPorId(Long inventarioId) {
            return inventoryRepository.findById(inventarioId)
                    .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado: " + inventarioId));
        }

    @Override
    @Transactional(readOnly = true)
    public List<Inventory> listaPorProducto(Long productoId) {
        return inventoryRepository.findAllByProducts_Id(productoId);
    }






}

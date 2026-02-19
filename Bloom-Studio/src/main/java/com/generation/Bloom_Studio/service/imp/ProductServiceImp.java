package com.generation.Bloom_Studio.service.imp;

import com.generation.Bloom_Studio.dto.CatalogProductDTO;
import com.generation.Bloom_Studio.dto.CreateProductRequestDTO;
import com.generation.Bloom_Studio.dto.ProductListDTO;
import com.generation.Bloom_Studio.dto.ProductResponseDTO;
import com.generation.Bloom_Studio.model.*;
import com.generation.Bloom_Studio.repository.CategoryRepository;
import com.generation.Bloom_Studio.repository.EtiquetaRepository;
import com.generation.Bloom_Studio.repository.InventoryRepository;
import com.generation.Bloom_Studio.repository.ProductoRepository;
import com.generation.Bloom_Studio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.generation.Bloom_Studio.exceptions.product.ProductBadRequestException;
import com.generation.Bloom_Studio.exceptions.product.ProductConflictException;
import com.generation.Bloom_Studio.exceptions.product.ProductNotFoundException;


import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImp implements ProductService {

    private final ProductoRepository productoRepository;
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    private final EtiquetaRepository etiquetaRepository;


    public ProductServiceImp(ProductoRepository productoRepository, InventoryRepository inventoryRepository, CategoryRepository categoryRepository, EtiquetaRepository etiquetaRepository) {
        this.productoRepository = productoRepository;
        this.inventoryRepository = inventoryRepository;
        this.categoryRepository = categoryRepository;
        this.etiquetaRepository = etiquetaRepository;
    }

    private void syncProductoPorStock(Products producto) {

        if (producto.getEstadoProducto() == EstadoProducto.DESCONTINUADO) {
            producto.setEstatus(false);
            return;
        }

        long totalStock = inventoryRepository.sumCantidadByProductoId(producto.getId());

        if (totalStock > 0) {
            producto.setEstatus(true);
            producto.setEstadoProducto(EstadoProducto.ACTIVO);
        } else {
            producto.setEstatus(false);
            producto.setEstadoProducto(EstadoProducto.AGOTADO);
        }
    }

    @Override
    public Products crearProductoConRelaciones(CreateProductRequestDTO dto, String imgUrl) {

        if (dto == null) {
            throw new ProductBadRequestException("El body del producto es obligatorio.");
        }
        if (dto.getSku() == null || dto.getSku().isBlank()) {
            throw new ProductConflictException("El SKU es obligatorio.");
        }
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new ProductBadRequestException("El nombre es obligatorio.");
        }
        if (dto.getPrecio() == null) {
            throw new ProductBadRequestException("El precio es obligatorio.");
        }
        if (productoRepository.existsBySku(dto.getSku())) {
            throw new ProductConflictException("Ya existe un producto con el SKU: " + dto.getSku());
        }
        if (imgUrl == null || imgUrl.isBlank()) {
            throw new ProductBadRequestException("La URL de imagen es obligatoria.");
        }

        Products p = new Products();
        p.setSku(dto.getSku());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setImgUrl(imgUrl);


        if (dto.getCategoriaIds() != null && !dto.getCategoriaIds().isEmpty()) {
            Set<Category> categorias = new HashSet<>(categoryRepository.findAllById(dto.getCategoriaIds()));
            if (categorias.size() != dto.getCategoriaIds().size()) {
                throw new ProductBadRequestException("Una o más categorías no existen.");
            }
            p.setCategorias(categorias);
        }

        if (dto.getEtiquetaIds() != null && !dto.getEtiquetaIds().isEmpty()) {
            Set<Etiqueta> etiquetas = new HashSet<>(etiquetaRepository.findAllById(dto.getEtiquetaIds()));
            if (etiquetas.size() != dto.getEtiquetaIds().size()) {
                throw new ProductBadRequestException("Una o más etiquetas no existen.");
            }
            p.setEtiquetas(etiquetas);
        }

        Products guardado = productoRepository.save(p);

        syncProductoPorStock(guardado);

        return productoRepository.save(guardado);
    }

    @Override
    public Products actualizarCategoriasEtiquetas(Long productId, List<Long> categoriaIds, List<Long> etiquetaIds) {

        Products p = productoRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + productId));

        if (categoriaIds != null) {
            Set<Category> categorias = new HashSet<>(categoryRepository.findAllById(categoriaIds));
            if (categorias.size() != categoriaIds.size()) {
                throw new ProductBadRequestException("Una o más categorías no existen.");
            }
            p.setCategorias(categorias);
        }

        if (etiquetaIds != null) {
            Set<Etiqueta> etiquetas = new HashSet<>(etiquetaRepository.findAllById(etiquetaIds));
            if (etiquetas.size() != etiquetaIds.size()) {
                throw new ProductBadRequestException("Una o más etiquetas no existen.");
            }
            p.setEtiquetas(etiquetas);
        }

        return productoRepository.save(p);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long productoId, Long categoriaId) {

        Products producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.getCategorias()
                .removeIf(c -> c.getIdCategoria().equals(categoriaId));

        productoRepository.save(producto);
    }


    @Override
    public Products crearProducto(Products products) {

        if (products.getSku() == null || products.getSku().isBlank()){
            throw new ProductConflictException("El SKU es obligatorio.");
        }

        if (products.getNombre() == null || products.getNombre().isBlank()) {
            throw new ProductBadRequestException("El nombre es obligatorio.");
        }
        if (products.getPrecio() == null) {
            throw new ProductBadRequestException("El precio es obligatorio.");
        }

        if(productoRepository.existsBySku(products.getSku())){
            throw new ProductConflictException("Ya existe un producto con el SKU: " + products.getSku());
        }

        Products guardado = productoRepository.save(products);

        syncProductoPorStock(guardado);

        return productoRepository.save(guardado);
    }

    @Override
    public Products obtenerProductoId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));
    }

    @Override
    public List<Products> listaProductosActivos() {
        return productoRepository.findByEstatusTrue();
    }

    @Override
    public Products actualizarProducto(Long id, Products products) {
        Products existente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));

        if(products.getSku() != null){
            if(products.getSku().isBlank()){
                throw new ProductBadRequestException("El SKU no puede ser vacío.");
            }
            if(!products.getSku().equals(existente.getSku()) && productoRepository.existsBySku(products.getSku())){
                throw new ProductConflictException("Ya existe un producto con el SKU: " + products.getSku());
            }
            existente.setSku(products.getSku());
        }

        if(products.getNombre() != null){
            if(products.getNombre().isBlank()){
                throw new ProductBadRequestException("El nombre no puede ser vacío.");
            }
            existente.setNombre(products.getNombre());
        }

        if(products.getDescripcion() != null) existente.setDescripcion(products.getDescripcion());
        if(products.getPrecio() != null) existente.setPrecio(products.getPrecio());
        if(products.getImgUrl() != null) existente.setImgUrl(products.getImgUrl());
        if(products.getCategorias() != null) existente.setCategorias(products.getCategorias());
        if(products.getEtiquetas() != null) existente.setEtiquetas(products.getEtiquetas());

        Products guardado = productoRepository.save(existente);

        syncProductoPorStock(guardado);

        return productoRepository.save(guardado);
    }

    @Override
    public void eliminarProducto(Long id) {
        Products producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));

        productoRepository.delete(producto);
    }

    @Override
    public Products cambiarEstadoProducto(Long id, EstadoProducto nuevoEstado) {
        Products producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));

        if (nuevoEstado == EstadoProducto.DESCONTINUADO) {
            producto.setEstadoProducto(EstadoProducto.DESCONTINUADO);
            producto.setEstatus(false);
            return productoRepository.save(producto);
        }

        producto.setEstadoProducto(nuevoEstado);

        Products guardado = productoRepository.save(producto);

        syncProductoPorStock(guardado);

        return productoRepository.save(guardado);
    }
    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO obtenerProductoConStock(Long id) {

        Products p = productoRepository.findWithRelacionesById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con id: " + id));

        long stock = inventoryRepository.sumCantidadByProductoId(id);

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(p.getId());
        dto.setSku(p.getSku());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setImgUrl(p.getImgUrl());
        dto.setEstadoProducto(p.getEstadoProducto());
        dto.setEstatus(p.getEstatus());
        dto.setStockTotal(stock);

        // 🔥 PROTECCIÓN CONTRA NULL
        if (p.getCategorias() != null) {
            dto.setCategoriaNombres(
                    p.getCategorias().stream()
                            .map(Category::getNombreCategoria)
                            .toList()
            );

            dto.setCategoriaIds(
                    p.getCategorias().stream()
                            .map(Category::getIdCategoria)
                            .toList()
            );
        } else {
            dto.setCategoriaNombres(List.of());
            dto.setCategoriaIds(List.of());
        }

        if (p.getEtiquetas() != null) {
            dto.setEtiquetaNombres(
                    p.getEtiquetas().stream()
                            .map(Etiqueta::getNombreEtiqueta)
                            .toList()
            );

            dto.setEtiquetaIds(
                    p.getEtiquetas().stream()
                            .map(Etiqueta::getId_etiqueta)
                            .toList()
            );
        } else {
            dto.setEtiquetaNombres(List.of());
            dto.setEtiquetaIds(List.of());
        }

        return dto;
    }

    @Override
    public List<Products> listarTodos() {
        return List.of();
    }

    @Override
    public List<ProductListDTO> listarTodosConStock() {
        return productoRepository.listarTodosConStock();
    }

    @Override
    public List<ProductListDTO> listarActivosConStock() {
        return productoRepository.listarActivosConStock();
    }

    // ===== NUEVO: catálogo enriquecido para la Tienda (frontend) =====
    @Override
    @Transactional(readOnly = true)
    public List<CatalogProductDTO> listarCatalogo() {

        List<Products> productos = productoRepository.findActivosWithRelaciones();
        if (productos.isEmpty()) return List.of();

        List<Long> ids = productos.stream()
                .map(Products::getId)
                .toList();

        Map<Long, Long> stockPorProducto = inventoryRepository
                .sumStockByProductoIds(ids)
                .stream()
                .collect(Collectors.toMap(
                        InventoryRepository.StockTotalRow::getProductoId,
                        InventoryRepository.StockTotalRow::getStockTotal
                ));

        List<Inventory> inventarios =
                inventoryRepository.findAllByProductoIdsWithJoins(ids);

        Map<Long, Set<String>> coloresPorProducto = new HashMap<>();
        Map<Long, Set<String>> tallasPorProducto = new HashMap<>();

        for (Inventory inv : inventarios) {
            Long productoId = inv.getProducts().getId();

            coloresPorProducto
                    .computeIfAbsent(productoId, k -> new LinkedHashSet<>())
                    .add(inv.getColor().getNombreColor());

            tallasPorProducto
                    .computeIfAbsent(productoId, k -> new LinkedHashSet<>())
                    .add(inv.getTallas().getNombreTalla());
        }

        List<CatalogProductDTO> resultado = new ArrayList<>();

        for (Products p : productos) {

            List<String> categorias = p.getCategorias()
                    .stream()
                    .map(Category::getNombreCategoria)
                    .toList();

            List<String> etiquetas = p.getEtiquetas()
                    .stream()
                    .map(Etiqueta::getNombreEtiqueta)
                    .toList();

            long stockTotal = stockPorProducto.getOrDefault(p.getId(), 0L);

            List<String> colores = new ArrayList<>(
                    coloresPorProducto.getOrDefault(p.getId(), Set.of())
            );

            List<String> tallas = new ArrayList<>(
                    tallasPorProducto.getOrDefault(p.getId(), Set.of())
            );

            CatalogProductDTO dto = new CatalogProductDTO();

            dto.setId(p.getId());
            dto.setSku(p.getSku());
            dto.setNombre(p.getNombre());
            dto.setDescripcion(p.getDescripcion());

            dto.setCategorias(categorias);
            dto.setCategoria(
                    categorias.isEmpty() ? "Sin categoría" : categorias.get(0)
            );

            dto.setColores(colores);
            dto.setTallas(tallas);

            dto.setPrecio(p.getPrecio());
            dto.setRating(0.0);
            dto.setReviews(0);

            dto.setEtiquetas(etiquetas);

            dto.setImagen(new CatalogProductDTO.ImagenDTO(p.getImgUrl(), p.getNombre()));

            dto.setStockTotal(stockTotal);

            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    @Transactional
    public void eliminarEtiqueta(Long productoId, Long etiquetaId) {

        Products producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.getEtiquetas()
                .removeIf(e -> e.getId_etiqueta().equals(etiquetaId));

        productoRepository.save(producto);
    }

    @Override
    public void agregarCategoria(Long productoId, Long categoriaId) {

        Products producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category categoria = categoryRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.getCategorias().add(categoria);

        productoRepository.save(producto);
    }

    @Override
    public void agregarEtiqueta(Long productoId, Long etiquetaId) {

        Products producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new RuntimeException("Etiqueta no encontrada"));

        producto.getEtiquetas().add(etiqueta);

        productoRepository.save(producto);
    }

}
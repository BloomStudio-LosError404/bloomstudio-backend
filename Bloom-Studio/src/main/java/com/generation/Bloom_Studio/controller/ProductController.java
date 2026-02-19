package com.generation.Bloom_Studio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generation.Bloom_Studio.dto.*;
import com.generation.Bloom_Studio.exceptions.product.ProductBadRequestException;
import com.generation.Bloom_Studio.model.Products;
import com.generation.Bloom_Studio.service.CloudinaryService;
import com.generation.Bloom_Studio.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.generation.Bloom_Studio.dto.CreateProductRequestDTO;


import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@CrossOrigin(
        origins = "*",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.PATCH,
                RequestMethod.OPTIONS
        }
)
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    public ProductController(ProductService productService,
                             CloudinaryService cloudinaryService,
                             ObjectMapper objectMapper) {
        this.productService = productService;
        this.cloudinaryService = cloudinaryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<Products> crear(@RequestBody Products products){
        Products creado = productService.crearProducto(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping(value = "/con-imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDTO> crearConImagen(
            @RequestPart("producto") String productoJson,
            @RequestPart("imagen") MultipartFile imagen
    ) {
        if (imagen == null || imagen.isEmpty()) {
            throw new ProductBadRequestException("La imagen es obligatoria.");
        }
        String contentType = imagen.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ProductBadRequestException("El archivo debe ser una imagen (image/*).");
        }

        try {
            CreateProductRequestDTO dto = objectMapper.readValue(productoJson, CreateProductRequestDTO.class);

            String urlImagen = cloudinaryService.subirImagen(imagen);

            Products creado = productService.crearProductoConRelaciones(dto, urlImagen);

            ProductResponseDTO resp = new ProductResponseDTO();
            resp.setId(creado.getId());
            resp.setSku(creado.getSku());
            resp.setNombre(creado.getNombre());
            resp.setDescripcion(creado.getDescripcion());
            resp.setPrecio(creado.getPrecio());
            resp.setImgUrl(creado.getImgUrl());
            resp.setEstadoProducto(creado.getEstadoProducto());
            resp.setEstatus(creado.getEstatus());

            resp.setStockTotal(0);

            resp.setCategoriaNombres(
                    creado.getCategorias() == null ? java.util.List.of()
                            : creado.getCategorias().stream()
                            .map(c -> c.getNombreCategoria())
                            .toList()
            );

            resp.setEtiquetaNombres(
                    creado.getEtiquetas() == null ? java.util.List.of()
                            : creado.getEtiquetas().stream()
                            .map(e -> e.getNombreEtiqueta())
                            .toList()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(resp);


        } catch (Exception e) {
            throw new ProductBadRequestException("No se pudo procesar el producto o la imagen: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/categorias-etiquetas")
    public ResponseEntity<ProductResponseDTO> actualizarCategoriasEtiquetas(
            @PathVariable Long id,
            @RequestBody CreateProductRequestDTO dto
    ) {
        Products actualizado = productService.actualizarCategoriasEtiquetas(id, dto.getCategoriaIds(), dto.getEtiquetaIds());

        ProductResponseDTO resp = new ProductResponseDTO();
        resp.setId(actualizado.getId());
        resp.setSku(actualizado.getSku());
        resp.setNombre(actualizado.getNombre());
        resp.setDescripcion(actualizado.getDescripcion());
        resp.setPrecio(actualizado.getPrecio());
        resp.setImgUrl(actualizado.getImgUrl());
        resp.setEstadoProducto(actualizado.getEstadoProducto());
        resp.setEstatus(actualizado.getEstatus());
        resp.setStockTotal(0);

        resp.setCategoriaNombres(
                actualizado.getCategorias() == null ? java.util.List.of()
                        : actualizado.getCategorias().stream().map(c -> c.getNombreCategoria()).toList()
        );
        resp.setEtiquetaNombres(
                actualizado.getEtiquetas() == null ? java.util.List.of()
                        : actualizado.getEtiquetas().stream().map(e -> e.getNombreEtiqueta()).toList()
        );

        return ResponseEntity.ok(resp);
    }



    @GetMapping("/admin")
    public ResponseEntity<List<ProductListDTO>> listarTodos() {
        return ResponseEntity.ok(productService.listarTodosConStock());
    }

    @GetMapping("/catalogo")
    public ResponseEntity<List<CatalogProductDTO>> catalogo() {
        return ResponseEntity.ok(productService.listarCatalogo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> obtenerProductoId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.obtenerProductoConStock(id));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Products> cambiarEstado(@PathVariable Long id, @RequestBody ProductEstadoRequestDTO body) {
        if (body == null || body.getEstadoProducto() == null) {
            throw new ProductBadRequestException("Body inválido. Se requiere: estadoProducto.");
        }

        Products actualizado = productService.cambiarEstadoProducto(id, body.getEstadoProducto());
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public ResponseEntity<List<ProductListDTO>> listarActivos() {
        return ResponseEntity.ok(productService.listarActivosConStock());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> actualizar(@PathVariable Long id, @RequestBody Products products){
        Products actualizado = productService.actualizarProducto(id, products);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto (@PathVariable Long id){
        productService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
    //eliminarcategoria
    @DeleteMapping("/{productoId}/categoria/{categoriaId}")
    public ResponseEntity<Void> eliminarCategoriaDeProducto(
            @PathVariable Long productoId,
            @PathVariable Long categoriaId) {

        productService.eliminarCategoria(productoId, categoriaId);
        return ResponseEntity.noContent().build();
    }
    //eliminaretiqueta
    @DeleteMapping("/{productoId}/etiquetas/{etiquetaId}")
    public ResponseEntity<Void> eliminarEtiquetaDeProducto(
            @PathVariable Long productoId,
            @PathVariable Long etiquetaId) {

        productService.eliminarEtiqueta(productoId, etiquetaId);
        return ResponseEntity.noContent().build();
    }
    //
    // AGREGAR CATEGORIA
    @PostMapping("/{productoId}/categoria/{categoriaId}")
    public ResponseEntity<Void> agregarCategoria(
            @PathVariable Long productoId,
            @PathVariable Long categoriaId) {

        productService.agregarCategoria(productoId, categoriaId);
        return ResponseEntity.ok().build();
    }

    // AGREGAR ETIQUETA
    @PostMapping("/{productoId}/etiquetas/{etiquetaId}")
    public ResponseEntity<Void> agregarEtiqueta(
            @PathVariable Long productoId,
            @PathVariable Long etiquetaId) {

        productService.agregarEtiqueta(productoId, etiquetaId);
        return ResponseEntity.ok().build();
    }
}
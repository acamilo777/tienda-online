package com.tienda.productos.controller;

import com.tienda.productos.entities.Producto;
import com.tienda.productos.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener lista de productos", description = "Devuelve todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos retornada correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)))
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/vista-productos")
    public String mostrarProductosEnVista(Model model) {
        List<Producto> lista = productoService.obtenerTodos();
        model.addAttribute("productos", lista);
        return "productos";  // Asegúrate de tener esta vista Thymeleaf
    }

    @Operation(summary = "Obtener producto por Id", description = "Obtiene el detalle de un producto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class)))
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto creado = productoService.guardarProducto(producto);
        return ResponseEntity.status(201).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detalles) {
        Optional<Producto> optional = productoService.obtenerPorId(id);
        if (!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Producto producto = optional.get();
        producto.setNombre(detalles.getNombre());
        producto.setPrecio(detalles.getPrecio());
        producto.setStock(detalles.getStock());

        Producto actualizado = productoService.guardarProducto(producto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}

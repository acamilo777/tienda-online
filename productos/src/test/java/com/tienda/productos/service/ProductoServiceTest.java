package com.tienda.productos.service;

import com.tienda.productos.entities.Producto;
import com.tienda.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto crearProductoFalso() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Perfume");
        producto.setPrecio(12000.0);
        producto.setStock(10);
        return producto;
    }

    @Test
    void testObtenerTodos() {
        Producto producto = crearProductoFalso();
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));

        List<Producto> productos = productoService.obtenerTodos();

        assertEquals(1, productos.size());
        assertEquals("Perfume", productos.get(0).getNombre());
        verify(productoRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Producto producto = crearProductoFalso();
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> encontrado = productoService.obtenerPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals("Perfume", encontrado.get().getNombre());
        verify(productoRepository).findById(1L);
    }

    @Test
    void testGuardarProducto() {
        Producto producto = crearProductoFalso();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto guardado = productoService.guardarProducto(producto);

        assertNotNull(guardado);
        assertEquals("Perfume", guardado.getNombre());
        verify(productoRepository).save(producto);
    }

    @Test
    void testEliminarProducto() {
        Long id = 1L;

        productoService.eliminarProducto(id);

        verify(productoRepository).deleteById(id);
    }
}

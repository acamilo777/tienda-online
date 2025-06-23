package com.tienda.productos.restcontrollers;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.productos.entities.Producto;
import com.tienda.productos.service.ProductoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoRestControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoService productoService;

    private List<Producto> productosLista;

    @Test
    public void verProductosTest() throws Exception {
        when(productoService.obtenerTodos()).thenReturn(productosLista);

        mockMvc.perform(get("/productos").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void verunProductoTest() {
        Producto unProducto = new Producto(1L, "Carolina Herrera", 30000.00, 200);
        try {
            when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(unProducto));
            mockMvc.perform(get("/productos/1").accept(MediaType.APPLICATION_JSON))
                   .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzó un error: " + ex.getMessage());
        }
    }

    @Test
    public void productoNoExisteTest() throws Exception {
        when(productoService.obtenerPorId(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/productos/10").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void crearProductoTest() throws Exception {
        Producto unProducto = new Producto(null, "Paco Rabanne", 20000.0, 300);
        Producto otroProducto = new Producto(4L, "Antonio Banderas", 25000.0, 350);
        when(productoService.guardarProducto(any(Producto.class))).thenReturn(otroProducto);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unProducto)))
                .andExpect(status().isCreated());
    }
}

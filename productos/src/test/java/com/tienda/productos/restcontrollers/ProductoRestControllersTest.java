package com.tienda.productos.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.productos.entities.Producto;
import com.tienda.productos.service.ProductoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoRestControllersTest {
    @Autowired 
    private MockMvc mockmvc; 
    @Autowired 
    private ObjectMapper objectMapper; 
    @MockitoBean 
    private ProductoService productoservice; 
    
    private List<Producto> productosLista;

    @Test
    public void verProductosTest() throws Exception{
	    when (productoservice.obtenerTodos()).thenReturn(productosLista);
	    mockmvc.perform(get("/productos"))
	    .contentType(MediaType.APPLICATION_JSON)
	    .andExpect(status().isOk());
    }

    @Test
    public void verunProductoTest(){
	    Producto unProducto = new Producto(1L,"Carolina Herrera",30000.00,200);
	    try{
	    	when(productoservice.obtenerPorId(null)).thenReturn(Optional.of(unProducto));
		    mockmvc.perform(get("api/producto/1")
		    .contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(status().isOk());
	    }catch(Exception ex){
		    fail("El testing lanzó un error :"+ex.getMessage());
	    }
    }

    @Test
    public void productoNoExisteTest() throws Exception{
	    when(productoservice.obtenerPorId(10L)).thenReturn(Optional.empty());
	    mockmvc.perform(get("productos/10")
	    .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isNotFound());
    }

    @Test
    public void crearProductoTest() throws Exception{
	    Producto unProducto = new Producto(null,"Paco Rabanne",20000.0,300);
	    Producto otroProducto = new Producto(4L,"Antonio Banderas",25000.0,350);
	    when(productoservice.guardarProducto(any(Producto.class))).thenReturn(otroProducto);
	    mockmvc.perform(post("/productos")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(objectMapper.writeValueAsString(unProducto)))
	    .andExpect(status().isCreated());
    }

}




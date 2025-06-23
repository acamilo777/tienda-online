package com.tienda.pedidos.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.service.PedidoService;
import com.tienda.pedidos.controller.PedidoController;  // IMPORT CORRECTO

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

public class PedidoRestControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();  // puede ser final

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoController pedidoController;  // paquete corregido

    private final List<Pedido> pedidosLista = List.of();  // final y vacía para evitar null

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    public void verPedidosTest() throws Exception {
        when(pedidoService.obtenerTodos()).thenReturn(pedidosLista);
        mockMvc.perform(get("/pedidos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void verUnPedidoTest() {
        Pedido unPedido = new Pedido(1L, 1L, 30L, 4);
        try {
            when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(unPedido));
            mockMvc.perform(get("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzó un error :" + ex.getMessage());
        }
    }

    @Test
    public void pedidoNoExisteTest() throws Exception {
        when(pedidoService.obtenerPorId(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/pedidos/10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void crearProductoTest() throws Exception {
        Pedido unPedido = new Pedido(null, 1L, 20L, 300);
        Pedido otroPedido = new Pedido(4L, 2L, 30L, 350);
        when(pedidoService.guardarPedido(any(Pedido.class))).thenReturn(otroPedido);
        mockMvc.perform(post("/pedidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unPedido)))
            .andExpect(status().isCreated());
    }
}

package com.tienda.pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private com.tienda.pedidos.controller.PedidoController pedidoController;

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
    }

    @Test
    public void listarPedidosTest() throws Exception {
        Pedido pedido = new Pedido(1L, 1L, 2L, 5);
        when(pedidoService.obtenerTodos()).thenReturn(Arrays.asList(pedido));

        mockMvc.perform(get("/pedidos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void obtenerPedidoPorIdTest() throws Exception {
        Pedido pedido = new Pedido(1L, 1L, 2L, 5);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void obtenerPedidoNoExisteTest() throws Exception {
        when(pedidoService.obtenerPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/pedidos/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearPedidoTest() throws Exception {
        Pedido pedidoInput = new Pedido(null, 1L, 2L, 5);
        Pedido pedidoGuardado = new Pedido(1L, 1L, 2L, 5);

        when(pedidoService.guardarPedido(any(Pedido.class))).thenReturn(pedidoGuardado);

        mockMvc.perform(post("/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoInput)))
                .andExpect(status().isCreated());
    }

    @Test
    public void actualizarPedidoTest() throws Exception {
        Pedido pedidoExistente = new Pedido(1L, 1L, 2L, 5);
        Pedido pedidoActualizado = new Pedido(1L, 1L, 2L, 10);

        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedidoExistente));
        when(pedidoService.guardarPedido(any(Pedido.class))).thenReturn(pedidoActualizado);

        mockMvc.perform(put("/pedidos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoActualizado)))
                .andExpect(status().isOk());
    }

    @Test
    public void eliminarPedidoTest() throws Exception {
        mockMvc.perform(delete("/pedidos/1"))
                .andExpect(status().isNoContent());
    }
}

package com.tienda.pedidos.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {
    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido crearPedidoFalso() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setCantidad(3);
        pedido.setProductoId(23L);
        pedido.setUsuarioId(1L);

        return pedido;
    }

    @Test
    void testObtenerTodos() {
        Pedido pedido = crearPedidoFalso();
        when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));

        List<Pedido> pedidos = pedidoService.obtenerTodos();

        assertEquals(1, pedidos.size());
        assertEquals(1L, pedidos.get(0).getId());
        verify(pedidoRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Pedido pedido = crearPedidoFalso();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Optional<Pedido> encontrado = pedidoService.obtenerPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals(1L, encontrado.get().getId());
        verify(pedidoRepository).findById(1L);
    }

    @Test
    void testGuardarPedido() {
        Pedido pedido = crearPedidoFalso();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido guardado = pedidoService.guardarPedido(pedido);

        assertNotNull(guardado);
        assertEquals(1L, guardado.getId());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testEliminarPedido() { 
        Long id = 1L;
        pedidoService.eliminarPedido(id);
        verify(pedidoRepository).deleteById(id);
}
}




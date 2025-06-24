package com.tienda.pedidos.controller;

import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable Long id, @RequestBody Pedido detalles) {
        Optional<Pedido> pedidoOptional = pedidoService.obtenerPorId(id);
        if (!pedidoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setUsuarioId(detalles.getUsuarioId());
        pedido.setProductoId(detalles.getProductoId());
        pedido.setCantidad(detalles.getCantidad());

        Pedido actualizado = pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}

package com.tienda.pedidos.controller;

import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary="Obtener lista de pedidos",description="Devuelve todos los pedidos disponibles")
    @ApiResponse(responseCode = "200", description="Lista de pedidos retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema= @Schema(implementation = Pedido.class)))
    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/vista-pedidos")
    public String mostrarPedidosEnVista(Model model) {
        List<Pedido> lista = pedidoService.obtenerTodos();
        model.addAttribute("pedidos", lista);
        return "pedidos";
    }

    @Operation(summary="Obtener pedido por Id", description="Obtiene el detalle de un pedido especifico")
    @ApiResponses(value = {
        @ApiResponse(responseCode="200", description = "Pedido encontrado",
            content=@Content(mediaType="application/json",schema=@Schema(implementation=Pedido.class))),
        @ApiResponse(responseCode = "404",description="Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoService.obtenerPorId(id);
        return pedido.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary ="Crear un nuevo pedido",description="Crea un pedido con los datos proporcionados")
    @ApiResponse(responseCode="201", description="Pedido creado correctamente",
        content=@Content(mediaType = "application/json", schema=@Schema(implementation = Pedido.class)))
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
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

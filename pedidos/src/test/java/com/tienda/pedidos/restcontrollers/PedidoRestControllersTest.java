package com.tienda.pedidos.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.pedidos.entities.Pedido;
import com.tienda.pedidos.service.PedidoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;


public class PedidoRestControllersTest {
    @Autowired 
    private MockMvc mockmvc; 
    
    @Autowired 
    private ObjectMapper objectMapper; 
    
    @MockitoBean 
    private PedidoService pedidoservice; 
    private List<Pedido> pedidosLista;

    @Test
    public void verPedidosTest() throws Exception{
	    when (pedidoservice.obtenerTodos()).thenReturn(pedidosLista);
	    mockmvc.perform(get("/pedidos")
	    .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isOk());    
    }

    @Test
    public void verUnPedidoTest(){
	    Pedido unPedido = new Pedido(1L,1L,30L,4);
	    try{
		    when(pedidoservice.obtenerPorId(1L)).thenReturn(Optional.of(unPedido));
	    	mockmvc.perform(get("/pedidos/1")
	    	.contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk());
    	}catch(Exception ex){
    		fail("El testing lanzó un error :"+ex.getMessage());
    	}
    }

    @Test
    public void pedidoNoExisteTest() throws Exception{
	    when(pedidoservice.obtenerPorId(10L)).thenReturn(Optional.empty());
	    mockmvc.perform(get("/pedidos/10")
	    .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isNotFound());
    }

    @Test
    public void crearProductoTest() throws Exception{
	    Pedido unPedido = new Pedido(null,1L,20L,300);
	    Pedido otroPedido = new Pedido(4L,2L,30L,350);
	    when(pedidoservice.guardarPedido(any(Pedido.class))).thenReturn(otroPedido);
	    mockmvc.perform(post("/pedidos")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(objectMapper.writeValueAsString(unPedido)))
	    .andExpect(status().isCreated());
    }
}

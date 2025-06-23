package com.tienda.usuarios.restcontrollers;

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
import com.tienda.usuarios.entities.Usuario;
import com.tienda.usuarios.service.UsuarioService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;




@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired 
    private MockMvc mockmvc; 
    
    @Autowired 
    private ObjectMapper objectMapper; 
   
    
    @MockitoBean 
    private UsuarioService usuarioservice;
    
    private List<Usuario> usuariosLista;
    

    @Test
    public void verUsuariosTest() throws Exception{
        when(usuarioservice.obtenerTodos()).thenReturn(usuariosLista);
        mockmvc.perform(get("/usuarios")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());    
    
    }

    //Test mostrar objeto por id
    @Test
    public void verunUsuarioTest(){
	    Usuario unUsuario = new Usuario(1L,"Jorge Loyola", "gualoyola@chile.cl");
	    try{
		    when(usuarioservice.obtenerPorId(1L)).thenReturn(Optional.of(unUsuario));
		    mockmvc.perform(get("usuarios/1")
		    .contentType(MediaType.APPLICATION_JSON))
		    .andExpect(status().isOk());
	    }catch(Exception ex){
		    fail("El testing lanzó un error :"+ex.getMessage());
	    }
    }

    //test de objeto no existente
    @Test
    public void usuarioNoExisteTest() throws Exception{
	    when(usuarioservice.obtenerPorId(10L)).thenReturn(Optional.empty());
	    mockmvc.perform(get("usuarios/10")
	    .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isNotFound());  
    }

    @Test
    public void crearUsuariooTest() throws Exception{
	    Usuario unUsuario = new Usuario(null,"Juan Soto","correo@yahoo.cl");
	    Usuario otroUsuario = new Usuario(4L,"Nidia Olea","correo@hotmail.com");
	    when(usuarioservice.guardarUsuario(any(Usuario.class))).thenReturn(otroUsuario);
	    mockmvc.perform(post("usuarios")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(objectMapper.writeValueAsString(unUsuario)))
	    .andExpect(status().isCreated());
    }
    
    
}    

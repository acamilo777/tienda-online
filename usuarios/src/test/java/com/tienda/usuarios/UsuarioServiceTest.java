package com.tienda.usuarios;

import com.tienda.usuarios.entities.Usuario;
import com.tienda.usuarios.repository.UsuarioRepository;
import com.tienda.usuarios.service.UsuarioService;

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
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario crearUsuarioFalso() {
        Usuario usuario = new Usuario(1L,"Pepito Perez","peperez@duocuc.cl");
        return usuario;
       
    }
    @Test
    void testObtenerTodos() {
        Usuario usuario = crearUsuarioFalso();
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertEquals(1, usuarios.size());
        assertEquals("Pepito Perez", usuarios.get(0).getNombre());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testObtenerPorId() {
        Usuario usuario = crearUsuarioFalso();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> encontrado = usuarioService.obtenerPorId(1L);

        assertTrue(encontrado.isPresent());
        assertEquals("Pepito Perez", encontrado.get().getNombre());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testGuardarUsuario() {
        Usuario usuario = crearUsuarioFalso();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario guardado = usuarioService.guardarUsuario(usuario);

        assertNotNull(guardado);
        assertEquals("Pepito Perez", guardado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void testEliminarUsuario() {
        Long id = 1L;

        usuarioService.eliminarUsuario(id);

        verify(usuarioRepository).deleteById(id);
    }

}


    
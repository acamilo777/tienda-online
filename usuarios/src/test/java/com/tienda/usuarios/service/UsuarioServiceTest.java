package com.tienda.usuarios.service;

import com.tienda.usuarios.entities.Usuario;
import com.tienda.usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testObtenerTodos() {
        Usuario u = new Usuario();
        u.setNombre("Camilo");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u));

        List<Usuario> lista = usuarioService.obtenerTodos();
        assertEquals(1, lista.size());
    }

    @Test
    void testGuardarUsuario() {
        Usuario u = new Usuario();
        u.setNombre("Nuevo");
        when(usuarioRepository.save(u)).thenReturn(u);

        Usuario guardado = usuarioService.guardarUsuario(u);
        assertEquals("Nuevo", guardado.getNombre());
    }
}

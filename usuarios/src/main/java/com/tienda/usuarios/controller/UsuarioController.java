package com.tienda.usuarios.controller;

import com.tienda.usuarios.entities.Usuario;
import com.tienda.usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary="Obtener lista de usuarios",description="Devuelve todos los usuarios disponibles")
    @ApiResponse(responseCode = "200", description="Lista de usuarios retornada correctamente",
	content = @Content(mediaType = "application/json",schema= @Schema(implementation = Usuario.class)))
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }
     
    @GetMapping("/vista-usuarios")
    public String mostrarUsuariosEnVista(Model model) {
    List<Usuario> lista = usuarioService.obtenerTodos();
    model.addAttribute("usuarios", lista);
    return "usuarios"; // nombre del archivo HTML (usuarios.html)
    }


    @Operation(summary="Obtener usuario por Id", description="Obtiene el detalle de un usuario especifico")
    @ApiResponses(value = {
	    @ApiResponse(responseCode="200", description = "Usuario encontrado",
		    content=@Content(mediaType="application/json",schema=@Schema(implementation=Usuario.class))),
    	@ApiResponse(responseCode = "404",description="Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
   
    }

    @Operation(summary ="Crear un nuevo usuario",description="Crea un usuario con los datos proporcionados")
    @ApiResponse(responseCode="201", description="Usuario creado correctamente",
	    content=@Content(mediaType = "application/json", schema=@Schema(implementation = Usuario.class)))
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        Optional<Usuario> usuarioOptional = usuarioService.obtenerPorId(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = usuarioOptional.get();
        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setEmail(usuarioDetalles.getEmail());
        Usuario usuarioActualizado = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

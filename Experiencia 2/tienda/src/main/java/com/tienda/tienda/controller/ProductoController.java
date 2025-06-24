package com.tienda.tienda.controller;

import com.tienda.tienda.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductoController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${productos.api.url}")
    private String productosApiUrl;

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        Producto[] productos = restTemplate.getForObject(productosApiUrl, Producto[].class);
        List<Producto> lista = Arrays.asList(productos);
        model.addAttribute("productos", lista);
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/productos")
    public String guardarProducto(@ModelAttribute Producto producto) {
        restTemplate.postForObject(productosApiUrl, producto, Producto.class);
        return "redirect:/productos";
    }
}

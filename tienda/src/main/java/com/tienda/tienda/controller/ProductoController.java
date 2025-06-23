package com.tienda.tienda.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tienda.tienda.config.ApiUrlsProperties;
import com.tienda.tienda.model.Producto;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final RestTemplate restTemplate;
    private final ApiUrlsProperties apiUrlsProperties;

    public ProductoController(RestTemplate restTemplate, ApiUrlsProperties apiUrlsProperties) {
        this.restTemplate = restTemplate;
        this.apiUrlsProperties = apiUrlsProperties;
    }

    @GetMapping
    public String listarProductos(Model model) {
        try {
            Producto[] productos = restTemplate.getForObject(apiUrlsProperties.getProductos(), Producto[].class);
            List<Producto> lista = Arrays.asList(productos);
            model.addAttribute("productos", lista);
        } catch (RestClientException e) {
            logger.error("Error al obtener productos", e);
            model.addAttribute("error", "No se pudo obtener la lista de productos");
        }
        return "productos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping
    public String guardarProducto(@ModelAttribute Producto producto, Model model) {
        try {
            restTemplate.postForObject(apiUrlsProperties.getProductos(), producto, Producto.class);
        } catch (RestClientException e) {
            logger.error("Error al guardar producto", e);
            model.addAttribute("error", "No se pudo guardar el producto");
            return "formulario"; // Regresa al formulario en caso de error
        }
        return "redirect:/productos";
    }
}

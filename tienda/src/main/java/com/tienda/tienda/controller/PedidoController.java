package com.tienda.tienda.controller;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tienda.tienda.config.ApiUrlsProperties;

import java.util.List;
import java.util.Map;

@RestController
public class PedidoController {

    private final RestTemplate restTemplate;
    private final ApiUrlsProperties apiUrlsProperties;

    public PedidoController(RestTemplate restTemplate, ApiUrlsProperties apiUrlsProperties) {
        this.restTemplate = restTemplate;
        this.apiUrlsProperties = apiUrlsProperties;
    }

    @GetMapping("/pedidos")
    public List<Map<String, Object>> obtenerPedidos() {
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            apiUrlsProperties.getPedidos(),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );
        return response.getBody();
    }
}

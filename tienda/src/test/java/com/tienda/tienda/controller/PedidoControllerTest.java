package com.tienda.tienda.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(controllers = PedidoController.class)
@Import(PedidoControllerTest.Config.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
    @SuppressWarnings("unused")
    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testObtenerPedidos() throws Exception {
        String json = "[{\"id\":1,\"usuarioId\":2,\"productoId\":3,\"cantidad\":5}]";

        mockServer.expect(once(), requestTo("http://localhost:8083/pedidos"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cantidad").value(5));

        mockServer.verify();
    }
}

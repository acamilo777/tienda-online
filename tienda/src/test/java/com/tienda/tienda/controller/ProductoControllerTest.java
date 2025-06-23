package com.tienda.tienda.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = ProductoController.class)
@Import(ProductoControllerTest.Config.class)
public class ProductoControllerTest {

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
    void testListarProductos() throws Exception {
        String json = "[{\"id\":1,\"nombre\":\"Producto A\",\"precio\":10.5,\"stock\":100}]";

        mockServer.expect(once(), requestTo("http://localhost:8081/productos"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Producto A")));

        mockServer.verify();
    }
}

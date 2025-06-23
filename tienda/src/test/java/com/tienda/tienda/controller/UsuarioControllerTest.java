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

import static org.hamcrest.Matchers.hasSize;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(controllers = UsuarioController.class)
@Import(UsuarioControllerTest.Config.class)
public class UsuarioControllerTest {

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

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testObtenerUsuarios() throws Exception {
        String json = "[{\"id\":1,\"nombre\":\"Juan\"}]";

        mockServer.expect(once(), requestTo("http://localhost:8082/usuarios"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre").value("Juan"));

        mockServer.verify();
    }
}

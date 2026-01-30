package com.amaraltech.xbrain_tech_test.controllers;

import com.amaraltech.xbrain_tech_test.model.Venda;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void criarVendaTest() throws Exception {
        Venda venda = new Venda(
                LocalDate.now(),
                1500.0,
                1L,
                "João Silva"
        );

        mockMvc.perform(post("/api/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(venda)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendedorNome").value("João Silva"));
    }

    @Test
    void getRelatorioVendedoresTest() throws Exception {
        String inicio = LocalDate.now().minusDays(30).toString();
        String fim = LocalDate.now().toString();

        mockMvc.perform(get("/api/vendas/relatorio-vendedores")
                        .param("inicio", inicio)
                        .param("fim", fim))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
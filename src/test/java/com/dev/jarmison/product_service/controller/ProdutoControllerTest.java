package com.dev.jarmison.product_service.controller;

import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
@DisplayName("Testes para o ProdutoController")
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar um novo produto e retornar 201 Created")
    void deveCriarProdutoERetornarStatus201() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Teclado Mecânico");
        produtoDTO.setPreco(new BigDecimal("350.00"));

        ProdutoDTO produtoCriado = new ProdutoDTO();
        produtoCriado.setId(1L);
        produtoCriado.setNome("Teclado Mecânico");

        when(produtoService.criarProduto(any(ProdutoDTO.class))).thenReturn(produtoCriado);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produtoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Teclado Mecânico"));
    }

    @Test
    @DisplayName("Deve retornar um produto por ID e status 200 OK")
    void deveRetornarProdutoPorIdEStatus200() throws Exception {
        ProdutoDTO produtoEncontrado = new ProdutoDTO();
        produtoEncontrado.setId(1L);
        produtoEncontrado.setNome("Teclado Mecânico");

        when(produtoService.buscarPorId(anyLong())).thenReturn(produtoEncontrado);

        mockMvc.perform(get("/api/produtos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Teclado Mecânico"));
    }

    @Test
    @DisplayName("Deve retornar todos os produtos e status 200 OK")
    void deveRetornarTodosOsProdutos() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Mouse");

        when(produtoService.buscarTodos()).thenReturn(Collections.singletonList(produtoDTO));

        mockMvc.perform(get("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Mouse"));
    }

    @Test
    @DisplayName("Deve deletar um produto e retornar status 204 No Content")
    void deveDeletarProdutoERetornarStatus204() throws Exception {
        doNothing().when(produtoService).deletarProduto(anyLong());

        mockMvc.perform(delete("/api/produtos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
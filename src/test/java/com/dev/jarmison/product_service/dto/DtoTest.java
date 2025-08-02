package com.dev.jarmison.product_service.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para as classes DTO")
class DtoTest {

    @Test
    @DisplayName("AuditoriaProdutoDTO deve ter getters, setters e construtores corretos")
    void auditoriaProdutoDTOTest() {
        AuditoriaProdutoDTO auditoriaProdutoDTO = new AuditoriaProdutoDTO();
        assertNotNull(auditoriaProdutoDTO);

        Long produtoId = 1L;
        String acao = "CRIACAO";
        String detalhes = "Produto criado para teste";
        LocalDateTime dataAcao = LocalDateTime.of(2025, 8, 2, 10, 0);

        auditoriaProdutoDTO.setProdutoId(produtoId);
        auditoriaProdutoDTO.setAcao(acao);
        auditoriaProdutoDTO.setDetalhes(detalhes);
        auditoriaProdutoDTO.setDataAcao(dataAcao);

        assertEquals(produtoId, auditoriaProdutoDTO.getProdutoId());
        assertEquals(acao, auditoriaProdutoDTO.getAcao());
        assertEquals(detalhes, auditoriaProdutoDTO.getDetalhes());
        assertEquals(dataAcao, auditoriaProdutoDTO.getDataAcao());

        AuditoriaProdutoDTO auditoriaCompleta = new AuditoriaProdutoDTO(2L, "ATUALIZACAO", "Produto atualizado para teste");
        assertNotNull(auditoriaCompleta);
        assertEquals(2L, auditoriaCompleta.getProdutoId());
        assertEquals("ATUALIZACAO", auditoriaCompleta.getAcao());
    }

    @Test
    @DisplayName("ProdutoDTO deve ter getters e setters corretos")
    void produtoDTOTest() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        assertNotNull(produtoDTO);

        Long id = 10L;
        String nome = "Notebook";
        String descricao = "Notebook para trabalho";
        BigDecimal preco = new BigDecimal("4500.00");
        Integer quantidadeEmEstoque = 50;
        Integer limiteMinimoEstoque = 10;

        produtoDTO.setId(id);
        produtoDTO.setNome(nome);
        produtoDTO.setDescricao(descricao);
        produtoDTO.setPreco(preco);
        produtoDTO.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produtoDTO.setLimiteMinimoEstoque(limiteMinimoEstoque);

        assertEquals(id, produtoDTO.getId());
        assertEquals(nome, produtoDTO.getNome());
        assertEquals(descricao, produtoDTO.getDescricao());
        assertEquals(preco, produtoDTO.getPreco());
        assertEquals(quantidadeEmEstoque, produtoDTO.getQuantidadeEmEstoque());
        assertEquals(limiteMinimoEstoque, produtoDTO.getLimiteMinimoEstoque());
    }
}
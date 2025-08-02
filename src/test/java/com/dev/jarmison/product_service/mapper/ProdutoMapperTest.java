package com.dev.jarmison.product_service.mapper;

import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testes para o Mapper de Produto")
class ProdutoMapperTest {

    private final ProdutoMapper mapper = Mappers.getMapper(ProdutoMapper.class);

    @Test
    @DisplayName("Deve converter a entidade Produto para ProdutoDTO")
    void deveConverterEntidadeParaDto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Monitor Ultrawide");
        produto.setDescricao("Monitor para gamers");
        produto.setPreco(new BigDecimal("2500.00"));
        produto.setQuantidadeEmEstoque(50);
        produto.setLimiteMinimoEstoque(10);

        ProdutoDTO produtoDTO = mapper.toDTO(produto);

        assertNotNull(produtoDTO);
        assertEquals(produto.getId(), produtoDTO.getId());
        assertEquals(produto.getNome(), produtoDTO.getNome());
        assertEquals(produto.getDescricao(), produtoDTO.getDescricao());
        assertEquals(produto.getPreco(), produtoDTO.getPreco());
        assertEquals(produto.getQuantidadeEmEstoque(), produtoDTO.getQuantidadeEmEstoque());
        assertEquals(produto.getLimiteMinimoEstoque(), produtoDTO.getLimiteMinimoEstoque());
    }

    @Test
    @DisplayName("Deve converter o DTO ProdutoDTO para a entidade Produto")
    void deveConverterDtoParaEntidade() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(2L);
        produtoDTO.setNome("Teclado Mecânico");
        produtoDTO.setDescricao("Teclado RGB para digitação");
        produtoDTO.setPreco(new BigDecimal("400.00"));
        produtoDTO.setQuantidadeEmEstoque(100);
        produtoDTO.setLimiteMinimoEstoque(20);

        Produto produto = mapper.toEntity(produtoDTO);

        assertNotNull(produto);
        assertEquals(produtoDTO.getId(), produto.getId());
        assertEquals(produtoDTO.getNome(), produto.getNome());
        assertEquals(produtoDTO.getDescricao(), produto.getDescricao());
        assertEquals(produtoDTO.getPreco(), produto.getPreco());
        assertEquals(produtoDTO.getQuantidadeEmEstoque(), produto.getQuantidadeEmEstoque());
        assertEquals(produtoDTO.getLimiteMinimoEstoque(), produto.getLimiteMinimoEstoque());
    }
}
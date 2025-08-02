package com.dev.jarmison.product_service.service;

import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.exception.RecursoNaoEncontradoException;
import com.dev.jarmison.product_service.mapper.ProdutoMapper;
import com.dev.jarmison.product_service.model.Produto;
import com.dev.jarmison.product_service.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o Serviço de Produto")
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Mouse Gamer");
        produto.setPreco(new BigDecimal("200.00"));

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Mouse Gamer");
        produtoDTO.setPreco(new BigDecimal("200.00"));
    }

    @Test
    @DisplayName("Deve criar um produto com sucesso")
    void deveCriarProdutoComSucesso() {
        when(produtoMapper.toEntity(any(ProdutoDTO.class))).thenReturn(produto);
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO produtoCriado = produtoService.criarProduto(produtoDTO);

        assertNotNull(produtoCriado);
        assertEquals(produtoCriado.getNome(), produtoDTO.getNome());
    }

    @Test
    @DisplayName("Deve buscar um produto por ID com sucesso")
    void deveBuscarProdutoPorIdComSucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(produtoMapper.toDTO(any(Produto.class))).thenReturn(produtoDTO);

        ProdutoDTO produtoEncontrado = produtoService.buscarPorId(1L);

        assertNotNull(produtoEncontrado);
        assertEquals(1L, produtoEncontrado.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar produto com ID inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.buscarPorId(1L));
    }
}
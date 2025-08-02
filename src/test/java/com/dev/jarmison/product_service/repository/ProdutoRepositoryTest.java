package com.dev.jarmison.product_service.repository;

import com.dev.jarmison.product_service.model.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Testes para o Repositório de Produto")
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Deve salvar um produto no banco de dados")
    void deveSalvarProduto() {
        Produto produto = new Produto();
        produto.setNome("Mouse");
        produto.setPreco(new BigDecimal("150.00"));
        produto.setQuantidadeEmEstoque(100);

        Produto produtoSalvo = produtoRepository.save(produto);

        assertNotNull(produtoSalvo.getId());
        assertEquals("Mouse", produtoSalvo.getNome());
    }

    @Test
    @DisplayName("Deve buscar um produto por ID")
    void deveBuscarProdutoPorId() {
        Produto produto = new Produto();
        produto.setNome("Teclado");
        produto.setPreco(new BigDecimal("300.00"));
        produto.setQuantidadeEmEstoque(50);
        Produto produtoSalvo = produtoRepository.save(produto);

        Optional<Produto> produtoEncontrado = produtoRepository.findById(produtoSalvo.getId());

        assertTrue(produtoEncontrado.isPresent());
        assertEquals("Teclado", produtoEncontrado.get().getNome());
    }

    @Test
    @DisplayName("Deve retornar todos os produtos")
    void deveRetornarTodosOsProdutos() {
        Produto p1 = new Produto();
        p1.setNome("Monitor");
        p1.setPreco(new BigDecimal("1500.00"));
        produtoRepository.save(p1);

        Produto p2 = new Produto();
        p2.setNome("Webcam");
        p2.setPreco(new BigDecimal("250.00"));
        produtoRepository.save(p2);

        List<Produto> produtos = produtoRepository.findAll();

        assertEquals(2, produtos.size());
    }

    @Test
    @DisplayName("Deve deletar um produto por ID")
    void deveDeletarProduto() {
        Produto produto = new Produto();
        produto.setNome("Gabinete");
        produto.setPreco(new BigDecimal("500.00"));
        Produto produtoSalvo = produtoRepository.save(produto);

        produtoRepository.deleteById(produtoSalvo.getId());

        Optional<Produto> produtoDeletado = produtoRepository.findById(produtoSalvo.getId());
        assertFalse(produtoDeletado.isPresent());
    }
}
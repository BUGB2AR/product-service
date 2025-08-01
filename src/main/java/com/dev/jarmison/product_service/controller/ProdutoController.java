package com.dev.jarmison.product_service.controller;


import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.criarProduto(produtoDTO);
        return ResponseEntity.created(URI.create("/api/produtos/" + novoProduto.getId())).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> buscarTodos() {
        log.info("Requisição GET para buscar todos os produtos.");
        List<ProdutoDTO> produtos = produtoService.buscarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        log.info("[product-service:] Requisição PUT para atualizar o produto com ID: {}", id);
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        log.info("Requisição DELETE para deletar o produto com ID: {}", id);
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}

package com.dev.jarmison.product_service.service;


import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.exception.RecursoNaoEncontradoException;
import com.dev.jarmison.product_service.mapper.ProdutoMapper;
import com.dev.jarmison.product_service.model.Produto;
import com.dev.jarmison.product_service.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProdutoService {
    private static final Logger log = LoggerFactory.getLogger(ProdutoService.class);
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional
    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        log.info("[produto-service]: Iniciando a criação de um novo produto: {}", produtoDTO.getNome());
        Produto produto = produtoMapper.toEntity(produtoDTO);
        Produto savedProduto = produtoRepository.save(produto);
        return produtoMapper.toDTO(savedProduto);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        log.info("[produto-service]: Iniciando atualização do produto com ID: {}", id);
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com ID: " + id));

        BeanUtils.copyProperties(produtoDTO, produtoExistente, "id");

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        log.info("[produto-service]: Produto com ID {} atualizado com sucesso.", id);
        return produtoMapper.toDTO(produtoAtualizado);
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarTodos() {
        log.info("[produto-service]: Buscando todos os produtos.");
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        log.info("[produto-service]: Buscando produto por ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[produto-service]: Produto não encontrado com ID: {}", id);
                    return new RecursoNaoEncontradoException("[produto-service]: Produto não encontrado com ID: " + id);
                });
        return produtoMapper.toDTO(produto);
    }

    @Transactional
    public void deletarProduto(Long id) {
        log.info("Iniciando a exclusão do produto com ID: {}", id);
        if (!produtoRepository.existsById(id)) {
            log.error("[produto-service]: Tentativa de exclusão falhou: produto não encontrado com ID: {}", id);
            throw new RecursoNaoEncontradoException("[produto-service]:  Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
        log.info("[produto-service]: Produto com ID {} deletado com sucesso.", id);
    }
}
package com.dev.jarmison.product_service.service;


import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.exception.RecursoNaoEncontradoException;
import com.dev.jarmison.product_service.mapper.ProdutoMapper;
import com.dev.jarmison.product_service.model.Produto;
import com.dev.jarmison.product_service.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Transactional
    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoMapper.toEntity(produtoDTO);
        Produto savedProduto = produtoRepository.save(produto);
        return produtoMapper.toDTO(savedProduto);
    }

    @Transactional
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com ID: " + id));

        BeanUtils.copyProperties(produtoDTO, produtoExistente, "id");

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return produtoMapper.toDTO(produtoAtualizado);
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("[produto-service]: Produto não encontrado com ID: " + id));
        return produtoMapper.toDTO(produto);
    }

    @Transactional
    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("[produto-service]:  Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
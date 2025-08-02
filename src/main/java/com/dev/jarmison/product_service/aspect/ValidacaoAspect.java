package com.dev.jarmison.product_service.aspect;

import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.exception.ValidacaoDeCamposException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidacaoAspect {

    private static final Logger log = LoggerFactory.getLogger(ValidacaoAspect.class);

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.criarProduto(..)) || " +
            "execution(* com.dev.jarmison.product_service.service.ProdutoService.atualizarProduto(..))")
    public void validacaoProdutoPointcut() {}

    @Before("validacaoProdutoPointcut()")
    public void validarCampos(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[args.length - 1] instanceof ProdutoDTO) {
            ProdutoDTO produtoDTO = (ProdutoDTO) args[args.length - 1];

            if (produtoDTO.getNome() == null || produtoDTO.getNome().isBlank()) {
                log.error("[product-service]: Validação falhou: o campo 'nome' não pode ser nulo ou vazio.");
                throw new ValidacaoDeCamposException("O campo 'nome' é obrigatório.");
            }
            if (produtoDTO.getPreco() == null) {
                log.error("[product-service]: Validação falhou: o campo 'preço' não pode ser nulo.");
                throw new ValidacaoDeCamposException("O campo 'preço' é obrigatório.");
            }
            if (produtoDTO.getQuantidadeEmEstoque() == null) {
                log.error("[product-service]: Validação falhou: o campo 'quantidadeEmEstoque' não pode ser nulo.");
                throw new ValidacaoDeCamposException("O campo 'quantidadeEmEstoque' é obrigatório.");
            }

            if (produtoDTO.getLimiteMinimoEstoque() == null) {
                log.error("[product-service]: Validação falhou: o campo 'limiteMinimoEstoque' não pode ser nulo.");
                throw new ValidacaoDeCamposException("O campo 'limiteMinimoEstoque' é obrigatório.");
            }
        }
    }
}

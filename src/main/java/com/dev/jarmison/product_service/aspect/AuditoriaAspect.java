package com.dev.jarmison.product_service.aspect;

import com.dev.jarmison.product_service.dto.AuditoriaProdutoDTO;
import com.dev.jarmison.product_service.dto.ProdutoDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Aspect
@Component
public class AuditoriaAspect {
    private static final Logger log = LoggerFactory.getLogger(AuditoriaAspect.class);
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${auditoria.service.url}")
    private String urlAuditoria;

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.criarProduto(..))")
    public void criarProdutoPointcut() {}

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.atualizarProduto(..))")
    public void atualizarProdutoPointcut() {}

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.deletarProduto(..))")
    public void deletarProdutoPointcut() {}

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.buscarPorId(..))")
    public void buscarPorIdPointcut() {}

    @Pointcut("execution(* com.dev.jarmison.product_service.service.ProdutoService.buscarTodos(..))")
    public void buscarTodosPointcut() {}

    @AfterReturning(pointcut = "criarProdutoPointcut()", returning = "result")
    public void auditarCriacao(JoinPoint joinPoint, Object result) {
        ProdutoDTO produto = (ProdutoDTO) result;
        AuditoriaProdutoDTO auditoria = new AuditoriaProdutoDTO(
                produto.getId(), "CRIACAO", "Produto " + produto.getNome() + " criado."
        );
        enviarAuditoria(auditoria);
    }

    @AfterReturning(pointcut = "atualizarProdutoPointcut()", returning = "result")
    public void auditarAtualizacao(JoinPoint joinPoint, Object result) {
        ProdutoDTO produto = (ProdutoDTO) result;
        AuditoriaProdutoDTO auditoria = new AuditoriaProdutoDTO(
                produto.getId(), "ATUALIZACAO", "Produto " + produto.getNome() + " atualizado."
        );
        enviarAuditoria(auditoria);
    }

    @AfterReturning(pointcut = "deletarProdutoPointcut()")
    public void auditarRemocao(JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        AuditoriaProdutoDTO auditoria = new AuditoriaProdutoDTO(
                id, "REMOCAO", "Produto com ID " + id + " removido."
        );
        enviarAuditoria(auditoria);
    }

    @AfterReturning(pointcut = "buscarPorIdPointcut()")
    public void auditarBuscaPorId(JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        AuditoriaProdutoDTO auditoria = new AuditoriaProdutoDTO(
                id, "BUSCA_ID", "Produto com ID " + id + " consultado."
        );
        enviarAuditoria(auditoria);
    }

    @AfterReturning(pointcut = "buscarTodosPointcut()")
    public void auditarBuscaTodos() {
        AuditoriaProdutoDTO auditoria = new AuditoriaProdutoDTO(
                null, "BUSCA_TODOS", "Todos os produtos foram consultados."
        );
        enviarAuditoria(auditoria);
    }

    private void enviarAuditoria(AuditoriaProdutoDTO auditoria) {
        try {
            restTemplate.postForEntity(urlAuditoria, auditoria, String.class);
            log.info("Auditoria enviada com sucesso para o Serviço de Estoque: {}", auditoria.getAcao());
        } catch (Exception e) {
            log.error("O Serviço de Estoque está incomunicável. Falha ao enviar auditoria da ação '{}'. Erro: {}",
                    auditoria.getAcao(), e.getMessage());
        }
    }
}
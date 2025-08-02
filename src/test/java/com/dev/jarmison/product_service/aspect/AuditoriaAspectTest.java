package com.dev.jarmison.product_service.aspect;

import com.dev.jarmison.product_service.dto.AuditoriaProdutoDTO;
import com.dev.jarmison.product_service.dto.ProdutoDTO;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o Aspecto de Auditoria")
class AuditoriaAspectTest {

    private AuditoriaAspect auditoriaAspect;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        auditoriaAspect = new AuditoriaAspect();
        ReflectionTestUtils.setField(auditoriaAspect, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(auditoriaAspect, "urlAuditoria", "http://localhost:8081/api/auditoria");
    }

    @Test
    @DisplayName("Deve enviar auditoria de criação com sucesso")
    void deveEnviarAuditoriaDeCriacaoComSucesso() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Teclado");

        when(restTemplate.postForEntity(anyString(), any(AuditoriaProdutoDTO.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        auditoriaAspect.auditarCriacao(joinPoint, produtoDTO);

        verify(restTemplate, times(1)).postForEntity(anyString(), any(AuditoriaProdutoDTO.class), eq(String.class));
    }

    @Test
    @DisplayName("Deve falhar ao enviar auditoria e não lançar exceção")
    void deveFalharAoEnviarAuditoriaENaoLancarExcecao() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Teclado");

        when(restTemplate.postForEntity(anyString(), any(AuditoriaProdutoDTO.class), eq(String.class)))
                .thenThrow(new ResourceAccessException("Conexão recusada"));

        assertDoesNotThrow(() -> auditoriaAspect.auditarCriacao(joinPoint, produtoDTO));
    }
}
package com.dev.jarmison.product_service.aspect;

import com.dev.jarmison.product_service.dto.ProdutoDTO;
import com.dev.jarmison.product_service.exception.ValidacaoDeCamposException;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para o Aspecto de Validação")
class ValidacaoAspectTest {

    private ValidacaoAspect validacaoAspect;

    @Mock
    private JoinPoint joinPoint;

    @BeforeEach
    void setUp() {
        validacaoAspect = new ValidacaoAspect();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome do produto é nulo")
    void deveLancarExcecaoQuandoNomeDoProdutoENulo() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setPreco(BigDecimal.TEN);
        produtoDTO.setQuantidadeEmEstoque(10);
        produtoDTO.setLimiteMinimoEstoque(5);

        when(joinPoint.getArgs()).thenReturn(new Object[]{produtoDTO});

        assertThrows(ValidacaoDeCamposException.class, () -> validacaoAspect.validarCampos(joinPoint));
    }

    @Test
    @DisplayName("Deve permitir a execução quando todos os campos são válidos")
    void devePermitirExecucaoQuandoCamposSaoValidos() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Smartphone");
        produtoDTO.setPreco(BigDecimal.TEN);
        produtoDTO.setQuantidadeEmEstoque(10);
        produtoDTO.setLimiteMinimoEstoque(5);

        when(joinPoint.getArgs()).thenReturn(new Object[]{produtoDTO});

        assertDoesNotThrow(() -> validacaoAspect.validarCampos(joinPoint));
    }
}
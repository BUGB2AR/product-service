package com.dev.jarmison.product_service.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes para as classes de Exceção")
class ExceptionTest {

    @Test
    @DisplayName("RecursoNaoEncontradoException deve manter a mensagem correta")
    void recursoNaoEncontradoExceptionTest() {
        String mensagemDeErro = "Produto com ID 10 não encontrado.";
        RecursoNaoEncontradoException exception = new RecursoNaoEncontradoException(mensagemDeErro);

        assertEquals(mensagemDeErro, exception.getMessage());
    }

    @Test
    @DisplayName("ValidacaoDeCamposException deve manter a mensagem correta")
    void validacaoDeCamposExceptionTest() {
        String mensagemDeErro = "O campo 'nome' é obrigatório.";
        ValidacaoDeCamposException exception = new ValidacaoDeCamposException(mensagemDeErro);

        assertEquals(mensagemDeErro, exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível lançar e capturar RecursoNaoEncontradoException")
    void deveLancarRecursoNaoEncontradoException() {
        String mensagem = "Recurso não existe.";

        Exception excecaoCapturada = assertThrows(RecursoNaoEncontradoException.class, () -> {
            throw new RecursoNaoEncontradoException(mensagem);
        });

        assertEquals(mensagem, excecaoCapturada.getMessage());
    }
}
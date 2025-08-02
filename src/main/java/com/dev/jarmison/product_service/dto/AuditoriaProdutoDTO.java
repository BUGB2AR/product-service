package com.dev.jarmison.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaProdutoDTO {
    private Long produtoId;
    private String acao;
    private String detalhes;
    private LocalDateTime dataAcao = LocalDateTime.now();

    public AuditoriaProdutoDTO(Long id, String criacao, String detalhes) {
        this.produtoId = id;
        this.acao = criacao;
        this.detalhes = detalhes;
    }
}

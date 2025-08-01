package com.dev.jarmison.product_service.repository;

import com.dev.jarmison.product_service.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

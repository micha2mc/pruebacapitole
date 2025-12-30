package com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.ProductId;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ExternalProductPort {
    CompletableFuture<Optional<Product>> getProductDetail(ProductId productId);
}

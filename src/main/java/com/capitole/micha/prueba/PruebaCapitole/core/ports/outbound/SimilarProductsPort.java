package com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.ProductId;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SimilarProductsPort {
    CompletableFuture<List<ProductId>> getSimilarProductIds(ProductId productId);
}

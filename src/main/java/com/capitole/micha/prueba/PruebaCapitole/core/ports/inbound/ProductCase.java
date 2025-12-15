package com.capitole.micha.prueba.PruebaCapitole.core.ports.inbound;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;

import java.util.List;

public interface ProductCase {
    List<Product> getSimilarProducts(String productId);
}

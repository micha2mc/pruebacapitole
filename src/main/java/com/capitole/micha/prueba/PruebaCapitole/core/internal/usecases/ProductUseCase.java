package com.capitole.micha.prueba.PruebaCapitole.core.internal.usecases;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.inbound.ProductCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductUseCase implements ProductCase {
    @Override
    public List<Product> getSimilarProducts(String productId) {
        return List.of();
    }
}

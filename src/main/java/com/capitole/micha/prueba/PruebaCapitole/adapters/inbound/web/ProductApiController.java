package com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.web;

import com.capitole.micha.api.ProductApi;
import com.capitole.micha.models.SimilarProducts;
import com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter.SimilarProductsConverter;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.inbound.ProductCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductApiController implements ProductApi {
    private final ProductCase productCase;
    private final SimilarProductsConverter similarProductsConverter;

    @Override
    public ResponseEntity<SimilarProducts> getProductSimilar(final String productId) {
        try {
            if (productId == null || productId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<Product> domainProducts = productCase.getSimilarProducts(productId);
            if (domainProducts.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(similarProductsConverter.toSimilarProducts(domainProducts));

        } catch (Exception e) {
            log.error("ERROR MESAAGE: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

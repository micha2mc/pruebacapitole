package com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.web;

import com.capitole.micha.api.ProductApi;
import com.capitole.micha.models.ProductDetail;
import com.capitole.micha.models.SimilarProducts;
import com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter.ProductConverter;
import com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter.SimilarProductsConverter;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.inbound.ProductCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductApiController implements ProductApi {
    private final ProductCase productCase;
    private final ProductConverter productConverter;
    private final SimilarProductsConverter similarProductsConverter;

    @Override
    public ResponseEntity<SimilarProducts> getProductSimilar(final String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Product> domainProducts = productCase.getSimilarProducts(productId);
        if(domainProducts.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<ProductDetail> response = domainProducts.stream()
                .map(productConverter::toProductDetail)
                .toList();
        return ResponseEntity.ok(similarProductsConverter.toSimilarProducts(response));
    }

    private void validateProductId(final String productId) {

    }
}

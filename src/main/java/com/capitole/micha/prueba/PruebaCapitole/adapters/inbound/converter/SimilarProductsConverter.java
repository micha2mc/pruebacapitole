package com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter;

import com.capitole.micha.models.ProductDetail;
import com.capitole.micha.models.SimilarProducts;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimilarProductsConverter {

    private ProductConverter productConverter;

    public SimilarProducts toSimilarProducts(List<Product> domainProducts) {
        List<ProductDetail> productDetailList = domainProducts.stream()
                .map(productConverter::toProductDetail)
                .toList();
        SimilarProducts similarProducts = new SimilarProducts();
        similarProducts.items(productDetailList);
        return similarProducts;
    }
}

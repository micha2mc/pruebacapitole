package com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter;


import com.capitole.micha.models.ProductDetail;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductDetail toProductDetail(final Product product) {
        ProductDetail productDetail = new ProductDetail();
        return productDetail;
    }
}

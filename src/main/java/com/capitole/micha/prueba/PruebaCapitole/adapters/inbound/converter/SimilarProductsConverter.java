package com.capitole.micha.prueba.PruebaCapitole.adapters.inbound.converter;

import com.capitole.micha.models.ProductDetail;
import com.capitole.micha.models.SimilarProducts;

import java.util.List;

public class SimilarProductsConverter {

    public SimilarProducts toSimilarProducts (List<ProductDetail> productDetailList){
        SimilarProducts similarProducts = new SimilarProducts();
        similarProducts.addAll(productDetailList);
        return similarProducts;
    }
}

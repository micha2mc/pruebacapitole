package com.capitole.micha.prueba.PruebaCapitole.adapters.outbound.http;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.ProductId;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound.ExternalProductPort;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound.SimilarProductsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExternalApiAdapter implements ExternalProductPort, SimilarProductsPort {

    @Value("${api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Override
    public CompletableFuture<Optional<Product>> getProductDetail(ProductId productId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = baseUrl + "/product/" + productId.value();
                ResponseEntity<ProductResponse> response =
                        restTemplate.getForEntity(url, ProductResponse.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    ProductResponse productResponse = response.getBody();
                    Product product = new Product(
                            new ProductId(productResponse.id),
                            productResponse.name,
                            productResponse.price,
                            productResponse.availability
                    );
                    return Optional.of(product);
                }
            } catch (Exception e) {
                // Log error
            }
            return Optional.empty();
        });
    }

    @Override
    public CompletableFuture<List<ProductId>> getSimilarProductIds(ProductId productId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String url = baseUrl + "/product/" + productId.value() + "/similarids";
                ResponseEntity<String[]> response = restTemplate.getForEntity(url, String[].class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return Arrays.stream(response.getBody())
                            .map(ProductId::new)
                            .collect(Collectors.toList());
                }
            } catch (Exception e) {
                // Log error
            }
            return List.of();
        });
    }

    private static class ProductResponse {
        public String id;
        public String name;
        public double price;
        public boolean availability;
    }
}

package com.capitole.micha.prueba.PruebaCapitole.core.internal.usecases;

import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.Product;
import com.capitole.micha.prueba.PruebaCapitole.core.internal.domain.model.ProductId;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.inbound.ProductCase;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound.ExternalProductPort;
import com.capitole.micha.prueba.PruebaCapitole.core.ports.outbound.SimilarProductsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductUseCase implements ProductCase {
    private final SimilarProductsPort similarProductsPort;
    private final ExternalProductPort externalProductPort;

    @Override
    public List<Product> getSimilarProducts(String productId) {
        ProductId id = new ProductId(productId);

        try {
            // 1. Obtener IDs similares
            List<ProductId> similarIds = similarProductsPort.getSimilarProductIds(id)
                    .get(2, TimeUnit.SECONDS);

            if (similarIds.isEmpty()) {
                return List.of();
            }

            // 2. Obtener detalles en paralelo
            List<CompletableFuture<Optional<Product>>> futures = similarIds.stream()
                    .map(this::fetchProductWithTimeout)
                    .toList();

            // 3. Combinar resultados
            List<Product> products = new ArrayList<>();
            for (CompletableFuture<Optional<Product>> future : futures) {
                try {
                    Optional<Product> product = future.get(2, TimeUnit.SECONDS);
                    product.ifPresent(products::add);
                } catch (TimeoutException | ExecutionException e) {
                    // Ignorar productos con timeout o error
                }
            }

            return products;

        } catch (TimeoutException e) {
            // Timeout al obtener IDs similares
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Error getting similar products", e);
        }
    }
    private CompletableFuture<Optional<Product>> fetchProductWithTimeout(ProductId productId) {
        return externalProductPort.getProductDetail(productId)
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> Optional.empty());
    }
}

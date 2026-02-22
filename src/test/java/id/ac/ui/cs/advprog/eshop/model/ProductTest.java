package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    private Product product;
    private UUID productId;

    @BeforeEach
    void setUp() {
        product = new Product();
        productId = UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6");

        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals(
                productId,
                product.getProductId(),
                "Product ID getter should return correct UUID"
        );
    }

    @Test
    void testGetProductName() {
        assertEquals(
                "Sampo Cap Bambang",
                product.getProductName(),
                "Product name getter should return correct name"
        );
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(
                100,
                product.getProductQuantity(),
                "Product quantity getter should return correct quantity"
        );
    }
}
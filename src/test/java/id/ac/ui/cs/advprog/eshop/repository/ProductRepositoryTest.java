package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // --- TEST EDIT PRODUCT ---
    @Test
    void testEditProductPositive() {
        // Arrange: Buat produk awal
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Act: Ubah nama dan quantity
        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6"); // ID harus sama
        updatedProduct.setProductName("Sampo Cap Baru");
        updatedProduct.setProductQuantity(50);
        productRepository.update(updatedProduct); // Asumsi nama method di repo adalah 'update' atau 'edit'

        // Assert: Cek apakah data berubah
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Baru", savedProduct.getProductName());
        assertEquals(50, savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductNegative_NotFound() {
        // Arrange: Produk yang ingin diedit tidak pernah dibuat
        Product product = new Product();
        product.setProductId("random-id-tidak-ada");
        product.setProductName("Produk Hantu");
        product.setProductQuantity(10);

        // Act: Coba update
        Product result = productRepository.update(product);

        // Assert: Seharusnya return null (atau behavior lain sesuai implementasi kamu)
        assertNull(result);
    }

    // --- TEST DELETE PRODUCT ---
    @Test
    void testDeleteProductPositive() {
        // Arrange: Buat produk
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Act: Hapus produk
        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        // Assert: Pastikan iterator kosong
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductNegative_NotFound() {
        // Arrange: Repository kosong (tidak ada produk)

        // Act: Coba hapus ID sembarang
        // (Asumsi method delete return boolean atau void, sesuaikan assertnya)
        // Jika void, kita hanya cek tidak ada error, dan list tetap kosong.
        productRepository.delete("random-id");

        // Assert
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
}
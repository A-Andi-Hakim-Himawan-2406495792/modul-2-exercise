package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    // GET /product/create
    @Test
    void testCreatePage() throws Exception {
        MvcResult result = mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("createProduct"))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus(),
                "GET /product/create should return 200");
    }

    // POST /product/create
    @Test
    void testCreatePost() throws Exception {
        MvcResult result = mockMvc.perform(post("/product/create")
                        .param("name", "Indomie")
                        .param("price", "3000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andReturn();

        assertEquals(302, result.getResponse().getStatus(),
                "POST create should redirect to product list");

        Mockito.verify(service).create(Mockito.any(Product.class));
    }

    // GET /product/list
    @Test
    void testProductList() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(new Product()));

        MvcResult result = mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("products"))
                .andExpect(view().name("productList"))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus(),
                "GET /product/list should return 200");
    }

    // GET /product/edit/{id} (product exists)
    @Test
    void testEditPageFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(new Product());

        MvcResult result = mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("editProduct"))
                .andReturn();

        assertEquals(200, result.getResponse().getStatus(),
                "Edit page should load when product exists");
    }

    // GET /product/edit/{id} (product not found)
    @Test
    void testEditPageNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(null);

        MvcResult result = mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andReturn();

        assertEquals(302, result.getResponse().getStatus(),
                "Should redirect when product not found");
    }

    // POST /product/edit
    @Test
    void testEditPost() throws Exception {
        MvcResult result = mockMvc.perform(post("/product/edit")
                        .param("name", "Updated")
                        .param("price", "5000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andReturn();

        assertEquals(302, result.getResponse().getStatus(),
                "POST edit should redirect to product list");

        Mockito.verify(service).update(Mockito.any(Product.class));
    }

    // POST /product/delete/{id}
    @Test
    void testDelete() throws Exception {
        UUID id = UUID.randomUUID();

        MvcResult result = mockMvc.perform(post("/product/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andReturn();

        assertEquals(302, result.getResponse().getStatus(),
                "Delete should redirect to product list");

        Mockito.verify(service).deleteById(id);
    }
}
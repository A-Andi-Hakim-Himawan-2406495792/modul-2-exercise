package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;


    @Test
    void createPage_statusOk() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk());
    }

    @Test
    void createPage_hasProductModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createPage_returnsView() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(view().name("createProduct"));
    }


    @Test
    void createPost_redirects() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("name", "Indomie")
                        .param("price", "3000"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createPost_redirectUrl() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("name", "Indomie")
                        .param("price", "3000"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void createPost_callsService() throws Exception {
        mockMvc.perform(post("/product/create")
                .param("name", "Indomie")
                .param("price", "3000"));

        Mockito.verify(service).create(Mockito.any(Product.class));
    }


    @Test
    void productList_statusOk() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk());
    }

    @Test
    void productList_hasModel() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/product/list"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void productList_returnsView() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(new Product()));

        mockMvc.perform(get("/product/list"))
                .andExpect(view().name("productList"));
    }


    @Test
    void editPageFound_statusOk() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void editPageFound_hasProduct() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void editPageFound_returnsView() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(view().name("editProduct"));
    }


    @Test
    void editPageNotFound_redirects() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(null);

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void editPageNotFound_redirectUrl() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(service.findById(id)).thenReturn(null);

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(redirectedUrl("/product/list"));
    }


    @Test
    void editPost_redirects() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("name", "Updated")
                        .param("price", "5000"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void editPost_callsService() throws Exception {
        mockMvc.perform(post("/product/edit")
                .param("name", "Updated")
                .param("price", "5000"));

        Mockito.verify(service).update(Mockito.any(Product.class));
    }

    @Test
    void delete_redirects() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/product/delete/" + id))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void delete_callsService() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/product/delete/" + id));

        Mockito.verify(service).deleteById(id);
    }
}
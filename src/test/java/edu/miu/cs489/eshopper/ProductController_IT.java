package edu.miu.cs489.eshopper;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.cs489.eshopper.controller.ProductController;
import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestSecurityConfig.class})
public class ProductController_IT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductController productController;

    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    public void setUp() {
        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test Product");
        productRequestDto.setDescription("This is a test product");
        productRequestDto.setPrice(50.0);
        productRequestDto.setQuantity(10);

        productResponseDto = new ProductResponseDto();
        productResponseDto.setId(1L);
        productResponseDto.setName("Test Product");
        productResponseDto.setDescription("This is a test product");
        productResponseDto.setPrice(50.0);
        productResponseDto.setRating(0.0);
        productResponseDto.setQuantity(10);
    }

    @Test
    public void testAddProduct() throws Exception {
        // Mocking the service method
        when(productService.addProduct(productRequestDto)).thenReturn(productResponseDto);

        // Perform POST request
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        // Verify response
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetProductById() throws Exception {
        // Mocking the service method
        when(productService.getProduct(1L)).thenReturn(productResponseDto);

        // Perform GET request
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/1"));

        // Verify response
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Mocking the service method
        List<ProductResponseDto> productList = Arrays.asList(productResponseDto);
        when(productService.getAllProducts()).thenReturn(productList);

        // Perform GET request
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/list"));

        // Verify response
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Mocking the service method
        when(productService.updateProduct(productRequestDto, 1L)).thenReturn(productResponseDto);

        // Perform PUT request
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        // Verify response
        result.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteProductById() throws Exception {
        // Perform DELETE request
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/product/delete/1"));

        // Verify response
        result.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}


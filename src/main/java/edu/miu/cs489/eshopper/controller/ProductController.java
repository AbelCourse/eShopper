package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@RequestParam(value = "name") String name) {
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @GetMapping("/list/{rating}")
    public List<ProductResponseDto> getProductByRating(@PathVariable double rating) {
        return productService.getProductByRating(rating);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productDto) {
        return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestDto productDto, @PathVariable Long id) {
        return new ResponseEntity<>(productService.updateProduct(productDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }


}

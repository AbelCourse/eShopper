package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(IProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    //
    @GetMapping("/{name}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequestDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRequestDto productDto, @PathVariable Long id) {
        productService.updateProduct(productDto, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }


    @GetMapping("/list/{rating}")
    public List<ProductResponseDto> getProductByRating(@PathVariable double rating) {
        return productService.getProductByRating(rating);
    }
    //
    // @GetMapping("/products/{name}")
    // public void getProductByName(@PathVariable String name) {
    //     productService.getProductByName(name);
    // }
    //
    // @GetMapping("/products/{rating}")
    // public List<ProductResponseDto> getProductByRating(@PathVariable double rating) {
    //     return productService.getProductByRating(rating);
    // }
    //
    // @GetMapping("/products/{name}")
    // public void getProductByName(@PathVariable String name) {
    //     productService.getProductByName(name);
    // }
    //
    // @GetMapping("/products/{rating}")
    // public List<ProductResponseDto> getProductByRating(@PathVariable double rating) {
    //     return productService.getProductByRating(rating);
    // }
    //

}

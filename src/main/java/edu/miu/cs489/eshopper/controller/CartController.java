package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.model.response.CartDTO;
import edu.miu.cs489.eshopper.service.interfaces.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;

    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/product/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return new ResponseEntity<>(cartService.addProductToCart(productId, cartId, quantity), HttpStatus.CREATED);
    }

    @GetMapping("/{emailID}/carts/{cartId}")
    public ResponseEntity<CartDTO> getCartById(@PathVariable String emailID, @PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getCart(emailID, cartId), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        return new ResponseEntity<>(cartService.removeProductFromCart(productId, cartId), HttpStatus.OK);
    }

    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<CartDTO> updateProductQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam int quantity) {
        return new ResponseEntity<>(cartService.updateProductQuantity(productId, cartId, quantity), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getCartTotal(cartId), HttpStatus.OK);
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<Integer> getNumberOfItems(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getNumberOfItems(cartId), HttpStatus.OK);
    }
}
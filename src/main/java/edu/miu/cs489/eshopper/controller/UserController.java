package edu.miu.cs489.eshopper.controller;

import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.model.request.ReviewRequestDTO;
import edu.miu.cs489.eshopper.model.request.UserRegistrationDTO;
import edu.miu.cs489.eshopper.model.response.UserByIdDTO;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import edu.miu.cs489.eshopper.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService userService;
    private final IProductService productService;

    public UserController(IUserService userService, IProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        return new ResponseEntity<>(userService.addUser(userRegistrationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserByIdDTO> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{userId}/product/{productId}/review")
    public ResponseEntity<String> addReviewToProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestBody ReviewRequestDTO reviewDTO) {
        return new ResponseEntity<>(productService.addReviewToProduct(userId, productId, reviewDTO), HttpStatus.CREATED);
    }
}

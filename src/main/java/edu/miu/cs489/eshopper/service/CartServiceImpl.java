package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.config.Mapper;
import edu.miu.cs489.eshopper.exception.ResourceNotFoundException;
import edu.miu.cs489.eshopper.model.Cart;
import edu.miu.cs489.eshopper.model.CartLine;
import edu.miu.cs489.eshopper.model.Product;
import edu.miu.cs489.eshopper.model.response.CartDTO;
import edu.miu.cs489.eshopper.model.response.ProductDTO;
import edu.miu.cs489.eshopper.repository.CartLineRepository;
import edu.miu.cs489.eshopper.repository.CartRepository;
import edu.miu.cs489.eshopper.repository.ProductRepository;
import edu.miu.cs489.eshopper.service.interfaces.ICartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartLineRepository cartItemRepository;
    private final Mapper mapper;


    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, CartLineRepository cartItemRepository, Mapper mapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.mapper = mapper;
    }

    @Override
    public CartDTO addProductToCart(Long productId, Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartLine cartLine = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartLine != null) {
            cartLine.setQuantity(cartLine.getQuantity() + quantity);
            cartItemRepository.save(cartLine);
        }
        if (product.getQuantity() < quantity) {
            throw new ResourceNotFoundException("Product quantity is less than requested quantity");
        }
        CartLine newCartLine = new CartLine();
        newCartLine.setProduct(product);
        newCartLine.setCart(cart);
        newCartLine.setQuantity(quantity);
        newCartLine.setProductPrice(product.getPrice());
        cartItemRepository.save(newCartLine);

        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + product.getPrice() * quantity);
        CartDTO cartDTO = mapper.map(cart, CartDTO.class);
        List<ProductDTO> productDTOS = cart.getCartLines().stream()
                .map(cartLine1 -> mapper.map(cartLine1.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    @Override
    public String removeProductFromCart(Long productId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartLine cartLine = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartLine == null) {
            throw new ResourceNotFoundException("Product not found in cart");
        }
        cart.setTotalPrice(cart.getTotalPrice() - cartLine.getProductPrice() * cartLine.getQuantity());
        Product product = cartLine.getProduct();
        product.setQuantity(product.getQuantity() + cartLine.getQuantity());
        cartItemRepository.delete(cartLine);
        return "Product" + cartLine.getProduct().getName() + " removed from cart";

    }

    @Override
    public CartDTO updateProductQuantity(Long productId, Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartLine cartLine = cartItemRepository.findCartItemByProductIdAndCartId(cartId, productId);
        if (cartLine == null) {
            throw new ResourceNotFoundException("Product not found in cart");
        }
        cartLine.setQuantity(quantity);
        cartItemRepository.save(cartLine);
        return getCart(cart.getUser().getEmail(), cartId);
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getCartLines().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartDTO cartDTO = mapper.map(cart, CartDTO.class);
        List<ProductDTO> productDTOS = cart.getCartLines().stream()
                .map(cartLine -> mapper.map(cartLine.getProduct(), ProductDTO.class))
                .toList();
        cartDTO.setProducts(productDTOS);
        return cartDTO;
    }

    @Override
    public double getCartTotal(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"))
                .getCartLines().stream()
                .mapToDouble(cartLine -> cartLine.getProduct().getPrice() * cartLine.getQuantity())
                .sum();
    }

    @Override
    public int getNumberOfItems(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart not found"))
                .getCartLines().stream()
                .mapToInt(CartLine::getQuantity)
                .sum();
    }


}

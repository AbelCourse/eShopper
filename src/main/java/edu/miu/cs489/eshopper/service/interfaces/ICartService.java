package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.response.CartDTO;

public interface ICartService {

    CartDTO addProductToCart(Long productId, Long cartId, int quantity);

    String removeProductFromCart(Long productId, Long cartId);

    CartDTO updateProductQuantity(Long productId, Long cartId, int quantity);

    void clearCart(Long cartId);

    CartDTO getCart(String emailID, Long cartId);

    double getCartTotal(Long cartId);

    int getNumberOfItems(Long cartId);

}

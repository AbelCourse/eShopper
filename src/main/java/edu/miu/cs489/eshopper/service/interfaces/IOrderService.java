package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.response.OrderDTO;

import java.util.List;

public interface IOrderService {
    OrderDTO createOrder(String email, Long cartId);

    OrderDTO updateOrder(String email, Long orderId, String status);

    //void deleteOrder();

    OrderDTO getOrder(String email, Long orderId);

    List<OrderDTO> getOrders();

    List<OrderDTO> getOrdersByUserId(String emailID);

}

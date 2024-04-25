package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.config.Mapper;
import edu.miu.cs489.eshopper.exception.ResourceNotFoundException;
import edu.miu.cs489.eshopper.model.Cart;
import edu.miu.cs489.eshopper.model.CartLine;
import edu.miu.cs489.eshopper.model.Order;
import edu.miu.cs489.eshopper.model.OrderItem;
import edu.miu.cs489.eshopper.model.response.OrderDTO;
import edu.miu.cs489.eshopper.model.response.OrderItemDTO;
import edu.miu.cs489.eshopper.repository.CartRepository;
import edu.miu.cs489.eshopper.repository.OrderItemRepository;
import edu.miu.cs489.eshopper.repository.OrderRepository;
import edu.miu.cs489.eshopper.repository.ProductRepository;
import edu.miu.cs489.eshopper.service.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;
    private final Mapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository, Mapper mapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
    }


    @Override
    public OrderDTO createOrder(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email, cartId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found");
        }
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Order Placed");
        order.setTotalPrice(cart.getTotalPrice());

        orderRepository.save(order);
        List<CartLine> cartLines = cart.getCartLines();
        if (cartLines.isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }
        List<OrderItem> orderItems = order.getOrderItems();
        for (CartLine cartLine : cartLines) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartLine.getProduct());
            orderItem.setQuantity(cartLine.getQuantity());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getCartLines().forEach(cartLine -> {
            productRepository.findById(cartLine.getProduct().getId()).ifPresent(product -> {
                product.setQuantity(product.getQuantity() - cartLine.getQuantity());
                productRepository.save(product);
            });
        });
        OrderDTO orderDTO = mapper.map(order, OrderDTO.class);
        orderItems.forEach(orderItem -> orderDTO.getOrderItems().add(mapper.map(orderItem, OrderItemDTO.class)));
        return orderDTO;
    }

    @Override
    public OrderDTO updateOrder(String email, Long orderId, String status) {
        Order order = orderRepository.findOrderByEmailAndOrderId(email, orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        order.setOrderStatus(status);
        orderRepository.save(order);
        return mapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrder(String email, Long orderId) {
        Order order = orderRepository.findOrderByEmailAndOrderId(email, orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }
        return mapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found");
        }
        return mapper.mapList(orders, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(String emailID) {
        List<Order> orders = orderRepository.findAllByEmail(emailID);
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found");
        }
        return mapper.mapList(orders, OrderDTO.class);
    }
}

package com.thesis.orderservice.service;

import com.thesis.orderservice.dto.MailOrder;
import com.thesis.orderservice.dto.MailOrderItem;
import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.entity.*;
import com.thesis.orderservice.repository.OrderItemRepository;
import com.thesis.orderservice.repository.OrderRepository;
import com.thesis.orderservice.repository.PaymentMethodRepository;
import com.thesis.orderservice.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private KafkaTemplate<String, String> toBasket;
    @Autowired
    private KafkaTemplate<String, MailOrder> toMail;

    public List<Order> getListOrder() {
        return orderRepository.findAll();
    }

    public Order findOrderById(long id) {
        return orderRepository.findByOrderId(id);
    }


//    public OrderItem findOrderItemById(Set<OrderItem> orderItems, String targetId) {
//        for (OrderItem a :
//                orderItems) {
//            if (a.getName().equalsIgnoreCase(targetId)) {
//                return a;
//            }
//        } // Trả về phần tử có id khớp hoặc null nếu không tìm thấy
//        return null;
//    }

    public OrderItem findOrderItemById(Set<OrderItem> orderItems, int targetId) {
        for (OrderItem a :
                orderItems) {
            if (a.getId() == (targetId)) {
                return a;
            }
        } // Trả về phần tử có id khớp hoặc null nếu không tìm thấy
        return null;
    }

    //    public void addOrder(OrderRequest orderRequest) {
//        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(orderRequest.getPaymentId());
//        Order order = Order.builder()
//                .email(orderRequest.getEmail())
//                .phoneNumber(orderRequest.getPhoneNumber())
//                .orderStatus("Pending")
//                .firstName(orderRequest.getFirstName())
//                .lastName(orderRequest.getLastName())
//                .username(orderRequest.getUsername())
//                .shippingAddress(orderRequest.getShippingAddress())
//                .paymentStatus("Pending")
//                .paymentMethod(paymentMethod.get())
//                .totalPrice(orderRequest.getTotalPrice())
//                .build();
//        orderRepository.save(order);
//        Set<OrderItem> orderItems = orderRequest.getOrderItems().stream()
//                .map(item -> OrderItem.builder()
//                        .productId(item.getProductId())
//                        .productName(item.getProductName())
//                        .quantity(item.getQuantity())
//                        .sellername(item.getSellername())
//                        .price(item.getPrice())
//                        .note(item.getNote())
//                        .orderId(order)
//                        .build())
//                .collect(Collectors.toSet());
//        orderItemRepository.saveAll(orderItems);
//        order.setOrderItems(orderItems);
//        toBasket.send("cart-to-order", order.getUsername());
//    }
    public List<Long> addOrder(OrderRequest orderRequest) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(orderRequest.getPaymentId());
        List<Long> orderNum = new ArrayList<>();
        // Group items by sellername
        Map<String, List<OrderItem>> itemsBySeller = orderRequest.getOrderItems().stream()
                .collect(Collectors.groupingBy(OrderItem::getSellername));

        // Create an order for each seller
        itemsBySeller.forEach((sellername, items) -> {
            Order order = Order.builder()
                    .email(orderRequest.getEmail())
                    .phoneNumber(orderRequest.getPhoneNumber())
                    .orderStatus(OrderStatus.PENDING)
                    .firstName(orderRequest.getFirstName())
                    .lastName(orderRequest.getLastName())
                    .username(orderRequest.getUsername())
                    .shippingAddress(orderRequest.getShippingAddress())
                    .paymentStatus(orderRequest.getPaymentStatus())
                    .paymentMethod(paymentMethod.get())
                    .orderDate(LocalDateTime.now())
                    .totalPrice(items.stream()
                            .map(OrderItem::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .build();

            orderRepository.save(order);

            orderNum.add(order.getOrderId());
            // Set order items for the current seller
            Set<OrderItem> orderItems = items.stream()
                    .map(item -> OrderItem.builder()
                            .productId(item.getProductId())
                            .name(item.getName())
                            .quantity(item.getQuantity())
                            .sellername(item.getSellername())
                            .price(item.getPrice())
                            .image_url(item.getImage_url())
                            .note(item.getNote())
                            .orderId(order)
                            .build())
                    .collect(Collectors.toSet());
            Set<MailOrderItem> mailOrderItems = items.stream()
                    .map(item -> MailOrderItem.builder()  // Sử dụng MailOrderItem.builder() ở đây
                            .name(item.getName())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build())
                    .collect(Collectors.toSet());

            MailOrder mailOrder = MailOrder
                    .builder()
                    .paymentMethod(paymentMethod.get().getMethod())
                    .firstName(orderRequest.getFirstName())
                    .lastName(orderRequest.getLastName())
                    .totalPrice(items.stream()
                            .map(OrderItem::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .shippingAddress(order.getShippingAddress())
                    .phoneNumber(order.getPhoneNumber())
                    .orderItems(mailOrderItems)
                    .email(order.getEmail())
                    .build();
            orderItemRepository.saveAll(orderItems);
            order.setOrderItems(orderItems);
            toMail.send("order-sendmail", mailOrder);

            // Notify the seller about the order
            toBasket.send("cart-to-order", order.getUsername());
        });
        return orderNum;
    }

    //    public void updateOrder(OrderRequest orderRequest) {
//        Optional<Order> order = orderRepository.findByUsername(orderRequest.getUser());
//        if (order.isEmpty()) {
//            throw new AppException("Không tìm thấy hóa đơn", HttpStatus.NOT_FOUND);
//        }
//        Order order1 = order.get();
//        order1.setUsername(orderRequest.getUser());
//
//        orderRepository.save(order1);
//    }
//
//    public void deleteOrder(Integer id) {
//        Optional<Order> orderOptional = orderRepository.findById(id);
//        if (orderOptional.isEmpty()) {
//            throw new AppException("không tìm thấy hóa đơn", HttpStatus.NOT_FOUND);
//        }
//        orderRepository.delete(orderOptional.get());
//    }
    public List<Order> findOrderByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }

    public Page<Order> getOrderByUsername(String username, Pageable pageable) {
        return orderRepository.findAllByUsername(username, pageable);
    }

    public void updateStatusOrder(long id, int value) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new AppException("Không tìm thấy order", HttpStatus.NOT_FOUND);
        }
        Order order = orderOptional.get();
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new AppException("Không thể cập nhật trạng thái giao dịch này", HttpStatus.BAD_REQUEST);
        }
        switch (value) {
            case 0:
                order.setOrderStatus(OrderStatus.CANCELED);
                break;
            case 1:
                order.setOrderStatus(OrderStatus.PENDING);
                break;
            case 2:
                order.setOrderStatus(OrderStatus.SHIPPING);
                break;
            case 3:
                order.setOrderStatus(OrderStatus.DELIVERED);
                order.setPaymentStatus("Paid");
                break;
            default:
                break;
        }
        orderRepository.save(order);
    }

    public Page<Order> getListOrderByStatus(int status, Pageable pageable) {
        Page<Order> orders;


        switch (status) {
            case 0:
                orders = orderRepository.findAllByOrderStatus(OrderStatus.CANCELED, pageable);
                break;
            case 1:
                orders = orderRepository.findAllByOrderStatus(OrderStatus.PENDING, pageable);
                break;
            case 2:
                orders = orderRepository.findAllByOrderStatus(OrderStatus.SHIPPING, pageable);
                break;
            case 3:
                orders = orderRepository.findAllByOrderStatus(OrderStatus.DELIVERED, pageable);
                break;
            default:
                throw new AppException("Trạng thái không hợp lệ", HttpStatus.BAD_REQUEST);
        }
        return orders;
    }

    public Map<String, BigDecimal> calculateRevenueByMonth(String username) {
        Map<String, BigDecimal> revenueByMonth = new HashMap<>();

        // Lấy tất cả đơn hàng từ cơ sở dữ liệu
        List<Order> allOrders = orderRepository.findAllByOrderStatus(OrderStatus.DELIVERED);

        // Format để lấy tháng từ ngày đặt hàng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        // Danh sách các tháng và năm trong năm cùng với số thứ tự
        List<MonthInfo> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            String monthName = Month.of(month).name();
            months.add(new MonthInfo(monthName, month));
        }

        for (Order order : allOrders) {
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    // Kiểm tra nếu orderItem có sellerName
                    if (orderItem.getSellername().equalsIgnoreCase(username)) {
                        String orderDateString = order.getOrderDate().format(formatter);

                        // Parse String thành LocalDateTime
                        LocalDateTime orderDate = LocalDateTime.parse(orderDateString, formatter);

                        // Tiếp tục xử lý như trước
                        String monthName = orderDate.getMonth().toString();
                        revenueByMonth.merge(monthName, new BigDecimal(orderItem.getPrice().doubleValue()), BigDecimal::add);
                    }
                }
            }
        }

        // Kiểm tra và thêm các tháng còn thiếu với giá trị 0
        for (MonthInfo month : months) {
            String monthName = month.getName();
            if (!revenueByMonth.containsKey(monthName)) {
                revenueByMonth.put(monthName, BigDecimal.ZERO);
            }
        }

        // Sắp xếp lại theo số thứ tự của tháng
        List<Map.Entry<String, BigDecimal>> sortedList = revenueByMonth.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> months.stream()
                        .filter(monthInfo -> monthInfo.getName().equals(entry.getKey()))
                        .findFirst()
                        .map(MonthInfo::getOrder)
                        .orElse(0)))
                .collect(Collectors.toList());

        // Chuyển đổi lại thành Map
        Map<String, BigDecimal> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry : sortedList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    private static class MonthInfo {
        private final String name;
        private final int order;

        public MonthInfo(String name, int order) {
            this.name = name;
            this.order = order;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }
    }
    public Map<String, BigDecimal> calculateRevenuePendingByMonth(String username) {
        List<OrderStatus> orderStatuses = new ArrayList<>();
        Map<String, BigDecimal> revenueByMonth = new HashMap<>();
        orderStatuses.add(OrderStatus.DELIVERED);
        orderStatuses.add(OrderStatus.CANCELED);

        // Lấy tất cả đơn hàng từ cơ sở dữ liệu
        List<Order> allOrders = orderRepository.findAllByOrderStatusNotIn(orderStatuses);

        // Format để lấy tháng từ ngày đặt hàng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

        // Danh sách các tháng và năm trong năm cùng với số thứ tự
        List<MonthInfo> months = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            String monthName = Month.of(month).name();
            months.add(new MonthInfo(monthName, month));
        }

        for (Order order : allOrders) {
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    // Kiểm tra nếu orderItem có sellerName
                    if (orderItem.getSellername().equalsIgnoreCase(username)) {
                        String orderDateString = order.getOrderDate().format(formatter);

                        // Parse String thành LocalDateTime
                        LocalDateTime orderDate = LocalDateTime.parse(orderDateString, formatter);

                        // Tiếp tục xử lý như trước
                        String monthName = orderDate.getMonth().toString();
                        revenueByMonth.merge(monthName, new BigDecimal(orderItem.getPrice().doubleValue()), BigDecimal::add);
                    }
                }
            }
        }

        // Kiểm tra và thêm các tháng còn thiếu với giá trị 0
        for (MonthInfo month : months) {
            String monthName = month.getName();
            if (!revenueByMonth.containsKey(monthName)) {
                revenueByMonth.put(monthName, BigDecimal.ZERO);
            }
        }

        // Sắp xếp lại theo số thứ tự của tháng
        List<Map.Entry<String, BigDecimal>> sortedList = revenueByMonth.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> months.stream()
                        .filter(monthInfo -> monthInfo.getName().equals(entry.getKey()))
                        .findFirst()
                        .map(MonthInfo::getOrder)
                        .orElse(0)))
                .collect(Collectors.toList());

        // Chuyển đổi lại thành Map
        Map<String, BigDecimal> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry : sortedList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    public BigDecimal calculateProductRevenue(int productId, String username) {
        List<Order> allOrders = orderRepository.findAll();

        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Order order : allOrders) {
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.getProductId()==productId && orderItem.getSellername().equalsIgnoreCase(username)) {
                        totalRevenue = totalRevenue.add(orderItem.getPrice());
                    }
                }
            }
        }

        return totalRevenue;
    }
    public void testKafka(String name) {
        toBasket.send("cart-order", name);
    }
}

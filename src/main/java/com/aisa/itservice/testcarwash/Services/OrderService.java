package com.aisa.itservice.testcarwash.Services;

import com.aisa.itservice.testcarwash.Entites.Order;
import com.aisa.itservice.testcarwash.Repositories.IOrderRepository;
import com.aisa.itservice.testcarwash.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        orders.addAll(orderRepository.getAll());
        return orders;
    }

    @Override
    public void deleteOrderByTime(Date dateExecute) {
        var order = orderRepository.getOrderByTimeExecute(dateExecute);
        if (order != null) {
            orderRepository.delete(order);
        }
    }
}

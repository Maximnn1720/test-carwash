package com.aisa.itservice.testcarwash.Services;


import com.aisa.itservice.testcarwash.Entites.Order;

import java.util.Date;
import java.util.List;

public interface IOrderService {

    void saveOrder(Order order);

    List<Order> getAll();

    void deleteOrderByTime(Date dateExecute);
}

package com.aisa.itservice.testcarwash.Repositories;

import com.aisa.itservice.testcarwash.Entites.Order;
import com.aisa.itservice.testcarwash.Entites.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IOrderRepository extends CrudRepository<Order, Integer> {
    @Query(value = "SELECT * FROM orders  where time_execute = :timeExecute", nativeQuery = true)
    Order getOrderByTimeExecute(@Param("timeExecute") Date timeExecute);

    @Query(value = "SELECT * FROM orders  where time_execute >= CURRENT_DATE", nativeQuery = true)
    List<Order> getAll();
}

package com.aisa.itservice.testcarwash.Entites;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@Component
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_gen")
    @SequenceGenerator(name = "order_gen", allocationSize = 1, sequenceName = "order_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "time_execute")
    private Date timeExecution;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "time_creation")
    private Date timeCreation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimeExecution() {
        return timeExecution;
    }

    public void setTimeExecution(Date timeExecution) {
        this.timeExecution = timeExecution;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimeCreation() {
        return timeCreation;
    }

    public void setTimeCreation(Date timeCreation) {
        this.timeCreation = timeCreation;
    }
}

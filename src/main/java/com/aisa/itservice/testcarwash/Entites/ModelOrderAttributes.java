package com.aisa.itservice.testcarwash.Entites;

import java.util.ArrayList;
import java.util.List;

public class ModelOrderAttributes {
    private String time;
    private List<String> ordersTime = new ArrayList<>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getOrdersTime() {
        return ordersTime;
    }

    public void setOrdersTime(List<String> ordersTime) {
        this.ordersTime = ordersTime;
    }

}

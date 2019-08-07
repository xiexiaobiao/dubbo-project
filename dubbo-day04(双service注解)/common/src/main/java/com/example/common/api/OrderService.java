package com.example.common.api;

import com.example.common.bo.Order;

public interface OrderService {

    boolean saveOrder(Order order);

    boolean delelteOrder();

    boolean invalidateOrder();
}

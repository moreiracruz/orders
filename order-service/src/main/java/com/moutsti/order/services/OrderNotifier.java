package com.moutsti.order.services;

import com.moutsti.order.entities.Order;

public interface OrderNotifier {
    void notify(Order order);
}

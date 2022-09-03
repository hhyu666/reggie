package com.hhyu.reggie.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhyu.reggie.entity.Orders;


public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
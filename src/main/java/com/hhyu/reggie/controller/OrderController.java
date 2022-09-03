package com.hhyu.reggie.controller;

import com.hhyu.reggie.common.R;
import com.hhyu.reggie.entity.Orders;
import com.hhyu.reggie.sevice.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info(String.valueOf(orders));
        orderService.submit(orders);
        return  R.success("success!");
    }
}

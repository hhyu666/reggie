package com.hhyu.reggie.sevice.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.entity.OrderDetail;
import com.hhyu.reggie.mapper.OrderDetailMapper;
import com.hhyu.reggie.sevice.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}

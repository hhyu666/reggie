package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.mapper.ShoppingCartMapper;
import com.hhyu.reggie.entity.ShoppingCart;
import com.hhyu.reggie.sevice.ShoppingCartService;
import org.springframework.stereotype.Service;


@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
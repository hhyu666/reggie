package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.entity.DishFlavor;
import com.hhyu.reggie.mapper.DishFlavorMapper;
import com.hhyu.reggie.sevice.DishFlavorservice;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorserviceimpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorservice {
}
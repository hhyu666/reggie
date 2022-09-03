package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.common.CustomException;
import com.hhyu.reggie.common.R;
import com.hhyu.reggie.dto.SetmealDto;
import com.hhyu.reggie.entity.Dish;
import com.hhyu.reggie.entity.Setmeal;
import com.hhyu.reggie.entity.SetmealDish;
import com.hhyu.reggie.mapper.SetmealMapper;
import com.hhyu.reggie.sevice.Dishservice;
import com.hhyu.reggie.sevice.SetmealDishService;
import com.hhyu.reggie.sevice.Setmealservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServicelmpl extends ServiceImpl<SetmealMapper, Setmeal> implements Setmealservice {

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        System.out.println(setmealDto.getCategoryId());
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息
        setmealDishService.saveBatch(setmealDishes);



    }
}

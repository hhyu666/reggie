package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.common.CustomException;
import com.hhyu.reggie.entity.Category;
import com.hhyu.reggie.entity.Dish;
import com.hhyu.reggie.entity.Employee;
import com.hhyu.reggie.entity.Setmeal;
import com.hhyu.reggie.mapper.CategoryMapper;
import com.hhyu.reggie.mapper.EmployeeMapper;
import com.hhyu.reggie.sevice.Categoryservice;
import com.hhyu.reggie.sevice.Dishservice;
import com.hhyu.reggie.sevice.Employservice;
import com.hhyu.reggie.sevice.Setmealservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Categoryserviceimpl extends ServiceImpl<CategoryMapper, Category> implements Categoryservice {
    @Autowired
    private Dishservice dishservice;

    @Autowired
    private Setmealservice setmealservice;

    @Override
    public void remove(Long ids){

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        int count=dishservice.count(dishLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("有关联菜品！");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        int count2=setmealservice.count(setmealLambdaQueryWrapper);
        if (count2>0){
            throw new CustomException("有关联菜单！");
        }

        super.removeById(ids);


    }
}

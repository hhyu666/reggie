package com.hhyu.reggie.sevice;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hhyu.reggie.dto.DishDto;
import com.hhyu.reggie.entity.Dish;
import org.springframework.stereotype.Service;


public interface Dishservice extends IService<Dish> {

    public void savewithflavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updatewithflavor(DishDto dishDto);

}


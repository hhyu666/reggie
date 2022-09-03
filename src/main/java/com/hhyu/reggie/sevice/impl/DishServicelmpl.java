package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.dto.DishDto;
import com.hhyu.reggie.entity.Dish;
import com.hhyu.reggie.entity.DishFlavor;
import com.hhyu.reggie.mapper.DishMapper;
import com.hhyu.reggie.sevice.DishFlavorservice;
import com.hhyu.reggie.sevice.Dishservice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishServicelmpl extends ServiceImpl<DishMapper, Dish> implements Dishservice {
    @Autowired
    private DishFlavorservice dishFlavorservice;

    /**
     * save with flavor.
     * @param dishDto
     */
    @Override
    public void savewithflavor(DishDto dishDto) {
        this.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());


        dishFlavorservice.saveBatch(dishDto.getFlavors());


    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //通过id查询菜品基本信息
        Dish dish = super.getById(id);

        //创建dto对象
        DishDto dishDto = new DishDto();

        //对象拷贝
        BeanUtils.copyProperties(dish,dishDto);

        //条件查询flavor
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorservice.list(queryWrapper);

        //将查询到的flavor赋值到dto对象中
        dishDto.setFlavors(list);

        return dishDto;
    }

    @Override
    public void updatewithflavor(DishDto dishDto) {
        this.updateById(dishDto);

        LambdaQueryWrapper<DishFlavor> wrapper=new LambdaQueryWrapper();
        wrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorservice.remove(wrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorservice.saveBatch(flavors);





    }

}

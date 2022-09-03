package com.hhyu.reggie.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhyu.reggie.dto.SetmealDto;
import com.hhyu.reggie.entity.Setmeal;


public interface Setmealservice extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);
}

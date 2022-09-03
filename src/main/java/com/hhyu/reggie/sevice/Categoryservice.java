package com.hhyu.reggie.sevice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhyu.reggie.entity.Category;

public interface Categoryservice extends IService<Category> {
    void remove(Long id);
}

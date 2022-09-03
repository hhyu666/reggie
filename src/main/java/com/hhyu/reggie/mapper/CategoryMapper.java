package com.hhyu.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhyu.reggie.common.BaseContext;
import com.hhyu.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

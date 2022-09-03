package com.hhyu.reggie.dto;

import com.hhyu.reggie.entity.Setmeal;
import com.hhyu.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

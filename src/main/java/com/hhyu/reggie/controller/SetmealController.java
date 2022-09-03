package com.hhyu.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhyu.reggie.common.BaseContext;
import com.hhyu.reggie.common.R;
import com.hhyu.reggie.dto.SetmealDto;
import com.hhyu.reggie.entity.Category;
import com.hhyu.reggie.entity.Setmeal;
import com.hhyu.reggie.sevice.Categoryservice;
import com.hhyu.reggie.sevice.SetmealDishService;
import com.hhyu.reggie.sevice.Setmealservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private Setmealservice setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private Categoryservice categoryservice;

    /**
     * save!
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody SetmealDto setmealDto){
        Long empid=(Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(empid);

        setmealService.saveWithDish(setmealDto);
        return R.success("success!");
    }

    @GetMapping("/page")
    public R<Page> list(int page, int pageSize, String name){
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        //构造查询条件对象
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(name != null, Setmeal::getName, name);

        //操作数据库
        setmealService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            //获取categoryId
            Long categoryId = item.getCategoryId();
            Category category = categoryservice.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(dtoPage);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        //创建条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());

        //排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }


}

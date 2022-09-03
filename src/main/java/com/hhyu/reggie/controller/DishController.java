package com.hhyu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhyu.reggie.common.BaseContext;
import com.hhyu.reggie.common.R;
import com.hhyu.reggie.dto.DishDto;
import com.hhyu.reggie.entity.Category;
import com.hhyu.reggie.entity.Dish;
import com.hhyu.reggie.entity.DishFlavor;
import com.hhyu.reggie.sevice.Categoryservice;
import com.hhyu.reggie.sevice.DishFlavorservice;
import com.hhyu.reggie.sevice.Dishservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private Dishservice dishService;

    @Autowired
    private DishFlavorservice dishFlavorService;

    @Autowired
    private Categoryservice categoryservice;



    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody DishDto dishDto){

        Long empid=(Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(empid);

        log.info(dishDto.toString());
        dishService.save(dishDto);
        return R.success("success!");
    }

    /**
     * 得到分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.like(name != null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        //获取原records数据
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();  //分类id
            Category category = categoryservice.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }



    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        //查询
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody DishDto dishDto){

        Long empid=(Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(empid);

        log.info(dishDto.toString());
        dishService.updatewithflavor(dishDto);
        return R.success("success!");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> listR(Dish dish){
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        lambdaQueryWrapper.eq(Dish::getStatus,1);
//        lambdaQueryWrapper.orderByAsc(Dish::getSort);
//
//        List<Dish> list = dishService.list(lambdaQueryWrapper);
//
//
//        return R.success(list);
//
//
//    }

    @GetMapping("/list")
    public R<List<DishDto>> listR(DishDto dish){
        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        lambdaQueryWrapper.eq(Dish::getStatus,1);
        lambdaQueryWrapper.orderByAsc(Dish::getSort);

        List<Dish> list = dishService.list(lambdaQueryWrapper);

        List<DishDto> lists = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();  //分类id
            Category category = categoryservice.getById(categoryId);
            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            Long itemId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(DishFlavor::getDishId,itemId);
            List<DishFlavor> list1 = dishFlavorService.list(lambdaQueryWrapper1);
            dishDto.setFlavors(list1);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(lists);


    }

}

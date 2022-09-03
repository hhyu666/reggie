package com.hhyu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhyu.reggie.common.BaseContext;
import com.hhyu.reggie.common.CustomException;
import com.hhyu.reggie.common.R;
import com.hhyu.reggie.entity.Category;
import com.hhyu.reggie.entity.Dish;
import com.hhyu.reggie.entity.Employee;
import com.hhyu.reggie.entity.Setmeal;
import com.hhyu.reggie.sevice.Categoryservice;

import com.hhyu.reggie.sevice.Dishservice;
import com.hhyu.reggie.sevice.Setmealservice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private Categoryservice categoryservice;

    @PostMapping
    public R<String> CreateCategory(HttpServletRequest request, @RequestBody Category category){
        Long empid=(Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(empid);
        categoryservice.save(category);
        return R.success("create success!");
    }

    /**
     * Page
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> Page(int page,int pageSize){

        Page<Category> page1=new Page<>(page,pageSize);

        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper();

        lambdaQueryWrapper.orderByAsc(Category::getSort);

        categoryservice.page(page1,lambdaQueryWrapper);

        return R.success(page1);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("将要删除的分类id:{}",ids);
        categoryservice.remove(ids);
        return R.success("分类信息删除成功");
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Category category){
        Long empid=(Long) request.getSession().getAttribute("employee");
        BaseContext.setThreadLocal(empid);
        log.info("修改分类信息为:{}",category);
        categoryservice.updateById(category);
        return R.success("修改分类信息成功");
    }


    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        //查询数据
        List<Category> list = categoryservice.list(queryWrapper);
        //返回数据
        return R.success(list);

    }








}

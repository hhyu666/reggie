package com.hhyu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhyu.reggie.common.BaseContext;
import com.hhyu.reggie.common.R;
import com.hhyu.reggie.entity.Employee;
import com.hhyu.reggie.sevice.Employservice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private Employservice employservice;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());




        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=employservice.getOne(queryWrapper);

        if(emp==null){
            return R.error("login error!");
        }
        else if (!emp.getPassword().equals(password)){
            return R.error("login error!");
        }
        else if (emp.getStatus()==0){
            return R.error("login error!");
        }

        request.getSession().setAttribute("employee",emp.getId());
        BaseContext.setThreadLocal(emp.getId());
        log.info("xiancheng:{}",Thread.currentThread().getId());
        return R.success(emp);

    }

    /**
     * 员工登出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("logout succes!");
    }

    /**
     * add new user
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("add new user!");

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        Long empid=(Long) request.getSession().getAttribute("employee");

        BaseContext.setThreadLocal(empid);

        employservice.save(employee);

        return R.success("add user success!");
    }

    /**
     * page
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page= {},page-size={},name={}",page,pageSize,name);

        Page page1=new Page(page,pageSize);

        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper();

        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);

        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        employservice.page(page1,lambdaQueryWrapper);

        return R.success(page1);
    }

    /**
     * update
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        System.out.println(employee.toString());

        Long empid=(Long) request.getSession().getAttribute("employee");

        BaseContext.setThreadLocal(empid);

        employservice.updateById(employee);

        return R.success("Update success!");
    }

    /**
     * getbyid
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee=employservice.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        else return R.error("error");

    }






}

package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.entity.Employee;
import com.hhyu.reggie.mapper.EmployeeMapper;
import com.hhyu.reggie.sevice.Employservice;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements Employservice {
}

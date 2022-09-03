package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.entity.User;
import com.hhyu.reggie.mapper.UserMapper;
import com.hhyu.reggie.sevice.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
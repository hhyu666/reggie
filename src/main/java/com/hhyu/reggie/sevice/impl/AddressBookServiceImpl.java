package com.hhyu.reggie.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhyu.reggie.entity.AddressBook;
import com.hhyu.reggie.mapper.AddressBookMapper;
import com.hhyu.reggie.sevice.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
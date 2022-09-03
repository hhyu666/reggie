package com.hhyu.reggie.controller;

import com.hhyu.reggie.sevice.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressController {
    @Autowired
    private AddressBookService addressBookService;
}

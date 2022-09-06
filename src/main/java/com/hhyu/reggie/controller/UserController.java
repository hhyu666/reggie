package com.hhyu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hhyu.reggie.common.R;

import com.hhyu.reggie.common.ValidateCodeUtils;
import com.hhyu.reggie.entity.User;
import com.hhyu.reggie.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate RedisTemplate;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        String phone=user.getPhone();

        if (StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode4String(4);
            log.info("code={}",code);

            //调用阿里云提供的短信服务API完成短信发送
//            SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);

            //需要将生成的验证码保存到session
//            log.info("code:{}",code);

            RedisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            session.setAttribute(phone,code);
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");

    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map,HttpSession session){

        String phone = map.get("phone").toString();

        String code = map.get("code").toString();

//        Object attribute = session.getAttribute(phone);

        String attribute = (String) RedisTemplate.opsForValue().get(phone);

        if (attribute!=null&&attribute.equals(code)){

            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user==null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());

            RedisTemplate.delete(phone);
            return R.success(user);
        }


        return R.error("error");
    }

}
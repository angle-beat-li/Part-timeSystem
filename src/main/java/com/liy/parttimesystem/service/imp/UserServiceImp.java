package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.mapper.UserMapper;
import com.liy.parttimesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImp$
 *
 * @author liy
 * @date 2024/3/12$
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<User> getUserList(Long phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if(phone != null) {
            queryWrapper.like(User::getPhone,phone);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach((item)->{
            item.setPassword(null);
        });
        return users;
    }
}

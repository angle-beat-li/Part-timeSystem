package com.liy.parttimesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liy.parttimesystem.entity.User;

import java.util.List;

/**
 * UserService$
 *
 * @author liy
 * @date 2024/3/12$
 */
public interface UserService extends IService<User> {
    List<User> getUserList(Long phone);
}

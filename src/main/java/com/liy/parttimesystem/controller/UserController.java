package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.service.UserService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.util.locale.provider.FallbackLocaleProviderAdapter;

import java.util.List;

/**
 * UserController$
 *
 * @author liy
 * @date 2024/3/12$
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    /**
     * @description: 更新用户信息

     * @return:

     */
    @PostMapping("/updateUserInfo")
    public APIResponse updateUserInfo(@RequestBody  User user){
        System.out.println(user);

        if(user != null){
            Long id = user.getId();
            User oldUser = userService.getById(id);
            if(oldUser != null) {
                user.setUpdateTime(NowDataTimeUtils.getNowTime());
                userService.updateById(user);

                return new APIResponse(ResponeCode.SUCCESS,"更新成功");
            }
        }
        return new APIResponse(ResponeCode.FAIL,"更新失败");
    }
    /**
     * @description: 获取用户信息

     * @return:

     */
    @GetMapping("/detail")
    public APIResponse detail(Long userId){
        if(userId != null) {
            User user = userService.getById(userId);
            if(user != null) {
                return new APIResponse(ResponeCode.SUCCESS, "查询成功",user);
            }
        }

        return new APIResponse(ResponeCode.FAIL,"没有该用户");
    }
    /**
     * @description: 获取用户列表

     * @return:

     */
    @GetMapping("/list")
    public APIResponse<List<User>> list(Long keyword){
        List<User> userList = userService.getUserList(keyword);
        return new APIResponse<>(ResponeCode.SUCCESS,"查询成功",userList);
    }
    /**
     * @description: 更新用户信息

     * @return: APIResponse

     */
    @PostMapping("/update")
    public APIResponse updateUser (@RequestBody  User newUser) {

        //1.查找该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getPhone,newUser.getPhone());
        User oldUser = userService.getOne(queryWrapper);
        //2.更改信息
        if(oldUser != null) {
            newUser.setId(oldUser.getId());
            newUser.setPassword(null);
            newUser.setPhone(null);
            newUser.setCreateTime(null);
            newUser.setUpdateTime(NowDataTimeUtils.getNowTime());
            //3.提交
            userService.updateById(newUser);
            return new APIResponse(ResponeCode.SUCCESS,"更新成功");
        }

        return new APIResponse(ResponeCode.FAIL,"非法操作");
    }
    /**
     * @description: 创建新用户

     * @return: APIResponse

     */
    @PostMapping("/create")
    public APIResponse createUser(@RequestBody User user) {
        Long phone =  user.getPhone();
        //1.判断phone是否为null
        if(phone != null) {
            //2.判断该phone是否已被使用
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User temp = userService.getOne(queryWrapper);
            if(temp == null) {
                //3.创建新用户
                User newUser = new User();
                newUser.setPhone(phone);
                newUser.setPassword(user.getPassword());
                newUser.setEmail(user.getEmail());
                newUser.setNickname(user.getNickname());
                newUser.setRole(user.getRole());
                newUser.setStatus(user.getStatus());
                newUser.setCreateTime(NowDataTimeUtils.getNowTime());
                newUser.setUpdateTime(NowDataTimeUtils.getNowTime());

                boolean save = userService.save(newUser);
                if(save)
                return new APIResponse(ResponeCode.SUCCESS,"创建成功",newUser);
            }
        }

        return new APIResponse(ResponeCode.FAIL,"创建新用户失败");
    }

    /**
     * @description: 批量删除用户

     * @return: APIResponse

     */
    @PostMapping("/delete")
    public APIResponse delete(String ids){
        // 批量删除
        String[] id = ids.split(",");
        Long[] phones = new Long[id.length];
        for (int i = 0; i < id.length; i++) {
            phones[i] = Long.parseLong(id[i]);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getPhone, phones);
        userService.remove(queryWrapper);
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }
    /**
     * @description: 后台登录

     * @return: APIResponse

     */
    @PostMapping("/login")
    public APIResponse adminLogin(@RequestBody  User user) {
            //1. 查找用户
            Long phone  = user.getPhone();
            String password = user.getPassword();
            if(phone != null && password != null) {
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(User::getPhone,phone);
                User user01 = userService.getOne(queryWrapper);
                if(user01 != null) {
                    //2.验证和权限密码
                    if(user01.getRole() == 3 && user01.getPassword().equals(password)){
                        //3.登录
                        return new APIResponse(ResponeCode.SUCCESS,"登陆成功",user01);
                    }
                }
            }
        return new APIResponse(ResponeCode.FAIL,"密码错误");

    }

    @PostMapping("/userLogin")
    public APIResponse userLogin(@RequestBody User user) {
        /**
         * 获取账号和密码
         */
        Long phone  = user.getPhone();
        String password = user.getPassword();
        //1.查找用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User user1 = userService.getOne(queryWrapper);
        if(user1 != null) {
            //2.检查密码正确与否
            if(user1.getPassword().equals(password)) {
                //3.登录

                /*
                 * 做redis缓存key以便快捷登录
                 */
                //TODO redis缓存未作

                return new APIResponse(ResponeCode.SUCCESS, "登陆成功",user1);
            }

        }

        return new APIResponse(ResponeCode.FAIL, "密码错误");
    }

    /**
     * @description: 创建用户

     * @return: APIResponse<User>

     */
    @PostMapping("/userRegister")
    public APIResponse<User> userRegister(@RequestBody User user){

        if (!StringUtils.isEmpty(user.getPhone())
                && !StringUtils.isEmpty(user.getPassword())) {
            //1. 验证该手机号是否已经被使用
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,user.getPhone());
            User one = userService.getOne(queryWrapper);
            //1.1该手机号已经被使用，结束，返回失败
            if(one == null) {
                //2.创建用户
                User newUser = new User();

                //3.设置用户名称 phone
                newUser.setPhone(user.getPhone());

                //3.1设置用户名称 用户_phone
                newUser.setNickname("用户_" + user.getPhone());

                //4.设置用户密码
                newUser.setPassword(user.getPassword());

                //5.设置创建时间
                newUser.setCreateTime(NowDataTimeUtils.getNowTime());

                //6.设置更新时间
                newUser.setUpdateTime(NowDataTimeUtils.getNowTime());
                //7.设置用户状态
                newUser.setStatus(1);

                //8.设置用户权限
                newUser.setRole(1);
                boolean save = userService.save(newUser);
                if(save)
                return new APIResponse(ResponeCode.SUCCESS, "创建成功",newUser);
            }
        }
        return new APIResponse(ResponeCode.FAIL, "创建失败");
    }

    /**
     * @description: 更改用户密码

     * @return: APIResponse

     */
    @PostMapping("/updatePwd")
    public APIResponse updatePwd(Long userId, String password, String newPassword) {
//        System.out.println(userId + " " + password + " " + newPassword);
        User user = userService.getById(userId);
        if(user != null) {
            if(user.getPassword().equals(password)) {
                user.setPassword(newPassword);
                userService.updateById(user);
                return new APIResponse(ResponeCode.SUCCESS,"更改密码成功");
            }
        }
        return new APIResponse(ResponeCode.FAIL,"更改密码失败,请仔细核对密码");
    }

}

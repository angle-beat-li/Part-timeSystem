package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.dto.ThingDto;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.ThingUser;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.service.ThingService;
import com.liy.parttimesystem.service.ThingUserService;
import com.liy.parttimesystem.service.UserService;
import com.sun.org.apache.bcel.internal.generic.LADD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ThingController$
 *
 * @author liy
 * @date 2024/3/21$
 */
@RestController
@RequestMapping("/thing")
public class ThingController {
    @Autowired
    ThingService thingService;

    @Autowired
    UserService userService;

    @Autowired
    ThingUserService thingUserService;
    /**
     * @description: 查询岗位

     * @return:

     */
    @GetMapping("/list")
    public APIResponse list(String keyword, String sort, String c, String tag){
        List<ThingDto> list =  thingService.getThingList(keyword, sort, c, tag);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", list);
    }
    /**
     * @description: 创建Thing,ThingTag,ThingUSer,ThingClassification

     * @return:

     */
    @PostMapping("/create")
    public APIResponse create(@RequestBody ThingDto thingDto) {
        System.out.println(thingDto.getTags() == null);
        if(thingDto.getUserId() != null ) {
            if(userService.getById(thingDto.getUserId()) != null) {
                System.out.println(thingDto.getStatus());
                thingService.insertThingDto(thingDto);
                return new APIResponse(ResponeCode.SUCCESS,"创建成功");
            }

        }

        return new APIResponse(ResponeCode.FAIL,"新建失败");
    }

    @GetMapping( "/detail")
    public APIResponse detail(Long id){
        Thing thing =  thingService.getById(id);

        return new APIResponse(ResponeCode.SUCCESS, "查询成功", thing);
    }


    /**
     * @description: 删除岗位

     * @return:

     */
    @PostMapping("/delete")
    public APIResponse delete(String ids){
        String[] id = ids.split(",");
        List<Long> thingIds = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            thingIds.add(Long.parseLong(id[i]));
        }
        thingService.removeThing(thingIds);
        return new APIResponse(ResponeCode.SUCCESS,"删除成功");
    }

    /**
     * @description: 更新岗位

     * @return:

     */
    @PostMapping("/update")
    public APIResponse update(@RequestBody  ThingDto thingDto) {
        if(thingDto.getId()  != null
                && thingDto.getUserId() != null
                && userService.getById(thingDto.getUserId()) != null) {
            thingService.updateThing(thingDto);
            return new APIResponse(ResponeCode.SUCCESS,"更新成功");
        }
        return new APIResponse(ResponeCode.FAIL , "更新失败");
    }
    /**
     * @description: 根据用户id查询岗位列表

     * @return:

     */
    @GetMapping("/listUserThingApi")
    public APIResponse listUserThingApi(Long userId){
        if(userId != null) {
            User user = userService.getById(userId);
            if(user != null) {
                LambdaQueryWrapper<ThingUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ThingUser::getUserId,userId);
                List<ThingUser> list = thingUserService.list(queryWrapper);

                List<Long> thingId = list.stream().map(item->
                        item.getThingId()).collect(Collectors.toList());
                LambdaQueryWrapper<Thing> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.in(Thing::getId,thingId);
                List<Thing> res = thingService.list(queryWrapper1);
                return new APIResponse(ResponeCode.SUCCESS,"查询成功",res);
            }
        }
        return new APIResponse(ResponeCode.FAIL,"查询失败");
    }
}

package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.ThingCollect;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.service.ThingCollectService;
import com.liy.parttimesystem.service.ThingService;
import com.liy.parttimesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ThingCollectController$
 *
 * @author liy
 * @date 2024/3/27$
 */
@RestController
@RequestMapping("/thingCollect")
public class ThingCollectController {

    @Autowired
    ThingCollectService thingCollectService;
    @Autowired
    UserService userService;
    @Autowired
    ThingService thingService;

    @GetMapping("/getUserCollectList")
    public APIResponse getUserCollectList(Long  userId){
        LambdaQueryWrapper<ThingCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ThingCollect::getUserId,userId);
        List<ThingCollect> list = thingCollectService.list(queryWrapper);
        List<Long> collect = list.stream().map(item -> {
            return item.getThingId();
        }).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            LambdaQueryWrapper<Thing> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Thing::getId,collect);
            List<Thing> thingList = thingService.list(lambdaQueryWrapper);
            return new APIResponse(ResponeCode.SUCCESS,"获取成功",thingList);
        }

        return new APIResponse(ResponeCode.FAIL,"获取失败");
    }

    @PostMapping("/collect")
    public APIResponse collect(ThingCollect thingCollect){
        if(thingCollect.getUserId() != null && thingCollect.getThingId() != null) {
            if (userService.getById(thingCollect.getUserId()) != null && thingService.getById(thingCollect.getThingId()) != null) {
                LambdaQueryWrapper<ThingCollect> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ThingCollect::getThingId,thingCollect.getThingId());
                queryWrapper.eq(ThingCollect::getUserId,thingCollect.getUserId());
                ThingCollect one = thingCollectService.getOne(queryWrapper);
                if(one == null) {
                    thingCollectService.save(thingCollect);
                    return new APIResponse(ResponeCode.SUCCESS, "收藏成功");
                }

            }
        }
        return new APIResponse(ResponeCode.FAIL, "收藏失败");
    }

    @GetMapping("/unCollect")
    public APIResponse unCollect(Long id){
        thingCollectService.removeById(id);
        return new APIResponse(ResponeCode.SUCCESS,"取消收藏成功");
    }
}

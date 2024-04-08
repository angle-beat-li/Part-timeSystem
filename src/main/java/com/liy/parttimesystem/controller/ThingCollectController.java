package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
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

    @PostMapping("/getUserCollectList")
    public APIResponse getUserCollectList(Long  useId){
        LambdaQueryWrapper<ThingCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ThingCollect::getUserId,useId);
        List<ThingCollect> list = thingCollectService.list(queryWrapper);
        return new APIResponse(ResponeCode.SUCCESS,"获取成功",list);
    }

    @PostMapping("/collect")
    public APIResponse collect(ThingCollect thingCollect){
        if(thingCollect.getUserId() != null && thingCollect.getThingId() != null) {
            if (userService.getById(thingCollect.getUserId()) != null && thingService.getById(thingCollect.getThingId()) != null) {
                thingCollectService.save(thingCollect);
            }
        }
        return new APIResponse(ResponeCode.SUCCESS, "收藏成功");
    }

    @GetMapping("/unCollect")
    public APIResponse unCollect(Long id){
        thingCollectService.removeById(id);
        return new APIResponse(ResponeCode.SUCCESS,"取消收藏成功");
    }
}

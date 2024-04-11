package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.ThingWish;
import com.liy.parttimesystem.service.ThingService;
import com.liy.parttimesystem.service.ThingWishService;
import com.liy.parttimesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ThingWishController$
 *
 * @author liy
 * @date 2024/3/27$
 */
@RestController
@RequestMapping("/thingWish")
public class ThingWishController {
    @Autowired
    ThingWishService thingWishService;
    @Autowired
    ThingService thingService;
    @Autowired
    UserService userService;

    /**
     * @description: 添加心愿

     * @return:

     */
    @PostMapping("/wish")
    public APIResponse wish(@RequestBody ThingWish thingWish){
        if(thingWish != null && thingWish.getThingId() != null && thingWish.getUserId() != null) {
            if (thingService.getById(thingWish.getThingId()) != null && userService.getById(thingWish.getUserId()) != null) {
                LambdaQueryWrapper<ThingWish> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ThingWish::getThingId,thingWish.getThingId());
                queryWrapper.eq(ThingWish::getUserId,thingWish.getUserId());
                ThingWish one = thingWishService.getOne(queryWrapper);
                if(one == null) {
                    thingWishService.save(thingWish);
                    return new APIResponse(ResponeCode.SUCCESS,"添加成功");
                }

            }
        }
        return new APIResponse(ResponeCode.FAIL,"添加失败");
    }
    /**
     * @description: 取消心愿

     * @return:

     */
    @PostMapping("/unWish")
    public APIResponse unWish(Long id){
        if(id != null && thingWishService.removeById(id)) {
             return new APIResponse(ResponeCode.SUCCESS,"ok");
        }
        return new APIResponse(ResponeCode.FAIL,"取消失败");
    }
    /**
      * @description: 查询全部

      * @return:

      */
    @GetMapping("/getUserWishList")
    public  APIResponse getUserWishList(Long userId){
        if(userId != null) {
            List<Thing> list = thingWishService.getByUserId(userId);
            return new APIResponse(ResponeCode.SUCCESS,"查询成功",list);
        }
        return new APIResponse(ResponeCode.FAIL,"查询失败");
    }

}

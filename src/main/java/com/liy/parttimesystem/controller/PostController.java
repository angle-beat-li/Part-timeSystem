package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.dto.PostDto;
import com.liy.parttimesystem.entity.Post;
import com.liy.parttimesystem.entity.ThingUser;
import com.liy.parttimesystem.service.PostService;
import com.liy.parttimesystem.service.ThingUserService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PostController$
 *
 * @author liy
 * @date 2024/4/11$
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    ThingUserService thingUserService;

    @GetMapping("/listUserPostApi")
    public APIResponse listUserPostApi(Long userId) {

        if(userId != null) {
            LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Post::getUserId,userId);
             List<Post> posts = postService.list(queryWrapper);
             return new APIResponse(ResponeCode.SUCCESS,"查询成功",posts);
        }


        return  new APIResponse(ResponeCode.FAIL,"查询失败");
    }

    @GetMapping("/listThingPostApi")
    public APIResponse listThingPostApi(Long userId) {
        if(userId != null) {
            LambdaQueryWrapper<ThingUser> thingUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
            thingUserLambdaQueryWrapper.eq(ThingUser::getUserId,userId);
            List<ThingUser> thingUsers = thingUserService.list(thingUserLambdaQueryWrapper);
            List<Long> things = thingUsers.stream().map(item -> {
                return item.getThingId();
            }).collect(Collectors.toList());
            List<PostDto> posts = postService.listByThing(things);
            return new APIResponse(ResponeCode.SUCCESS,"查询成功",posts);
        }
        return new APIResponse(ResponeCode.FAIL,"查询失败");
    }
    @PostMapping("/create")
    public APIResponse create(@RequestBody Post post){
        System.out.println(post);
        if(post.getThingId() != null && post.getUserId() != null) {
            LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Post::getUserId,post.getUserId());
            queryWrapper.eq(Post::getThingId,post.getThingId());
            Post one = postService.getOne(queryWrapper);
            if(one == null) {
                post.setCreateTime(NowDataTimeUtils.getNowTime());
                postService.save(post);
                return new APIResponse(ResponeCode.SUCCESS,"投递成功");
            } else {
                return new APIResponse(ResponeCode.FAIL,"已投递过该岗位");
            }

        }
        return new APIResponse(ResponeCode.FAIL,"投递失败");
    }
}

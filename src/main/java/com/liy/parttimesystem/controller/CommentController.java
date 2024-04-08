package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.dto.CommentDto;
import com.liy.parttimesystem.entity.Comment;
import com.liy.parttimesystem.entity.CommentLike;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.service.CommentLikeService;
import com.liy.parttimesystem.service.CommentService;
import com.liy.parttimesystem.service.ThingService;
import com.liy.parttimesystem.service.UserService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * CommentController$
 *
 * @author liy
 * @date 2024/3/27$
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    ThingService thingService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    CommentLikeService commentLikeService;

    @GetMapping("/list")
    public APIResponse list(){
        return new APIResponse(ResponeCode.SUCCESS,"成功",commentService.getList());
    }

    /**
     * @description: 创建评论

     * @return:

     */
    @PostMapping("/create")
    public APIResponse create(@RequestBody Comment comment){
        if(comment != null && comment.getUserId() != null && comment.getThingId() != null) {
            User user = userService.getById(comment.getUserId());
            Thing thing = thingService.getById(comment.getThingId());
            if(user != null && thing != null) {
                comment.setCommentTime(NowDataTimeUtils.getNowTime());
                commentService.save(comment);
                return new APIResponse(ResponeCode.SUCCESS,"评论成功");
            }
        }

        return new APIResponse(ResponeCode.FAIL,"评论失败");
    }

    /**
     * @description: 删除评论

     * @return:

     */
    @PostMapping("/delete")
    public APIResponse delete(String ids){
        // 批量删除
        String[] id = ids.split(",");
        Long[] comments = new Long[id.length];
        for (int i = 0; i < id.length; i++) {
            comments[i] = Long.parseLong(id[i]);
        }
        commentService.removeByIds(Arrays.asList(comments));
        LambdaQueryWrapper<CommentLike> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.in(CommentLike::getCommentId,comments);
        commentLikeService.remove(queryWrapper);
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }
    /**
     * @description: 岗位评论

     * @return:

     */
    @GetMapping("/listThingComments")
    public APIResponse listThingComments(Long thingId,String order){
        if(thingId != null && thingService.getById(thingId) != null) {
            List<CommentDto> list = commentService.getListByThing(thingId, order);
            return new APIResponse(ResponeCode.SUCCESS,"ok",list);
        }
        return new APIResponse(ResponeCode.FAIL,"null");
    }

    /**
     * @description:获取用户评论

     * @return:

     */
    @GetMapping("/listUserComments")
    public APIResponse listUserComments(Long userId){
        if(userId != null && userService.getById(userId) != null){
             List<Comment> list = commentService.getListByUser(userId);
             return new APIResponse(ResponeCode.SUCCESS,"ok",list);
        }
        return new APIResponse(ResponeCode.FAIL,"失败");
    }

    @PostMapping("/like")
    public APIResponse like(Long id,Long userId){
//        System.out.println(id + "  " + userId);
        if(id != null && userId != null && userService.getById(userId) != null && commentService.getById(id) != null) {
            LambdaQueryWrapper<CommentLike> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CommentLike::getCommentId,id);
            queryWrapper.eq(CommentLike::getUserId,userId);
            CommentLike one = commentLikeService.getOne(queryWrapper);
            if(one == null) {
                CommentLike commentLike = new CommentLike();
                commentLike.setCommentId(id);
                commentLike.setUserId(userId);
                commentLikeService.save(commentLike);
                return new APIResponse(ResponeCode.SUCCESS,"ok");
            }

        }
        return new APIResponse(ResponeCode.FAIL,"点赞失败");
    }
}

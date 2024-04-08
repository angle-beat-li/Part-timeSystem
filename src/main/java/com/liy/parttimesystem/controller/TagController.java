package com.liy.parttimesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liy.parttimesystem.common.APIResponse;
import com.liy.parttimesystem.common.ResponeCode;
import com.liy.parttimesystem.entity.Tag;
import com.liy.parttimesystem.entity.User;
import com.liy.parttimesystem.service.TagService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TagController$
 *
 * @author liy
 * @date 2024/3/21$
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tagService;

    /**
     * @description: 查找全部标签

     * @return: APIResponse

     */
    @GetMapping("/list")
    public APIResponse list() {
        List<Tag> list = tagService.list();
        return new APIResponse(ResponeCode.SUCCESS,"成功",list);
    }
    /**
     * @description: 创建标签

     * @return: APIResponse

     */
    @PostMapping("/create")
    public APIResponse createTag(@RequestBody  Tag tag) {
        if(tag != null) {
            //1.判断是否存在该标签
            String title = tag.getTitle().trim();
            if(!"".equals(title)){
                LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Tag::getTitle,title);
                Tag one = tagService.getOne(queryWrapper);
                if(one == null) {
                    //2.不存在->创建
                    Tag newTag = new Tag();
                    newTag.setTitle(title);
                    newTag.setCreateTime(NowDataTimeUtils.getNowTime());
                    tagService.save(newTag);
                    return new APIResponse(ResponeCode.SUCCESS,"创建成功",newTag);
                }
            }
        }

        //3.存在->创建失败
        return new APIResponse(ResponeCode.FAIL,"创建失败,可能存在此标签",tag);
    }

    /**
     * @description: 更新标签

     * @return:

     */
    @PostMapping("/update")
    public APIResponse updateTag(@RequestBody  Tag tag) {
        tagService.updateById(tag);
        return new APIResponse(ResponeCode.SUCCESS, "更新成功");
    }
    /**
     * @description: 删除标签

     * @return:

     */
    @PostMapping("/delete")
    public APIResponse delete(String ids){
        // 批量删除
        String[] id = ids.split(",");
        Long[] tags = new Long[id.length];
        for (int i = 0; i < id.length; i++) {
            tags[i] = Long.parseLong(id[i]);
        }
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Tag::getId,tags);
        tagService.remove(queryWrapper);
        return new APIResponse(ResponeCode.SUCCESS, "删除成功");
    }
}

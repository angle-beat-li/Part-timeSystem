package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.dto.ThingDto;
import com.liy.parttimesystem.entity.Thing;
import com.liy.parttimesystem.entity.ThingClassification;
import com.liy.parttimesystem.entity.ThingTag;
import com.liy.parttimesystem.entity.ThingUser;
import com.liy.parttimesystem.mapper.*;
import com.liy.parttimesystem.service.ThingClassificationService;
import com.liy.parttimesystem.service.ThingService;
import com.liy.parttimesystem.service.ThingTagService;
import com.liy.parttimesystem.service.ThingUserService;
import com.liy.parttimesystem.utils.NowDataTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * ThingServiceImp$
 *
 * @author liy
 * @date 2024/3/21$
 */
@Service
public class ThingServiceImp extends ServiceImpl<ThingMapper, Thing> implements ThingService {

    @Autowired
    ThingTagMapper thingTagMapper;

    @Autowired
    ThingClassificationMapper thingClassificationMapper;

    @Autowired
    ThingUserMapper thingUserMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentLikeMapper commentLikeMapper;


    public List<ThingDto> getThingList(String keyword, String sort, String c, String tag) {

        QueryWrapper<Thing> queryWrapper = new QueryWrapper<>();

        // 搜索
        queryWrapper.like(StringUtils.isNotBlank(keyword), "title", keyword);

        // 排序
        if (StringUtils.isNotBlank(sort)) {
            if (sort.equals("recent")) {
                queryWrapper.orderBy(true, false, "create_time");
            }
            else if (sort.equals("hot") || sort.equals("recommend")) {
                queryWrapper.orderBy(true, false, "update_time");
            }
        }else {
            queryWrapper.orderBy(true, false, "create_time");
        }

        // 根据分类筛选
//        if (StringUtils.isNotBlank(c) && !c.equals("-1")) {
//            queryWrapper.eq(true, "classification_id", c);
//        }

        List<Thing> things =list(queryWrapper);
        List<ThingDto> res = new ArrayList<>();

        for (Thing thing : things) {
            res.add(CopyThingToThingDto(thing));
        }
        // tag筛选
        if (StringUtils.isNotBlank(tag)) {
            List<ThingDto> tThingDtos = new ArrayList<>();

            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.eq("tag_id", tag);
            List<ThingTag> thingTagList = thingTagMapper.selectList(thingTagQueryWrapper);

            for (Thing thing : things) {
                for (ThingTag thingTag : thingTagList) {
                    if (thing.getId().equals(thingTag.getThingId())) {
                        tThingDtos.add(CopyThingToThingDto(thing));
                    }
                }
            }
            res.clear();
            res.addAll(tThingDtos);
        }

        // 附加tag
        for (ThingDto tmp : res) {
            QueryWrapper<ThingTag> thingTagQueryWrapper = new QueryWrapper<>();
            thingTagQueryWrapper.lambda().eq(ThingTag::getThingId, tmp.getId());
            List<ThingTag> thingTags = thingTagMapper.selectList(thingTagQueryWrapper);
            List<Long> tags = thingTags.stream().map(ThingTag::getTagId).collect(Collectors.toList());
            tmp.setTags(tags);
        }
        return res;
    }

    @Override
    @Transactional
    public void insertThingDto(ThingDto thingDto) {
        Thing newThing = CopyThingDtoToThing(thingDto);
        save(newThing);
        if(thingDto.getClassificationId() != null){
            ThingClassification newThingClassification = new ThingClassification();
            newThingClassification.setThingId(newThing.getId());
            newThingClassification.setClassificationId(thingDto.getClassificationId());
            thingClassificationMapper.insert(newThingClassification);
        }

        ThingUser newThingUser = new ThingUser();
        newThingUser.setThingId(newThing.getId());
        newThingUser.setUserId(thingDto.getUserId());
        thingUserMapper.insert(newThingUser);

        if(thingDto.getTags() != null){
            thingDto.getTags().stream().forEach(item->{
                ThingTag newThingTag = new ThingTag();
                newThingTag.setTagId(item);
                newThingTag.setThingId(newThing.getId());
                thingTagMapper.insert(newThingTag);
            });
        }

    }

    @Override
    @Transactional
    public void removeThing(List<Long> thingIds) {
        thingIds.stream().forEach(item->{
            removeById(item);
            thingTagMapper.deleteByThing(item);
            thingUserMapper.deleteByThing(item);
            thingClassificationMapper.deleteByThing(item);
            commentMapper.deleteByThing(item);
            commentLikeMapper.deleteByThing(item);

        });
    }

    @Override
    @Transactional
    public void updateThing(ThingDto thingDto) {
        Long thingId = thingDto.getId();
        updateById(thingDto);
        thingTagMapper.deleteByThing(thingId);
        thingUserMapper.deleteByThing(thingId);
        thingClassificationMapper.deleteByThing(thingId);
        if(thingDto.getClassificationId() != null){
            ThingClassification newThingClassification = new ThingClassification();
            newThingClassification.setThingId(thingId);
            newThingClassification.setClassificationId(thingDto.getClassificationId());
            thingClassificationMapper.insert(newThingClassification);
        }


        ThingUser newThingUser = new ThingUser();
        newThingUser.setThingId(thingId);
        newThingUser.setUserId(thingDto.getUserId());
        thingUserMapper.insert(newThingUser);

        if(thingDto.getTags() != null){
            thingDto.getTags().stream().forEach(item->{
                ThingTag newThingTag = new ThingTag();
                newThingTag.setTagId(item);
                newThingTag.setThingId(thingId);
                thingTagMapper.insert(newThingTag);
            });
        }
    }

    public Thing CopyThingDtoToThing(ThingDto thingDto) {
        Thing newThing = new Thing();
        newThing.setId(thingDto.getId());
        newThing.setTitle(thingDto.getTitle());
        newThing.setEducation(thingDto.getEducation());
        newThing.setLocation(thingDto.getLocation());
        newThing.setSalary(thingDto.getSalary());
        newThing.setDescription(thingDto.getDescription());
        newThing.setStatus(thingDto.getStatus());
        newThing.setWorkExpe(thingDto.getWorkExpe());
        newThing.setCreateTime(NowDataTimeUtils.getNowTime());
        newThing.setUpdateTime(NowDataTimeUtils.getNowTime());
        return newThing;
    }
    public ThingDto CopyThingToThingDto(Thing thing) {
        ThingDto newThingDto = new ThingDto();
        newThingDto.setId(thing.getId());
        newThingDto.setTitle(thing.getTitle());
        newThingDto.setEducation(thing.getEducation());
        newThingDto.setLocation(thing.getLocation());
        newThingDto.setSalary(thing.getSalary());
        newThingDto.setDescription(thing.getDescription());
        newThingDto.setStatus(thing.getStatus());
        newThingDto.setWorkExpe(thing.getWorkExpe());
        newThingDto.setCreateTime(NowDataTimeUtils.getNowTime());
        newThingDto.setUpdateTime(NowDataTimeUtils.getNowTime());
        return newThingDto;
    }
}

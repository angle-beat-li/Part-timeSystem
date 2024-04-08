package com.liy.parttimesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liy.parttimesystem.dto.ThingDto;
import com.liy.parttimesystem.entity.Thing;

import java.util.List;

/**
 * ThingService$
 *
 * @author liy
 * @date 2024/3/21$
 */
public interface ThingService extends IService<Thing> {
    List<ThingDto> getThingList(String keyword, String sort, String c, String tag);
    void insertThingDto(ThingDto thingDto);
    void removeThing(List<Long> thingIds);
    void updateThing(ThingDto thingDto);
}

package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.entity.ThingClassification;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * ThingClassificationMapper$
 *
 * @author liy
 * @date 2024/3/23$
 */
@Mapper
public interface ThingClassificationMapper extends BaseMapper<ThingClassification> {
//    @Delete("delete from thing_classification where thing_id = #{thingId}")
    void deleteByThing(long thingId);
}

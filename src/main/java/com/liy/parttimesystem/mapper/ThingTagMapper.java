package com.liy.parttimesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liy.parttimesystem.entity.ThingTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * ThingTagMapper$
 *
 * @author liy
 * @date 2024/3/23$
 */
@Mapper
public interface ThingTagMapper extends BaseMapper<ThingTag> {
//    @Delete("delete from thing_tag where thing_id = #{thingId}")
    void deleteByThing(long thingId);
}

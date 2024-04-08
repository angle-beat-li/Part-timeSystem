package com.liy.parttimesystem.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liy.parttimesystem.entity.Tag;
import com.liy.parttimesystem.mapper.TagMapper;
import com.liy.parttimesystem.service.TagService;
import org.springframework.stereotype.Service;

/**
 * TagServiceImp$
 *
 * @author liy
 * @date 2024/3/21$
 */
@Service
public class TagServiceImp extends ServiceImpl<TagMapper, Tag> implements TagService {
}

package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("thing_tag")
public class ThingTag {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private Long thingId;
  private Long tagId;

}

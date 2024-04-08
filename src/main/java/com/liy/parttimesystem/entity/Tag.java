package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tag")
public class Tag{

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;

  private String title;
  private String createTime;

}

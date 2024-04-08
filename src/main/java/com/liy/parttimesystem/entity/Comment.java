package com.liy.parttimesystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("comment")
public class Comment {

  @TableId(value = "id",type = IdType.AUTO)
  private Long id;
  private Long userId;
  private Long thingId;
  private String content;
  private String commentTime;
}

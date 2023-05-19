package com.example.demo3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@TableName("sys_action")
@ApiModel(value = "User对象", description = "")
@ToString
public class Action {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date date;

    private String name;

    private String introduce;

    private Integer people;

    private String place;

    private Integer joinpeople;

    private String pid;
}

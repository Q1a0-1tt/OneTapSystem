package com.example.demo3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@TableName("sys_leave")
@ToString
public class Leave {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userid;

    private String name;

    private String destination;

    private String reason;

    private Date starttime;

    private Date finaltime;

    private Integer status;
}

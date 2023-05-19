package com.example.demo3.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@TableName("action_user_view")
@ToString
public class ActionUserView {
    private Integer id;
    private String name;
    private Date date;
    private String introduce;
    private String place;
    private Integer people;
    private Integer joinpeople;
    private String person;
}

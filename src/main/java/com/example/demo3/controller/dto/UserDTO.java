package com.example.demo3.controller.dto;

import cn.hutool.core.annotation.Alias;
import com.example.demo3.entity.Menu;
import lombok.Data;

import java.util.List;


@Data
public class UserDTO {

    @Alias("username")
    private String username;
    @Alias("password")
    private String password;
    @Alias("nickname")
    private String nickname;
    @Alias("avatarUrl")
    private String avatarUrl;

    private String token;

    private String role;

    private List<Menu> menus;
}

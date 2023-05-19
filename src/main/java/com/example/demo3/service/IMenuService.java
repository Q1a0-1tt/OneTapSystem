package com.example.demo3.service;

import com.example.demo3.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 李彤彤
 * @since 2023-03-25
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}

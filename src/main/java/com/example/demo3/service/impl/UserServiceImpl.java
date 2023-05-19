package com.example.demo3.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo3.Mapper.RoleMapper;
import com.example.demo3.Mapper.RoleMenuMapper;
import com.example.demo3.common.Constants;
import com.example.demo3.controller.dto.UserDTO;
import com.example.demo3.entity.Menu;
import com.example.demo3.entity.User;
import com.example.demo3.exception.ServiceException;
import com.example.demo3.Mapper.UserMapper;
import com.example.demo3.service.IMenuService;
import com.example.demo3.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo3.util.TokenUtil;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 李彤彤
 * @since 2023-03-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    /**
     * 登录
     * @param userDTO
     * @return
     */
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            //设置token
            String token = TokenUtil.getToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);
            //设置菜单用户列表
            String role = one.getRole();
            List<Menu> roleMenus = getRoleMenu(role);
            userDTO.setMenus(roleMenus);
            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600,"账号密码错误");
        }
    }
    //注册
    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO,one,true);
            //设置初始头像
            one.setAvatarUrl("http://localhost:9090/file/1dc5b511cc86443fbbd65daacd5dee41.webp");
            //设置初始角色（默认注册为学生）
            one.setRole("ROLE_USER");
            save(one);
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }
    //封装方法-获得用户数据
    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try{
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    /**
     * 获取角色菜单
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenu(String roleFlag){
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        //当前角色所有菜单集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        //查询菜单
        List<Menu> menus = menuService.findMenus("");
        List<Menu> roleMenus = new ArrayList<>();
        //筛选角色菜单
        for(Menu menu:menus){
            if(menuIds.contains(menu.getId())){
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            children.removeIf(child->!menuIds.contains(child.getId()));
        }
        return roleMenus;
    }
}

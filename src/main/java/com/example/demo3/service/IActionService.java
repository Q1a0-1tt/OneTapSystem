package com.example.demo3.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo3.entity.Action;
import com.example.demo3.entity.ActionUserView;

import java.util.List;

public interface IActionService extends IService<Action> {
    //参加活动
    void joinAction(Integer actionId, String userName);

    int checkUser(Integer actionId, String userName);
    //退出活动
    void quitAction(Integer actionId, String userName);


    List<ActionUserView> listActionUserView(Integer id);
}

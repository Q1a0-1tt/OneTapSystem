package com.example.demo3.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo3.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo3.service.IMessageService;
import com.example.demo3.entity.Message;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 李彤彤
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/message")
        public class MessageController {
    
    @Resource
    private IMessageService messageService;

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Message message) {
        message.setTime(DateTime.now());
        messageService.saveOrUpdate(message);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        messageService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        messageService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/load")
    public Result findAll() {
        return Result.success(messageService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(messageService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize){
        QueryWrapper<Message> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return Result.success(messageService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
    }


package com.example.demo3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo3.common.Result;
import com.example.demo3.entity.Leave;
import com.example.demo3.entity.User;
import com.example.demo3.service.ILeaveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Resource
    private ILeaveService leaveService;

    //新增 or 修改
    @PostMapping
    public Result save(@RequestBody Leave leave) {
        return Result.success(leaveService.saveOrUpdate(leave));
    }

    //查询所有数据
    @GetMapping("/count")
    public Result findAll() {
        List<Leave> list = leaveService.list();
        int count = 0;
        for(Leave leave:list){
            if(leave.getStatus() == 0) {
                count++;
            }
        }
        return Result.success(count);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize){
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<>();
        return Result.success(leaveService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    @GetMapping("/pageByStatus/{temp}")
    public Result findPageByStatus(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @PathVariable Integer temp){
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",temp);
        return Result.success(leaveService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }

    @GetMapping("/pageByUser/{username}")
    public Result findPageByUser(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @PathVariable String username){
        QueryWrapper<Leave> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",username);
        return Result.success(leaveService.page(new Page<>(pageNum,pageSize),queryWrapper));
    }
}

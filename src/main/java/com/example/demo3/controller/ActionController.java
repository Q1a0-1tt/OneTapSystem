package com.example.demo3.controller;

import ch.qos.logback.core.joran.action.IncludeAction;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo3.common.Result;
import com.example.demo3.entity.Action;
import com.example.demo3.entity.ActionUserView;
import com.example.demo3.entity.User;
import com.example.demo3.service.IActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private IActionService actionService;

    //更新
    //新增 or 修改
    @PostMapping
    public Result save(@RequestBody Action action) {
        return Result.success(actionService.saveOrUpdate(action));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(actionService.removeById(id));
    }

    //查询所有数据
    @GetMapping("/count")
    public Result findAll() {
        long resCount = actionService.count();
        return Result.success(resCount);
    }

    //参加活动
    @PostMapping("/join/username={userName}/actionId={actionId}")
    public Result joinAction(@PathVariable String userName,
                             @PathVariable Integer actionId){

        if(actionService.checkUser(actionId,userName)>0){
            return Result.error();
        }else{
            actionService.joinAction(actionId,userName);
            return Result.success();
        }
    }

    //参加活动
    @PostMapping("/quit/username={userName}/actionId={actionId}")
    public Result quitAction(@PathVariable String userName,
                             @PathVariable Integer actionId){
        actionService.quitAction(actionId,userName);
        return Result.success();
    }
    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Action> queryWrapper = new QueryWrapper<>();
        return Result.success(actionService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    //分页查询
    @GetMapping("/pageByUser/{userName}")
    public Result findPageByUser(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @PathVariable String userName) {
        QueryWrapper<Action> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("pid", "%" + userName + "%");
        return Result.success(actionService.page(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @GetMapping("/export/{id}")
    public void exportActionUserView(HttpServletResponse response,@PathVariable Integer id) throws Exception{
        List<ActionUserView> list = actionService.listActionUserView(id);
        ExcelWriter writer = ExcelUtil.getWriter(true);

        writer.addHeaderAlias("id","ID");
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("date","日期");
        writer.addHeaderAlias("introduce","介绍");
        writer.addHeaderAlias("place","地点");
        writer.addHeaderAlias("people","人数");
        writer.addHeaderAlias("joinpeople","参加人数");
        writer.addHeaderAlias("person","参与人员");

        writer.write(list,true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String filename = URLEncoder.encode("活动和用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+filename+".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }


}

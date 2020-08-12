package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-08
 */
@CrossOrigin
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //查询讲师表中的所有数据
    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){

            return R.ok();
        }
        else {
            return R.error();
        }
    }

    @ApiOperation("分页讲师列表")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页码",required = true) @PathVariable long current,
                             @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit) ;
        //调用方法实现查询
        eduTeacherService.page(pageTeacher,null);

        long total=pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("条件查询分页讲师列表")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")//RequestBody注解，需要用post方式
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页码",required = true) @PathVariable long current,
                                  @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit) ;
       //构建条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();

        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        Integer level = teacherQuery.getLevel();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create0",end);
        }
        //调用方法实现查询
        eduTeacherService.page(pageTeacher,wrapper);
        long total=pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save)  return R.ok();
        else return R.error();
    }
    @ApiOperation("查询单个讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    @ApiOperation("修改讲师")
    @PutMapping("/updateTeacher/{id}")
    public R updateById(@ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id,
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody EduTeacher teacher){
        teacher.setId(id);
        boolean b = eduTeacherService.updateById(teacher);
        if(b)   return R.ok();
        else return R.error();
    }


}


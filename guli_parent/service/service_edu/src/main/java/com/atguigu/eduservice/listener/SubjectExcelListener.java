package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.config.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //因为SubjectExcelListener不能交给spring管理，需要自己new，不能注入其他对象

    //解决方法,写一个有参构造器
    public EduSubjectService subjectService;

    public SubjectExcelListener(){};

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        EduSubject eduSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if(eduSubject==null){//没有相同一级分类，进行添加
            eduSubject=new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(eduSubject);

        }
        //获取一级分类的id值
        String pid=eduSubject.getId();
        EduSubject eduSubject1 = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(eduSubject1==null){//没有相同二级分类，进行添加
            eduSubject1=new EduSubject();
            eduSubject1.setParentId(pid);
            eduSubject1.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject1);
        }

    }

    //判断一级分类不能重复添加
    public EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }
    //判断二级分类不能重复添加
    public EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = eduSubjectService.getOne(wrapper);
        return two;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现写操作
        //1.
 //       String filename="E:\\write.xlsx";
        //2.
 //       EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());


   //实现读操作
   // 1.
        String filename="E:\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelLsitener()).sheet().doRead();

    }
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData demoData=new DemoData();
            demoData.setSname("stu"+i);
            demoData.setSno(i);
            list.add(demoData);
        }
        return list;
    }
}

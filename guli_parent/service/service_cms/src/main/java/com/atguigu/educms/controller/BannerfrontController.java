package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "广告位推荐")
@CrossOrigin
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerfrontController {

    @Autowired
    private CrmBannerService bannerService;


    @ApiOperation("查询排序后前2条推荐")
    @GetMapping("getAllBanner")
    public R getAllBanner() {

        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }

}

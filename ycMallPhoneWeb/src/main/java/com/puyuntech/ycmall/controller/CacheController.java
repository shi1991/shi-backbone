package com.puyuntech.ycmall.controller;

import com.puyuntech.ycmall.service.CacheService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by shi on 2016/4/13.
 */
@Controller
public class CacheController extends BaseController {

    @Resource(name = "cacheServiceImpl")
    private CacheService cacheService;

    @RequestMapping("/getCache")
    @ResponseBody
    public String getCache(){
        Object value = cacheService.getCache("shichangcheng","shichangcheng");
        System.out.println( value );
        return String.valueOf(value);
    }

}

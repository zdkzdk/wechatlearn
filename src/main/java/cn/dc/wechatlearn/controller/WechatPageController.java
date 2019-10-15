package cn.dc.wechatlearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WechatPageController {
    @RequestMapping("test")
    public String page(){
        return "index";
    }
}

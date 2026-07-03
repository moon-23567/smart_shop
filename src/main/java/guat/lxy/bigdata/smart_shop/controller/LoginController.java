package guat.lxy.bigdata.smart_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginController {
    // 打开登录页面（你原有代码不动）
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}


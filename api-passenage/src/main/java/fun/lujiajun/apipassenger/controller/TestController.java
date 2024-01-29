package fun.lujiajun.apipassenger.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
public class TestController {
    @Value("${server.port}")
    String port;
    @GetMapping("/test")
    public String test(HttpServletRequest httpServletRequest){
        String day = httpServletRequest.getHeader("day1");
        System.out.println("请求头，网关加的:"+day);
        System.out.println("api-passenger:"+port);
        return "test api passenger"+port;
    }
}

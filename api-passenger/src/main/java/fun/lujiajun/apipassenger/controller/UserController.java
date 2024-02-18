package fun.lujiajun.apipassenger.controller;

import fun.lujiajun.apipassenger.service.UserService;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user")
    public ResponseResult getUer(HttpServletRequest request){

        String accessToken = request.getHeader("Authorization");
        System.out.println(accessToken);

        return  userService.getUserByAccessToken(accessToken);
    }
}

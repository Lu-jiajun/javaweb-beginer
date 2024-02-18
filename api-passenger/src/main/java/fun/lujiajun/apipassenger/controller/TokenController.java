package fun.lujiajun.apipassenger.controller;

import fun.lujiajun.apipassenger.service.TokenService;
import fun.lujiajun.internalcommon.constant.TokenConstants;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import fun.lujiajun.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        String refreshTokenSrc = tokenResponse.getRefreshToken();
        System.out.println("origin refresh token: "+ refreshTokenSrc);
        return  tokenService.refreshToken(refreshTokenSrc);
    }
}

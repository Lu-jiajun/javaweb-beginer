package fun.lujiajun.apipassenger.service;

import fun.lujiajun.internalcommon.constant.CommonStatusEnum;
import fun.lujiajun.internalcommon.constant.TokenConstants;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import fun.lujiajun.internalcommon.dto.TokenResult;
import fun.lujiajun.internalcommon.response.TokenResponse;
import fun.lujiajun.internalcommon.util.JwtUtils;
import fun.lujiajun.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc){
        // first step parse refresh token
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null){
            return  ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstants.REFRESH_TOKEN_TYPE);
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

        // second read access token and refresh token from redis through
        if (StringUtils.isBlank(refreshTokenRedis) ||(!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))){
            return  ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        //third generate access token and refresh token
        String refreshToken = JwtUtils.generatorToken(phone,identity,TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JwtUtils.generatorToken(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE);
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(refreshToken);
        tokenResponse.setAccessToken(accessToken);
        return ResponseResult.success(tokenResponse);
    }
}

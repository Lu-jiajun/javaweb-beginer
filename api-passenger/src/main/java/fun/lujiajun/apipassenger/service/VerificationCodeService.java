package fun.lujiajun.apipassenger.service;

import fun.lujiajun.internalcommon.constant.CommonStatusEnum;
import fun.lujiajun.internalcommon.constant.IdentityConstant;
import fun.lujiajun.internalcommon.constant.TokenConstants;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import fun.lujiajun.internalcommon.response.TokenResponse;
import fun.lujiajun.internalcommon.request.VerificationCodeDTO;
import fun.lujiajun.internalcommon.response.NumberCodeResponse;
import fun.lujiajun.apipassenger.remote.ServicePassengerUserClient;
import fun.lujiajun.internalcommon.util.JwtUtils;
import fun.lujiajun.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import fun.lujiajun.apipassenger.remote.ServiceVerificationCodeClient;

import java.util.concurrent.TimeUnit;

import static fun.lujiajun.internalcommon.util.RedisPrefixUtils.*;

@Service
public class VerificationCodeService {

    @Autowired
//    https://www.cnblogs.com/frankcui/p/15178450.html
    private ServiceVerificationCodeClient serviceVerificationcodeClient;



    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;


    // generator token by phone and identity;


    public ResponseResult generatorCode(String passengerPhone) {
        System.out.println("输入手机号码,调用验证码发送服务");
//        String code = "1111" ;
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("remote number code: "+numberCode);
        System.out.println("验证码存入redis中");
        String key = verificationCodePrefix + passengerPhone;
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

//        JSONObject result = new JSONObject();
//        result.put("code",1);
//        result.put("message","success");
        return  ResponseResult.success();
    }


    public ResponseResult checkCode(String passengerPhone,String verificationCode){
        System.out.println("read verification code from redis according passengerPhone");

//        generate key
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        // get verification code from redis
        System.out.println("check  verification code in redis: "+ codeRedis);
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if(!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        System.out.println("check if the user is exist");
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

        System.out.println("generate ande response the token");
        String accessToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey,accessToken,30,TimeUnit.DAYS);

        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone,IdentityConstant.PASSENGER_IDENTITY,TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey,refreshToken,31,TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return  ResponseResult.success(tokenResponse);
    }
}

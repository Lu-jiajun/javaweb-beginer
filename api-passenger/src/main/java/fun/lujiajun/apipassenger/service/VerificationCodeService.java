package fun.lujiajun.apipassenger.service;

import fun.lujiajun.constant.CommonStatusEnum;
import fun.lujiajun.dto.ResponseResult;
import fun.lujiajun.dto.TokenResponse;
import fun.lujiajun.request.VerificationCodeDTO;
import fun.lujiajun.response.NumberCodeResponse;
import fun.lujiajun.apipassenger.remote.ServicePassengerUserClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import fun.lujiajun.apipassenger.remote.ServiceVerificationCodeClient;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
//    https://www.cnblogs.com/frankcui/p/15178450.html
    private ServiceVerificationCodeClient serviceVerificationcodeClient;

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

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
    private  String generatorKeyByPhone(String passengerPhone){
        return verificationCodePrefix + passengerPhone;
    }

    public ResponseResult checkCode(String passengerPhone,String verificationCode){
        System.out.println("read verification code from redis according passengerPhone");

//        generate key
        String key = generatorKeyByPhone(passengerPhone);
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
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return  ResponseResult.success(tokenResponse);
    }
}

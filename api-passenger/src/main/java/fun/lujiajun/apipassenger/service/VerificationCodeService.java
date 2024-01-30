package fun.lujiajun.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    public String generatorCode(String passengerPhone) {
        System.out.println("输入手机号码,调用验证码发送服务");
        String code = "1111" ;
        System.out.println("验证码存入redis中");
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        return result.toString();
    }
}

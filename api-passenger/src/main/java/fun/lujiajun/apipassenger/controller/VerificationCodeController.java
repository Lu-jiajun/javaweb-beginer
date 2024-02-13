package fun.lujiajun.apipassenger.controller;

import fun.lujiajun.apipassenger.request.VerificationCodeDTO;
import fun.lujiajun.apipassenger.service.VerificationCodeService;
import fun.lujiajun.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;
    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        System.out.println("------------------------------");
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的手机号参数,"+passengerPhone);
        return  verificationCodeService.generatorCode(passengerPhone);
    }
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String  passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.print("Phone number:"+ passengerPhone+"verification code:"+verificationCode);
        return verificationCodeService.checkCode(passengerPhone,verificationCode);
    }
}

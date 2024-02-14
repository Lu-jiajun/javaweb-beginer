package fun.lujiajun.servicepassengeruser.controller;

import fun.lujiajun.dto.ResponseResult;
import fun.lujiajun.request.VerificationCodeDTO;
//import fun.lujiajun.servicepassengeruser.remote.ServicePassengerUserClient;
import fun.lujiajun.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user")
    public ResponseResult loginOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("passengerPhone: "+ passengerPhone);
        return  userService.loginOrRegister(passengerPhone);
    }

}

package fun.lujiajun.apipassenger.service;

import fun.lujiajun.internalcommon.dto.PassengerUser;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    public ResponseResult getUserByAccessToken(String accessToken){
        PassengerUser passengerUser = new PassengerUser();
        //
        return  ResponseResult.success(passengerUser);
    }
}

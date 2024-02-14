package fun.lujiajun.servicepassengeruser.service;

import fun.lujiajun.dto.ResponseResult;
import fun.lujiajun.servicepassengeruser.dto.PassengerUser;
import fun.lujiajun.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;
    public ResponseResult loginOrRegister(String passengerPhone){
        System.out.println("user service: "+ passengerPhone);

        // search user information by passengerPhone

        Map<String,Object> map = new HashMap<>();
        map.put("passenger_phone",passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println("------------------------------------------------");
//        System.out.println(passengerUsers==null?"无记录":passengerUsers.get(0).getPassengerPhone());

        if(passengerUsers.size() == 0){
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("Zhang3");
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUserMapper.insert(passengerUser);
        }


        // insert user information

        return ResponseResult.success();
    }
}

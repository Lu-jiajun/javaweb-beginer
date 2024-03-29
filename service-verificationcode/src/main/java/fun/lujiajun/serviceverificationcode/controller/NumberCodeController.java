package fun.lujiajun.serviceverificationcode.controller;

import fun.lujiajun.internalcommon.dto.ResponseResult;
import fun.lujiajun.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {
    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size){
        System.out.println("size:"+size);
//        JSONObject result = new JSONObject();
//        result.put("code",1);
//        result.put("message","success");
//        JSONObject data = new JSONObject();

//        data.put("numberCode",resultInt);
//        result.put("data",data);
//        return result.toString();
        double mathRandom = (Math.random()*9 + 1)*(Math.pow(10,size-1));
        int resultInt = (int)mathRandom;
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt);
        return ResponseResult.success(response);
    }

    public static void main(String[] args){
        double mathRandom = (Math.random()*9 + 1)*(Math.pow(10,5));
        System.out.println(mathRandom);
        int resultInt = (int)mathRandom;
        System.out.println(resultInt);
    }
}

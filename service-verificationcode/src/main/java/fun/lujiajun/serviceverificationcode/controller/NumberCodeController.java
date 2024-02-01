package fun.lujiajun.serviceverificationcode.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {
    @GetMapping("/numberCode/{size}")
    public String numberCode(@PathVariable("size") int size){
        System.out.println("size:"+size);
        JSONObject result = new JSONObject();
        result.put("code",1);
        result.put("message","success");
        JSONObject data = new JSONObject();
        double mathRandom = (Math.random()*9 + 1)*(Math.pow(10,size-1));
        int resultInt = (int)mathRandom;
        data.put("numberCode",resultInt);
        result.put("data",data);
        return result.toString();
    }

    public static void main(String[] args){
        double mathRandom = (Math.random()*9 + 1)*(Math.pow(10,5));
        System.out.println(mathRandom);
        int resultInt = (int)mathRandom;
        System.out.println(resultInt);
    }
}

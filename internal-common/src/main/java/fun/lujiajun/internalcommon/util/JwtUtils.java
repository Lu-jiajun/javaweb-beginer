package fun.lujiajun.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private  static  final String SIGN =  "CPFfun!@#$$";
    private static final String JWT_KEY = "passengerPhone";

    public  static String generatorToken(String passengerPhone){
        // guoqishiijan
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY,passengerPhone);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });

        builder.withExpiresAt(date);
        return builder.sign(Algorithm.HMAC256(SIGN));
    }
    public  static String parseToken(String token){
        DecodedJWT verify =  JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Claim claim = verify.getClaim(JWT_KEY);
        return claim.toString();
    }
    public  static  void  main(String[] regs){

        Map<String,String> map = new HashMap<>();
        map.put("name","zhang san");
        map.put("age","18");
        String s = generatorToken("13335208164");
        System.out.println("Token is : " + s);
        System.out.println(parseToken(s));
    }

}

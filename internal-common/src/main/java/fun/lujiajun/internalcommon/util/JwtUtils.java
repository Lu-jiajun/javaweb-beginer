package fun.lujiajun.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import fun.lujiajun.internalcommon.constant.IdentityConstant;
import fun.lujiajun.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private  static  final String SIGN =  "CPFfun!@#$$";
    private static final String JWT_KEY_PHONE = "Phone";
    private static final String JWT_KEY_IDENTITY = "identity";

    private  static  final  String JWT_TOKEN_TYPE = "tokenType";

    private static  final String JWT_TOKEN_TIME  = "tokenTime";

    public  static String generatorToken(String passengerPhone,String identity,String tokenType){
        // guoqishiijan
        Map<String,String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE,passengerPhone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
        map.put(JWT_TOKEN_TIME,date.toString());
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });

//        builder.withExpiresAt(date);
        return builder.sign(Algorithm.HMAC256(SIGN));
    }
    public  static TokenResult parseToken(String token){
        DecodedJWT verify =  JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setIdentity(identity);
        tokenResult.setPhone(phone);
        return tokenResult;
    }

    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;

        try{
            tokenResult = JwtUtils.parseToken(token);
//        }catch (SignatureVerificationException e){
//            resultString = "token sign error";
//            result = false;
//        }catch (TokenExpiredException e){
//            resultString = "token time out";
//            result = false;
//        }catch (AlgorithmMismatchException e){
//            resultString = "token AlgorithmMismatchException";
//            result = false;
        }catch (Exception e){
//            resultString = "token invalid";
//            result = false;
        }
        return  tokenResult;
    }
    public  static  void  main(String[] regs){
        String s = generatorToken("13335208164", IdentityConstant.PASSENGER_IDENTITY,"accessToken");
        System.out.println("the token generate: "+ s);
        System.out.println("the token parsed: "+parseToken(s));
        System.out.println(parseToken(s).getIdentity());
        System.out.println(parseToken(s).getPhone());

    }

}

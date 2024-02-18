package fun.lujiajun.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import fun.lujiajun.internalcommon.constant.TokenConstants;
import fun.lujiajun.internalcommon.dto.ResponseResult;
import fun.lujiajun.internalcommon.dto.TokenResult;
import fun.lujiajun.internalcommon.util.JwtUtils;
import fun.lujiajun.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if(tokenResult == null){
            PrintWriter out = response.getWriter();
            out.println(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity, TokenConstants.ACCESS_TOKEN_TYPE);
            // get token from redis
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis) ||(!token.trim().equals(tokenRedis.trim()))){
                resultString = "token invalid";
                result = false;
            }
        }

        return  result;
    }
}

package com.zxc.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wahaha
 * `@Component` ：通用的注解，可标注任意类为 `Spring` 组件。如果一个 Bean 不知道属于哪个层，可以使用`@Component` 注解标注。
 */

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //应对浏览器的预检机制
        String method = request.getMethod();
        String s = "OPTIONS";
        if(s.equalsIgnoreCase(method)){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null){
            ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录！", null);
            doResponse(response,resultVO);
        }else{
            try {
                //验证token
                JwtParser parser = Jwts.parser();
                //解析token的SigningKey必须和生成token时设置密码一致
                parser.setSigningKey("zhanxiaochen");
                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！", null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "Token不合法，寄！", null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVO resultVO = new ResultVO(ResStatus.LOGIN_FAIL_NOT, "请先登录！", null);
                doResponse(response,resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }

}

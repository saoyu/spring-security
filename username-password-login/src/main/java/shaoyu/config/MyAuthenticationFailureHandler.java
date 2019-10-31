package shaoyu.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leeshaoyu
 * @description MyAuthenticationFailHandler
 * @className MyAuthenticationFailHandler
 * @since 2019/10/29
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        logger.info("登录失败");

        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(e.getMessage());
    }
}
package shaoyu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author leeshaoyu
 * @description MyLogoutSuccessHandler
 * @className MyLogoutSuccessHandler
 * @since 2019/10/31
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

	Logger logger = LoggerFactory.getLogger(MyLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		logger.info("注销成功");

		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("注销成功");
	}
}
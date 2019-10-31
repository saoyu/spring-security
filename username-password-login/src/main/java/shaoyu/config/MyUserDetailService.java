package shaoyu.config;

import com.sun.xml.internal.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shaoyu.dao.UserDO;
import shaoyu.mapper.UserDOMapper;

/**
 * @author leeshaoyu
 * @description MyUserDetailService
 * @className MyUserDetailService
 * @since 2019/10/31
 */

@Component
public class MyUserDetailService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDOMapper userDOMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("输入的用户名：" + username);

		//用户数据库中的密码未加密
		UserDO userDO = userDOMapper.getUserByUsername(username);

		if (userDO == null) {
			logger.info("输入的用户不存在");
			throw new UsernameNotFoundException(username+"不存在");
		}

		logger.info("密码：" + userDO.getPassword());

		//  一般注册时加密
		String password = passwordEncoder.encode(userDO.getPassword());

		// TODO 输入已存在的用户可以进行判断，输入不存在用户没有进行处理
		return new User(username, password, true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

	}
}
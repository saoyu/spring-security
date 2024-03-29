package shaoyu.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author leeshaoyu
 * @description MyPasswordEncoder
 * @className MyPasswordEncoder
 * @since 2019/10/31
 */
public class MyPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(rawPassword.toString());
    }
}
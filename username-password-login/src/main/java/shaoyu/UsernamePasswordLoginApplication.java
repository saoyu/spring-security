package shaoyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author leeshaoyu
 * @description UsernamePasswordLoginApplication
 * @className UsernamePasswordLoginApplication
 * @since 2019/10/31
 */

@MapperScan("shaoyu.mapper")
@SpringBootApplication
public class UsernamePasswordLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsernamePasswordLoginApplication.class, args);
    }
}
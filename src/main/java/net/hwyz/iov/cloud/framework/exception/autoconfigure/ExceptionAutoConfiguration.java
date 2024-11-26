package net.hwyz.iov.cloud.framework.exception.autoconfigure;

import net.hwyz.iov.cloud.framework.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置类
 *
 * @author hwyz_leo
 */
@Configuration
public class ExceptionAutoConfiguration {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

}

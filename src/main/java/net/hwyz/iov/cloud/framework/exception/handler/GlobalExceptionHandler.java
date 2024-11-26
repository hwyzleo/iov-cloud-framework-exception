package net.hwyz.iov.cloud.framework.exception.handler;

import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.hwyz.iov.cloud.framework.common.exception.BaseException;
import net.hwyz.iov.cloud.framework.common.exception.InputParamInvalidException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 全局异常处理
 *
 * @author hwyz_leo
 */
@Slf4j
@RefreshScope
@ControllerAdvice
@ConfigurationProperties(prefix = "error")
public class GlobalExceptionHandler {

    private Map<Integer, String> message;

    public void setMessage(Map<Integer, String> message) {
        this.message = message;
    }

    @ResponseBody
    @ExceptionHandler({BaseException.class})
    public Object serviceException(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.OK.value());
        BaseException exception = (BaseException) ex;
        if (message != null && message.containsKey(exception.getCode())) {
            exception.setMessage(message.get(exception.getCode()));
        }
        // TODO 设置APP
        return exception;
    }

    @ResponseBody
    @ExceptionHandler({IllegalArgumentException.class})
    public Object illegalArgumentException(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.OK.value());
        InputParamInvalidException exception = new InputParamInvalidException(ex.getMessage());
        // TODO 设置APP
        return exception;
    }

    @ResponseBody
    @ExceptionHandler({MissingRequestHeaderException.class})
    public Object missingRequestHeaderExceptionException(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.OK.value());
        InputParamInvalidException exception = new InputParamInvalidException(ex.getMessage());
        // TODO 设置APP
        return exception;
    }

    @ResponseBody
    @ExceptionHandler({FeignException.class})
    public Object feignExceptionException(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.OK.value());
        BaseException exception = new BaseException(ex.getMessage());
        // TODO 设置APP
        return exception;
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Object exceptionException(HttpServletResponse response, Throwable ex) {
        response.setStatus(HttpStatus.OK.value());
        logger.error("未知异常[{}]", ex.getMessage(), ex);
        BaseException exception = new BaseException("未知异常");
        // TODO 设置APP
        return exception;
    }

}

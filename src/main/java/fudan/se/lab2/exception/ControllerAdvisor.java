package fudan.se.lab2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * You can handle all of your exceptions here.
 *
 * @author LBW
 */
//18302010071陈淼'Part
//异常处理系统
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    //登陆时的用户名不存在异常
    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //注册时的用户已存在异常
    @ExceptionHandler(UsernameHasBeenRegisteredException.class)
    ResponseEntity<?> handlerUsernameHasBeenRegisteredException(UsernameHasBeenRegisteredException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //登陆时的密码错误异常
    @ExceptionHandler(WrongPasswordException.class)
    ResponseEntity<?> handlerWrongPasswordException(WrongPasswordException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

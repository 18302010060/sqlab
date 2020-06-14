package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class AuthServiceTest {
    String username1="asdqwe";
    String passwor1="qwe123";
    String username2="asdq01";
    String fullname="Lucy";
    String area="fudan";
    String unit="shanghai";
    String email1="145246@163.com";
    @Autowired
    private AuthService authService;

    //注册成功 注册时用户名已经存在 登陆成功 用未申请的用户进行登录
    @Test
    void register() throws UsernameNotFoundException {
        //正常注册
        String result = authService.register(new RegisterRequest(username1, passwor1, email1, area, unit, fullname));
        assertEquals("注册成功", result);
        //用户名已注册
        String result1 = authService.register(new RegisterRequest(username1, "qwecd123", "1452df46@163.com", area, unit, fullname));
        assertEquals("用户名已存在", result1);

        //"该邮箱已被注册过"
        String result2 = authService.register(new RegisterRequest(username2, passwor1, email1, area, unit, fullname));
        assertEquals("该邮箱已被注册过", result2);

        //正常登陆
        String token1 = authService.login(username1, passwor1);
        assertNotNull(token1);

        //用户名不存在情况
        try {
            authService.login(unit, passwor1);
        } catch (UsernameNotFoundException e) {
            System.out.print("用户名不存在！！");
        }

    }

    //登录时密码错误
    @Test
    void login() throws WrongPasswordException {

        try {
            authService.login(username1, "e123");
        } catch (WrongPasswordException e) {
            System.out.print("密码错误！！");
        }
    }
}
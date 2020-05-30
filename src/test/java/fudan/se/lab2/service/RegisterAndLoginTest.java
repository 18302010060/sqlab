package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class RegisterAndLoginTest {//集成测试：注册和登录模块
    @Autowired
    private AuthService authService;

    @Test
    void test(){
        //成功注册
        String result = authService.register(new RegisterRequest("a", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        assertEquals("注册成功", result);

        //用户名已注册
        String result1 = authService.register(new RegisterRequest("a", "qwecd123", "1452df46@163.com", "fudan", "shanghai", "Lucy"));
        assertEquals("用户名已存在", result1);

        //"该邮箱已被注册过"
        String result2 = authService.register(new RegisterRequest("b", "qwe123", "145246@163.com", "fudan", "shanghai", "Lucy"));
        assertEquals("该邮箱已被注册过", result2);

        //正常登陆
        String token1 = authService.login("a", "qwe123");
        assertNotNull(token1);

        //用户名不存在情况/账号错误
        try {
            authService.login("c", "qwe123");
        } catch (UsernameNotFoundException e) {
            System.out.print("用户名不存在！！");
        }

        //用户密码错误
        try {
            authService.login("a", "e123");
        } catch (WrongPasswordException e) {
            System.out.print("密码错误！！");
        }

    }
}

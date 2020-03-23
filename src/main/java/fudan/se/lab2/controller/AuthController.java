package fudan.se.lab2.controller;

import fudan.se.lab2.service.AuthService;
import fudan.se.lab2.service.JwtUserDetailsService;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//18302010060 黄怡清'part
@RestController
@RequestMapping
public class AuthController {

    private AuthService authService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        //工作日志：输出注册请求的参数
        logger.info("RegistrationForm: " + request.toString());
        logger.info("username: " + request.getUsername());
        logger.info("password: " + request.getPassword());
        logger.info("email: " + request.getEmail());
        logger.info("area: " + request.getArea());
        logger.info("unit: " + request.getUnit());
        //进入authService中的register函数进行处理登录请求
        return ResponseEntity.ok(authService.register(request).getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());
       /*对于后端是否能够接受到数据的检验
        System.out.println(request.getUsername());
        System.out.println(request.getPassword());*/
       //工作日志：输出登录请求的参数
        logger.info("LoginForm: " + request.toString());
        logger.info("username: " + request.getUsername());
        logger.info("password: " + request.getPassword());
        //进入authService中的login函数进行处理登录请求
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to 2020 Software Engineering Lab2 xxx. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/index")
    public ResponseEntity<?> index() {
        Map<String, String> response = new HashMap<>();
        String message = "this is an index ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

}




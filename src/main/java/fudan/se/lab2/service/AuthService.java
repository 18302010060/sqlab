package fudan.se.lab2.service;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.exception.WrongPasswordException;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LBW
 */
@Service
//用户登陆注册功能实现18302010071陈淼'Part + 18302010077张超'Part
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    //注册功能 18302010071陈淼'Part + 18302010077张超'Part
    public User register(RegisterRequest request)throws UsernameHasBeenRegisteredException {
        // TODO: Implement the function.
        //从request中获取用户注册信息的基本参数
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        String area = request.getArea();
        String unit = request.getUnit();

        //对于authority属性的处理
        Authority authority = authorityRepository.findByAuthority("admin");
        if (authority == null) {
            authority = new Authority("admin");
            authorityRepository.save(authority);
        }

        //判断注册用户是否已经存在，若存在则进入用户已存在异常处理程序
        Optional<User> users = Optional.ofNullable(userRepository.findByUsername(username));
        if(users.isPresent()) {
            throw new UsernameHasBeenRegisteredException(username);
        }

        //根据输入的五个参数以及authority构造USER新用户
        User user = new User(username, password, email, area, unit, new HashSet<>(Collections.singletonList(authority)));
        //在数据库中进行保存
        userRepository.save(user);
        return user;
    }

    //登录功能 18302010071陈淼'Part
    public String login(String username, String password)throws WrongPasswordException {
        // TODO: Implement the function.
        //在用户仓库中新建用户信息查询类
        JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);
        //在类中调用重载方法，判断在库中是否存在该用户（若不存在则会在方法中进入异常处理程序）
        UserDetails user = jwtUserDetailsService.loadUserByUsername(username);
        if(!password.equals(user.getPassword())){
            throw new WrongPasswordException();
        }else {

            return "登陆成功";
        }


    }


}

package fudan.se.lab2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;

import fudan.se.lab2.service.MyTest;
import fudan.se.lab2.service.Token;
import fudan.se.lab2.service.loanService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;

/**
 * Welcome to 2020 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 */
//@SpringBootApplication

@SpringBootApplication

public class Lab2Application {

    public static void main(String[] args) throws UnsupportedEncodingException {
        MyTest myTest = new MyTest();
        loanService loanService = new loanService();
        String s = loanService.postCardAuthenticationJson("http://10.176.122.172:8012/sys/login/restful","username=BA2103154881&password=imbus123");
        System.out.println(s);
        Token tokenObject=JSON.parseObject(JSON.parse(s).toString(),Token.class);
        String token = tokenObject.getToken();
        loanService.insertLoan("http://10.176.122.172:8012/loan",token,"1",500,"1",1,"100","2021/3/26 00:00:00","2021/3/25 00:00:00");

        System.out.println(token);
        String param = "pageNum=10&pageSize=10&params=%7B%22loanStatus%22:1%7D&"+"login-token="+tokenObject.getToken();


        System.out.println(loanService.sendGet("http://10.176.122.172:8012/loan",param));


        //SpringApplication.run(Lab2Application.class, args);

    }

   /* *//**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     * You can change it as you like.
     *//*
    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Logger logger = LoggerFactory.getLogger(AuthController.class);

                Authority adminAuthority = getOrCreateAuthority("Admin", authorityRepository);


                if (userRepository.findByUsername("admin") == null) {
                    logger.info("no admin");
                    User admin = new User(
                            "admin",
                            "password",
                            new HashSet<>(Collections.singletonList(adminAuthority))
                    );
                    userRepository.save(admin);
                    logger.info("save admin");
                }


            }

            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText);
                    authorityRepository.save(authority);
                }
                return authority;
            }
        };
    }*/
}


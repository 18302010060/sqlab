package fudan.se.lab2.service;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author LBW
 */
@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;

    }

    public User register(RegisterRequest request)throws UsernameHasBeenRegisteredException {
        // TODO: Implement the function.

        String username = request.getUsername();
        String fullname = request.getFullname();
        String password = request.getPassword();
        String email = request.getEmail();
        String district = request.getDistrict();
        String workUnit = request.getWorkUnit();
        //Set<String> authorities = request.getAuthorities();

        Authority authority = authorityRepository.findByAuthority("admin");
        if (authority == null) {
            authority = new Authority("admin");
            authorityRepository.save(authority);
        }
        User user;
        if(userRepository.findByUsername(username)!=null){
            throw new UsernameHasBeenRegisteredException(username);
        }else {

             user = new User(
                    username,
                    password,
                    fullname,
                    email,
                    district,
                    workUnit,
                    new HashSet<>(Collections.singletonList(authority))
            );
            userRepository.save(user);
        }
        return user;
    }

    public String login(String username, String password) {
        // TODO: Implement the function.
        JwtUserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);
        UserDetails user = jwtUserDetailsService.loadUserByUsername(username);
        if(!password.equals(user.getPassword())){
            return "登陆失败";
        }else {
            return "登陆成功";
        }


    }


}

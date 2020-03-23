package fudan.se.lab2.service;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LBW
 */
@Service
//18302010071陈淼'Part
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Implement the function.
        //使用Optional数据结构进行查找避免不必要的null检查（创建一个既可空又可非空的Option对象）
        Optional<User> users = Optional.ofNullable(userRepository.findByUsername(username));
        //调用Optional中的isPresent方法判断值是否存在，若不存在则进行异常处理
        if(!users.isPresent()){
            throw new UsernameNotFoundException("User: '" + username + "' not found.");
        }
        //创建属于该对象的Authorities参数用于存储（目前对于其中的具体内容不作要求）
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("user"));
        //最后目的：返回一个具有完整信息的User/UserDetails对象
        return (UserDetails) new User(users.get().getUsername(),users.get().getPassword(),authorities);
    }
}

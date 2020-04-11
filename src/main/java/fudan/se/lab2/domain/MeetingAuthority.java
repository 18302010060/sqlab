package fudan.se.lab2.domain;

import fudan.se.lab2.service.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * @author LBW
 */
//18302010071陈淼'Part + 18302010077张超'Part
@Entity
@IdClass(usernameAndFullname.class)
public class MeetingAuthority {

    private static final long serialVersionUID = -6140085056226164016L;


    /*@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/


    @Id
    private String username;
    @Id
    private String fullname;
    private String authority;



    /*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();*/

    //User的构造方法
    //（username + password + authorities） 用于登录
    //（username + password + unit + area + authorities ） 用于注册
    public MeetingAuthority() {}
    public MeetingAuthority(String username, String fullname, String authority) {
        this.username = username;
        this.fullname = fullname;
        this.authority = authority;
    }
    //对应的GET SET方法


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

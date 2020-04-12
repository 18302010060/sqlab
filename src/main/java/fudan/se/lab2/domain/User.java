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
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String unit;
    private String area;
    private String fullname;//真实姓名


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    //User的构造方法
    //（username + password + authorities） 用于登录
    //（username + password + unit + area + authorities ） 用于注册
    public User() {}
    public User(String username, String password,  Set<Authority> authorities) {
        this.username = username;
        this.password= password;
        this.authorities = authorities;
    }
    public User(String username, String password, String email,String unit,String area,String fullname,Set<Authority> authorities) {
        this.username = username;
        this.password= password;
        this.authorities = authorities;
        this.email=email;
        this.area=area;
        this.unit=unit;
        this.fullname=fullname;
    }


    //对应的GET SET方法
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }


    public String getArea() {
        return area;
    }


    public String getUnit() {
        return unit;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}

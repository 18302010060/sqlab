package fudan.se.lab2.security.jwt;

import fudan.se.lab2.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This is a util class to use JWT.
 * We give it to you for free. :)
 *
 * @author LBW
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3839549913040578986L;

    //private static JwtConfigProperties jwtConfigProperties;

    private JwtConfigProperties jwtConfigProperties;

    public JwtTokenUtil(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    //生成Token
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .addClaims(claims)/* 自定义属性 */
                .setSubject(user.getUsername())/* 该JWT所面向的用户 */
                .setIssuedAt(new Date(System.currentTimeMillis())) /* 设置发放的时间，类型为： Date*/
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfigProperties.getValidity())) /* 设置过期时间 类型为：Date */
                .signWith(SignatureAlgorithm.HS512, jwtConfigProperties.getSecret())/* jwt签名算法和密钥 */
                .compact();/* 返回一个URL安全JWT字符串 */
    }

    //从token中得到Username
    public String getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }


    //验证Token
    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }
    //从 token 那里获取到期日期
    private Date getExpirationDateFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getExpiration);
    }

    //
    private <T> T getClaimFromToken(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String jwtToken) {
        return Jwts.parser().setSigningKey(jwtConfigProperties.getSecret()).parseClaimsJws(jwtToken).getBody();
    }


    //判断是否过期
    private boolean isTokenExpired(String jwtToken) {
        final Date expiration = getExpirationDateFromToken(jwtToken);
        return expiration.before(new Date());
    }


}

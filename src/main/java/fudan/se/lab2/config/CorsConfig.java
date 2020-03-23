package fudan.se.lab2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author LBW
 */

//跨域问题解决  18302010071陈淼'Part
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //TODO: If you encounter some Cross-Domain problems（跨域问题）, Maybe you can do something here.
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                        .allowCredentials(true)
                        .maxAge(3600)
                        .exposedHeaders("access-control-allow-headers",
                                "access-control-allow-methods",
                                "access-control-allow-origin",
                                "access-control-max-age",
                                "X-Frame-Options")
                        .allowedHeaders("*");
            }
        };
    }
}

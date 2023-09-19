package book.yes48.config.security;

import book.yes48.entity.member.Role;
import book.yes48.security.auth.PrincipleDetailsService;
import book.yes48.security.oauth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private final CustomOauth2UserService customOauth2UserService;

    @Autowired
    private PrincipleDetailsService memberDetailsService;

    @Bean
    protected DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable()
                    .httpBasic()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/myPage/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/order/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().permitAll()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("userId")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/")
                    .failureHandler(failHandler())
                    .successHandler(customLoginSuccessHandler())
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(customOauth2UserService);

        return http.build();
    }

    @Bean
    public CustomAuthenticationFailHandler failHandler() {
        return new CustomAuthenticationFailHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }

}

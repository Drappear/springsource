package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity // 웹에 적용할 시큐리티 클래스
@Configuration // 환경설정
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/sample/guest", "/auth").permitAll()
                .requestMatchers("/sample/member").hasRole("USER")
                .requestMatchers("/sample/admin").hasRole("ADMIN"))
                // .formLogin(Customizer.withDefaults()); // spring security 기본 로그인 페이지
                .formLogin(login -> login.loginPage("/member/login").permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean // 스프링 컨테이너가 관리하는 객체(다른 곳에서 passwordEncoder가 필요할때 자동 주입)
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user1")
                .password("{bcrypt}$2a$10$XdkT6q63R4R3.yAVCB7GsumsRSYWIuQsMKGq0VvnjHKlKvE9Cc6um")
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$XdkT6q63R4R3.yAVCB7GsumsRSYWIuQsMKGq0VvnjHKlKvE9Cc6um")
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}

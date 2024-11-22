package com.example.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity // 접근제한을 어노테이션으로 처리하기 위해 필요
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    // security 적용할 url 상세 설정(Filter 등록)
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/img/*", "/js/*", "/css/*", "/assets/*").permitAll()
                .requestMatchers("/board/modify").hasAnyRole("MEMBER", "ADMIN")
                .anyRequest().permitAll());

        http.formLogin(login -> login.loginPage("/member/login").permitAll());

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));
        return http.build();
    }

    // remember
    // @Bean
    // RememberMeServices rememberMeServices(UserDetailsService userDetailsService)
    // {
    // RememberMeTokenAlgorithm rMeTokenAlgorithm = RememberMeTokenAlgorithm.SHA256;
    // TokenBasedRememberMeServices rememberMeServices = new
    // TokenBasedRememberMeServices("myKey", userDetailsService,
    // rMeTokenAlgorithm);
    // rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
    // return rememberMeServices;
    // }

    // 비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

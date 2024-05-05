package org.zerock.foodnamdo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.foodnamdo.security.APIUserDetailsService;
import org.zerock.foodnamdo.security.filter.APILoginFilter;
import org.zerock.foodnamdo.security.filter.RefreshTokenFilter;
import org.zerock.foodnamdo.security.filter.TokenCheckFilter;
import org.zerock.foodnamdo.security.handler.APILoginSuccessHandler;
import org.zerock.foodnamdo.util.JWTUtil;


@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class CustomSecurityConfig {

    private final APIUserDetailsService apiUserDetailsService;
    private final JWTUtil jwtUtil;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        log.info("----------------web configure--------------------");
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(apiUserDetailsService)
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.authenticationManager(authenticationManager);
        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
        apiLoginFilter.setAuthenticationManager(authenticationManager);

        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);

        http.addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil), TokenCheckFilter.class);

        http.csrf(config -> config.disable());
        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
        return new TokenCheckFilter(jwtUtil);
    }
}

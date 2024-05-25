package org.zerock.foodnamdo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zerock.foodnamdo.repository.UserManagementRepository;
import org.zerock.foodnamdo.security.filter.APILoginFilter;
import org.zerock.foodnamdo.security.filter.RefreshTokenFilter;
import org.zerock.foodnamdo.security.filter.TokenCheckFilter;
import org.zerock.foodnamdo.security.service.APIUserDetailsService;
//import org.zerock.foodnamdo.security.handler.APILoginSuccessHandler;
import org.zerock.foodnamdo.util.JWTUtil;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class CustomSecurityConfig {

    private final UserManagementRepository userManagementRepository;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                "/generateToken",
                                "/refreshToken",
                                "/swagger-ui/**",
                                "/files/apiLogin.html",
                                "/files/refreshTest.html",
                                "/files/sendJWT.html",
                                "/usermanagement/**",
                                "/mypage/**",
                                "/mainsystem/**",
                                "/v3/api-docs/**"
                        ).permitAll()
//                        .requestMatchers("/usermanagement/deleteUser", "/mainsystem/**", "/mypage/myInfo").authenticated()
                        .anyRequest().authenticated())
//                .addFilterBefore(apiLoginFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(tokenCheckFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenCheckFilter(jwtUtil, apiUserDetailsService, userManagementRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new RefreshTokenFilter("/refreshToken", jwtUtil), TokenCheckFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    private APILoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
//        APILoginFilter apiLoginFilter = new APILoginFilter("/generateToken");
//        apiLoginFilter.setAuthenticationManager(authenticationManager);
//        APILoginSuccessHandler successHandler = new APILoginSuccessHandler(jwtUtil);
//        apiLoginFilter.setAuthenticationSuccessHandler(successHandler);
//        return apiLoginFilter;
//    }

    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil, APIUserDetailsService apiUserDetailsService, UserManagementRepository userManagementRepository) {
        return new TokenCheckFilter(jwtUtil, apiUserDetailsService, userManagementRepository);
    }
//    private TokenCheckFilter tokenCheckFilter(JWTUtil jwtUtil) {
//        return new TokenCheckFilter(jwtUtil);
//    }
}

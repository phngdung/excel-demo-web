//package com.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/**").permitAll() // GET requests don't need auth
//                .anyRequest()
//                .authenticated()
//                .and()
//                .cors()
//                .configurationSource(corsConfigurationSource())
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
//    }
//
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedMethods(List.of(
//                HttpMethod.GET.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.DELETE.name()
//        ));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
//        return source;
//    }
//
//}
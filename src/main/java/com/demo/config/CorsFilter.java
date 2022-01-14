package com.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static java.util.Collections.emptyList;

@Component
public class CorsFilter extends OncePerRequestFilter {

    Logger log = Logger.getLogger(this.getClass().getName());

    private String getToken(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null) {
            return request.getHeader("Authorization").replaceAll("Bearer", "").trim();
        }
        if (WebUtils.getCookie(request, WebSecurityConfig.TOKEN_NAME) != null) {
            return WebUtils.getCookie(request, WebSecurityConfig.TOKEN_NAME).getValue();
        }
        if (request.getParameter(WebSecurityConfig.TOKEN_NAME) != null) {
            return request.getParameter(WebSecurityConfig.TOKEN_NAME);
        }
        if (request.getHeader(WebSecurityConfig.TOKEN_NAME) != null) {
            return request.getHeader(WebSecurityConfig.TOKEN_NAME);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = this.getToken(request);
        if (token == null || token.equals("")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(WebSecurityConfig.TOKEN_SECRET);
            JWTVerifier verifier =
                    JWT.require(algorithm).withIssuer(WebSecurityConfig.TOKEN_ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getId();
//            log.info("Logged user id " + userId);
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null, emptyList()));
        } catch (Exception e) {
//            log.warning("decode token ex : " + e.getMessage());
        }

        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
        filterChain.doFilter(request, res);
    }

    @Override
    public void destroy() {
    }
}
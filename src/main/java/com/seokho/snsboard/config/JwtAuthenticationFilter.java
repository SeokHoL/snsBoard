package com.seokho.snsboard.config;

import com.seokho.snsboard.exception.jwt.JwtTokenNotFoundException;
import com.seokho.snsboard.service.JwtService;
import com.seokho.snsboard.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtService jwtService;
    @Autowired private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //TODO: JWT 검증
        String BEARER_PREFIX = "Bearer ";
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        var securityContext = SecurityContextHolder.getContext();

        //내가 따로 jwt 메세지를 보내고 싶다면. 이렇게 만든다.
//        if (ObjectUtils.isEmpty(authorization) || !authorization.startsWith(BEARER_PREFIX)){
//            throw new JwtTokenNotFoundException();
//        }

        if(!ObjectUtils.isEmpty(authorization) && authorization.startsWith(BEARER_PREFIX)
        && securityContext.getAuthentication()==null){

            var accessToken = authorization.substring(BEARER_PREFIX.length());
            var username = jwtService.getUsername(accessToken);
            var userDetails =  userService.loadUserByUsername(username);

            var authenticationToken =new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);
        }

        filterChain.doFilter(request, response);

    }
}

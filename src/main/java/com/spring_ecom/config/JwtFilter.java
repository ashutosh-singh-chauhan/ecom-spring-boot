package com.spring_ecom.config;


import com.spring_ecom.aop.ValidationAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring_ecom.service.JwtService;
import com.spring_ecom.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtFilter
 */
@Component 
public class JwtFilter extends OncePerRequestFilter {
    
  private final ValidationAspect validationAspect;

  @Autowired 
  JwtService jwtService;

  @Autowired 
  ApplicationContext context;

  JwtFilter(ValidationAspect validationAspect) {
    this.validationAspect = validationAspect;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String token = null;
    String userName = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
      userName = jwtService.extractUserName(token);
    }

    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);

      if (jwtService.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = 
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }

    }

    filterChain.doFilter(request, response);
  } 
}

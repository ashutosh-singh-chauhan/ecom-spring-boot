package com.spring_ecom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {

  @Autowired 
  private UserDetailsService userDetailsService;

  @Autowired 
  private JwtFilter jwtFilter;



  // Automatiallcy create a bean of this method and keep it in JVM Container
  @Bean 
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // by default csrf is enable in spring projects. 
    // To male your project stateless (avoid managing sessions) we can disable csrf like this
    http.csrf(customizer -> customizer.disable());
    // It makes all the /admin routes authenticated
    // http.authorizeHttpRequests(auth -> auth
    // .requestMatchers("/admin").authenticated()
    // .anyRequest().permitAll());
    // Allow anonymous access to registration endpoint, require auth for others
    http.authorizeHttpRequests(req -> req
        .requestMatchers("/register", "/login", "/swagger-ui/**", "/swagger-ui.html").permitAll()
      .anyRequest().authenticated());
    // Allow default username and pass
    http.httpBasic(Customizer.withDefaults());
    // make the application statless - Basically new session for each request
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
  // for authorizing the hard coded users
  // @Bean 
  // public UserDetailsService userDetailsService() {
  //   // this is how you can add a default users for authentication (never recommended for production).
  //   UserDetails user = User.withDefaultPasswordEncoder()
  //    .username("user")
  //    .password("1234")
  //    .roles("USER")
  //    .build();
  //   return new InMemoryUserDetailsManager(user);
  // }


  @Bean 
  public AuthenticationProvider authProvider() {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    return provider;
  }

  @Bean 
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
    return config.getAuthenticationManager();

  }

}

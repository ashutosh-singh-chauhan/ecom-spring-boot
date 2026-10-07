package com.spring_ecom;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.spring_ecom.service.JwtService;

class JwtServiceTest {

    @Test
    void generateAndValidateToken_shouldWork() {
        JwtService jwtService = new JwtService();
        UserDetails user = User.withUsername("alice").password("encoded-password").authorities("USER").build();

        String token = jwtService.generateToken("alice");

        assertNotNull(token);
        assertTrue(jwtService.validateToken(token, user));
    }
}

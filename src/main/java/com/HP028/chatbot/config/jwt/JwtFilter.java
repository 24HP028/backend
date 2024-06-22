package com.HP028.chatbot.config.jwt;

import com.HP028.chatbot.common.response.ApiFailStatus;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.split(" ")[1];
            ApiFailStatus apiFailStatus = jwtUtil.validateToken(token);
            if (apiFailStatus == null) {
                String email = jwtUtil.getUsername(token);
                String password = "tmppassword";
                String role = jwtUtil.getRole(token);

                CustomUserInfo member = new CustomUserInfo(email, password, role);

                CustomUserDetails customUserDetails = new CustomUserDetails(member);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                customAuthenticationEntryPoint.setResponseMessage(response, apiFailStatus);
            }

        }
        filterChain.doFilter(request, response);
    }
}

package com.V17Tech.social_commerce_platform_v2.configuration;

import com.V17Tech.social_commerce_platform_v2.util.CommonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class TokenValidationFilter extends OncePerRequestFilter {
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api/auth/users/")) {
            System.out.println(request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenFromRequest(request);
        String username = CommonUtil.getUserNameFromToken(token);
        if (token != null && redisTemplate.opsForValue().get("token of:" + username) != null) {
            System.out.println("qua filter");
            filterChain.doFilter(request, response);
        } else {
            System.out.println(token);
            System.out.println(username);
            System.out.println( redisTemplate.opsForValue().get(username));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }


}
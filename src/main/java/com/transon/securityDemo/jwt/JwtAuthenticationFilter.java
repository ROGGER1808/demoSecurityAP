package com.transon.securityDemo.jwt;

import com.transon.securityDemo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService UserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
          final String authorizationHeader = request.getHeader("Authorization");

          String username = null;
          String token = null;

          if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
              token= authorizationHeader.substring(7);
              username = jwtUtil.extractUsername(token);
          }

          if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
              if (jwtUtil.validateToken(token, userDetails)){
                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                          new UsernamePasswordAuthenticationToken(
                          userDetails, null, userDetails.getAuthorities()
                  );
                  usernamePasswordAuthenticationToken
                          .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
              }
          }

        filterChain.doFilter(request, response);
    }

}

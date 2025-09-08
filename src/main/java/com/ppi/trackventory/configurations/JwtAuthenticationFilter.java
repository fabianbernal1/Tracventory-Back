package com.ppi.trackventory.configurations;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ppi.trackventory.services.impl.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = this.jwtUtil.extractUsername(jwtToken);
            } catch (ExpiredJwtException exception) {
                System.out.println("Token has expired");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Invalid token, does not start with bearer string");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtUtil.validateToken(jwtToken, userDetails)) {

                // 🔹 Extraer permisos del token
                Claims claims = jwtUtil.extractAllClaims(jwtToken);
                @SuppressWarnings("unchecked")
				List<String> permissions = claims.get("permissions", List.class);

                // 🔹 Convertir permisos a authorities
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (permissions != null) {
                    for (String permiso : permissions) {
                        authorities.add(new SimpleGrantedAuthority(permiso));
                    }
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 🔹 Guardar en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            System.out.println("Invalid token");
        }
        filterChain.doFilter(request, response);
    }
}

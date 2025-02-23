package com.rbtmensagens.rbt_app.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtprovider;
    
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        DecodedJWT decodedjwt = null;
        String jwt = null;
        String username = null;

        logger.info("authorizationHeader " +authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            decodedjwt = jwtprovider.validateAccessToken(jwt);
            username = decodedjwt.getSubject();
        }
        logger.info("decodedjwt ");
        if (decodedjwt != null) {
            try {
            	var user = userDetailsService.loadUserByUsername(username);
                if (user != null){
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                throw new BadCredentialsException(e.getMessage());
            }
        }
        try{
            logger.info("chain.doFilter");
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.info("ERROWW "+e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }
    }
}
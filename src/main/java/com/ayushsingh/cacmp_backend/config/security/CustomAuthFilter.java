package com.ayushsingh.cacmp_backend.config.security;

import com.ayushsingh.cacmp_backend.config.security.service.ConsumerDetailsService;
import com.ayushsingh.cacmp_backend.config.security.service.CustomUserDetailsService;
import com.ayushsingh.cacmp_backend.config.security.service.DepartmentDetailsService;
import com.ayushsingh.cacmp_backend.config.security.util.JwtUtil;
import com.ayushsingh.cacmp_backend.constants.AppConstants;
import com.ayushsingh.cacmp_backend.util.exceptionUtil.ApiException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

import static com.ayushsingh.cacmp_backend.constants.AppConstants.AUTH_HEADER;

public class CustomAuthFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;
    @Autowired
    private  DepartmentDetailsService departmentDetailsService;
    @Autowired
    private  ConsumerDetailsService consumerDetailsService;

    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    public CustomAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Fo HTTP cookie authentication
        String token=this.getTokenFromCookie(request);
        String username = "";
        String password = "";
        UsernamePasswordAuthenticationToken authenticationToken;
        String uri = request.getRequestURI();
        String headerToken = "";
        if(token==null){
            headerToken = request.getHeader(AUTH_HEADER);
        }
        else{
            headerToken=token;
        }

        if (headerToken == null || (!headerToken.startsWith(AppConstants.BASIC_TOKEN_PREFIX) && !headerToken.startsWith(AppConstants.BEARER_TOKEN_PREFIX))) {
            filterChain.doFilter(request, response);
            return;
        }
        if (headerToken.startsWith(AppConstants.BASIC_TOKEN_PREFIX) && uri.endsWith(AppConstants.SIGN_IN_URI_ENDING)) {
            headerToken = StringUtils.delete(headerToken, AppConstants.BASIC_TOKEN_PREFIX).trim();
            username = JwtUtil.decodedBase64(headerToken)[0];
            password = JwtUtil.decodedBase64(headerToken)[1];
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticationResult = null;
            try {
                authenticationResult = this.authenticationManager.authenticate(authenticationToken);

            } catch (AuthenticationException e) {

                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.unauthenticated(username, password));
                exceptionResolver.resolveException(request, response, null, e);
            }
            if (authenticationResult != null) {
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            }

            filterChain.doFilter(request, response);
        } else if (headerToken.startsWith(AppConstants.BEARER_TOKEN_PREFIX) && !uri.endsWith(AppConstants.SIGN_IN_URI_ENDING)) {
            UserDetails userDetails = null;
            try {
                headerToken = StringUtils.delete(headerToken, AppConstants.BEARER_TOKEN_PREFIX).trim();
                String entityType = JwtUtil.extractEntityType(headerToken);
                username = JwtUtil.extractUsername(headerToken);


                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if (entityType.equals(AppConstants.ENTITY_TYPE_CONSUMER)) {
                        userDetails = this.consumerDetailsService.loadUserByUsername(username);
                    } else if (entityType.equals(AppConstants.ENTITY_TYPE_USER)) {
                        userDetails = this.customUserDetailsService.loadUserByUsername(username);
                    }
                    else if (entityType.equals(AppConstants.ENTITY_TYPE_DEPARTMENT)) {
                        userDetails = this.departmentDetailsService.loadUserByUsername(username);
                    }
                    if (userDetails == null) {
                        throw new ApiException("User not found with username: "+username);
                    } else if (JwtUtil.validateToken(headerToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                        filterChain.doFilter(request, response);
                    } else {
                        throw new ApiException("Token validation returned false");
                    }
                } else {
                    throw new ApiException("Username not found in token");
                }
            } catch (ExpiredJwtException e) {
                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.unauthenticated(userDetails, null));

                exceptionResolver.resolveException(request, response, null, e);
            } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException |
                     ApiException e) {
                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.unauthenticated(userDetails, null));

                exceptionResolver.resolveException(request, response, null, new ApiException(e.getMessage()));
            }

        }
    }

    private String getTokenFromCookie(HttpServletRequest httpServletRequest){
        Cookie cookie= WebUtils.getCookie(httpServletRequest, accessTokenCookieName);
        return cookie != null ? cookie.getValue():null;
    }
}

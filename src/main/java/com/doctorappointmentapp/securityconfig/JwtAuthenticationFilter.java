package com.doctorappointmentapp.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //steps
//        //1.get token
        String requestTokenHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        //checking null and format of token
        if (requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
            //jwtToken = requestTokenHeader.substring(7);
            jwtToken = requestTokenHeader;
            try{
                userName = this.jwtTokenHelper.getUserNameFromToken(jwtToken);
//                userName =request.getHeader("userName");
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Unable to get jwt token");
            } catch (ExpiredJwtException e){
                e.printStackTrace();
                System.out.println("Token expired");
            } catch (MalformedJwtException e){
                e.printStackTrace();
                System.out.println("Invalid jwt token");
            } catch (Exception e){
                e.printStackTrace();
            }
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userName);

            //2.validate token
            //security
            if (userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

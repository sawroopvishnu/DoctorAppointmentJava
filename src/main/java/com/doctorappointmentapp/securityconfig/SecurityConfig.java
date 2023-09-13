package com.doctorappointmentapp.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll() // Allow registration and login
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll() // All GET requests are allowed
//                .antMatchers(HttpMethod.POST, "/api/**").permitAll() // All POST requests are allowed
//                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll() // All DELETE requests are allowed
//                .antMatchers(HttpMethod.PUT, "/api/**").permitAll() // All PUT requests are allowed
//                .anyRequest().authenticated()
//            .and()
//            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow preflight requests
            .antMatchers("/api/auth/**").permitAll() // Allow registration and login
            .antMatchers("/api/appointments/**").permitAll() // Adjust this as needed for your API endpoints
            .antMatchers((HttpMethod.GET)).permitAll() //allowing all GET method fetch
            .antMatchers((HttpMethod.POST)).permitAll()
            .antMatchers((HttpMethod.DELETE)).permitAll()
            .antMatchers((HttpMethod.PUT)).permitAll()
            .anyRequest().authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    //original code 
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll() // Allow registration and login
//                .antMatchers((HttpMethod.GET)).permitAll() //allowing all GET method fetch
//                .antMatchers((HttpMethod.POST)).permitAll()
//                .antMatchers((HttpMethod.DELETE)).permitAll()
//                .antMatchers((HttpMethod.PUT)).permitAll()
//                .anyRequest().authenticated()
//            .and()
//            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

    //setup with frontend
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*"); //we will allow only those URL's on which frontend in running
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.setMaxAge(3600L);

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(urlBasedCorsConfigurationSource));
        return bean;
    }
}


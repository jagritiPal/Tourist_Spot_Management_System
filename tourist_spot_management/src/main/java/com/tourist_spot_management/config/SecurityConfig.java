package com.tourist_spot_management.config;

import com.tourist_spot_management.security.JwtAuthenticationEntryPoint;
import com.tourist_spot_management.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable() // Disable CSRF protection
                .authorizeRequests()
                .antMatchers("/api/spots/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/spots/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/spots/**").hasRole("ADMIN")
                .antMatchers("/api/spots/**").authenticated()
                .antMatchers("/api/bookings/add").authenticated()
                .antMatchers("/api/bookings/**").hasRole("ADMIN")
                .antMatchers("/api/reports/**").hasRole("ADMIN")
                .antMatchers("/api/guides/add").hasAnyRole("ADMIN", "GUIDE")
                .anyRequest()
                .permitAll() // Permit all requests without authentication
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

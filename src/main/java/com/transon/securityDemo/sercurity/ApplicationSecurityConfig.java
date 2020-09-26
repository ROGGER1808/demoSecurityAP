package com.transon.securityDemo.sercurity;

import com.transon.securityDemo.auth.MyUserDetailService;
import com.transon.securityDemo.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsService userService;

    public ApplicationSecurityConfig(MyUserDetailService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/management/roles/**").permitAll()
                .antMatchers(HttpMethod.POST , "/api/v1/auth/**").permitAll()
                .antMatchers(HttpMethod.POST , "/api/v1/upload/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/management/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/management/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/management/**").hasRole("ADMIN")
                //.antMatchers("/api/v1/management/roles/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/management/users/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/management/students/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v1/management/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/swagger").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger/**").permitAll()
                .antMatchers(HttpMethod.POST , "/api/v1/auth/changePassword").authenticated()
                .antMatchers("/", "/css/**", "/js/**", "index", "/home").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token"));
        configuration.setExposedHeaders(Collections.singletonList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

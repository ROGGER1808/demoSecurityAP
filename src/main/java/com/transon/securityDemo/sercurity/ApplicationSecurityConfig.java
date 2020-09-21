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
                .antMatchers(HttpMethod.POST , "/api/v1/auth/**").permitAll()
                .antMatchers(HttpMethod.POST , "/api/v1/upload/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/management/students/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/management/students/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/management/students/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/management/students/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v1/management/students").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/swagger").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger/**").permitAll()
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
}

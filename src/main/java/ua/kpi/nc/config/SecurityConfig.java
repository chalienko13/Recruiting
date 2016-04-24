package ua.kpi.nc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.kpi.nc.config.filter.StatelessAuthenticationFilter;
import ua.kpi.nc.config.filter.StatelessLoginFilter;
import ua.kpi.nc.controller.auth.TokenAuthenticationService;
import ua.kpi.nc.service.util.AuthenticationSuccessHandlerService;
import ua.kpi.nc.service.util.UserAuthService;

/**
 * Created by dima on 12.04.16.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserAuthService userAuthService = UserAuthService.getInstance();

    private TokenAuthenticationService tokenAuthenticationService;



    public SecurityConfig() {

        tokenAuthenticationService = new TokenAuthenticationService("verySecret", userAuthService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login/auth").permitAll()
                .anyRequest().permitAll().and()
                .addFilterBefore(new StatelessLoginFilter("/login/auth", tokenAuthenticationService, userAuthService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().cacheControl().and();
    }



    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userAuthService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected UserAuthService userDetailsService() {
        return userAuthService;
    }
}
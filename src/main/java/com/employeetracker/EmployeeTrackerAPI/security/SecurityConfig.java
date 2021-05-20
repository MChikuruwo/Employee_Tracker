package com.employeetracker.EmployeeTrackerAPI.security;

import com.employeetracker.EmployeeTrackerAPI.handler.ExceptionHandlerFilter;
import com.employeetracker.EmployeeTrackerAPI.models.User;
import com.employeetracker.EmployeeTrackerAPI.service.iface.EmployeeService;
import com.employeetracker.EmployeeTrackerAPI.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private  final EmployeeService employeeService;
    private User user;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder, JwtAuthenticationEntryPoint authenticationEntryPoint, EmployeeService employeeService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.employeeService = employeeService;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        //each Authentication provider is tested in order
        //if one passes then its following Authentication providers are skipped

        //DataBase Authentication with  Email Address
        auth.userDetailsService((UserDetailsService) userService).passwordEncoder(passwordEncoder);

        //DataBase Authentication with  Employee code
        //auth.userDetailsService((UserDetailsService) employeeService).passwordEncoder(passwordEncoder);


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/users/login/**","/api/v1/users/signUp/**","/api/v1/users/generate-credentials","/api/v1/users/all","/api/v1/users/edit/**","/api/v1/users/one/**").permitAll()
                .antMatchers("/", "/eureka/**").permitAll()
                .antMatchers( "/api/v1/admin/users/").access("hasRole('ADMIN')")
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin().disable()
                // Filter to handle authentication exceptions and non-controller exceptions
                .addFilterBefore(exceptionHandlerFilter(), UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/assets/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/");
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter(){
        return new ExceptionHandlerFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService((UserDetailsService) userService).passwordEncoder(passwordEncoder);
        //auth.userDetailsService((UserDetailsService) employeeService).passwordEncoder(passwordEncoder);

        //super.configure(auth);
    }



}



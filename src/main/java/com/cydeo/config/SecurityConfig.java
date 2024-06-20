package com.cydeo.config;

import com.cydeo.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    // this code is to define what user we can log in. we get read of spring app generating auto password
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList = new ArrayList<>();
//
//        // this User class is imported form security
//        userList.add( new User("Mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))) );
//        userList.add( new User("Leo", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))) );
//        userList.add( new User("Cristiano", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) );
//
//
//                return new InMemoryUserDetailsManager(userList);
//
//    }


    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    // this code is to chose what UI pages can be accessed by user role
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        return http
                .authorizeRequests()
                // user/** means userController any method can be accessed
//                .antMatchers("/user/**").hasRole("ROLE_Admin")
                    .antMatchers("/user/**").hasAuthority("Admin") // this is better to use
                    .antMatchers("/project/**").hasAuthority("Manager")
                    .antMatchers("/task/employee/**").hasAuthority("Employee")
                //same thing for employee
                    .antMatchers("/task/**").hasAuthority("Manager")
                // more usersRole can access same endpoint
//                .antMatchers("/task/**").hasAnyRole("ROLE_Manager", "ROLE_Employee")

//                .antMatchers("task/**").hasAuthority("Employee")
                // below is the pattern of what can be user accessed
                    .antMatchers( // these are directory
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                        // all usersRoles can have access, permitAll()
                    ).permitAll()
                    .anyRequest().authenticated()
                .and()
                // what kind of login page you want to be shown in UI - basic
//                .httpBasic()
                // this code is to define what login page you want to bring in UI
                    .formLogin()
                    .loginPage("/login") // formal login page login.html general
                // if successfully login where you want to land to welcome page
//                    .defaultSuccessUrl("/welcome")
                // this code will determine what page to land based on roles
                    .successHandler(authSuccessHandler)
                // if anything goes wrong
                    .failureUrl("/login?error=true")
                // who can access this page
                    .permitAll()
                // bridge command - and().build();
                .and()
                // logout code
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                //remember me checkbox
                    .rememberMe()
                    .tokenValiditySeconds(120)
                // this key hold the security session
                    .key("cydeo")
                // this UserDetailService wants to remember what user ? we need to provide user
                    .userDetailsService(securityService)
                .and().build();
    }



}

package com.example.ticketing.config;

import com.example.ticketing.service.CustomUserDetailsService;
import com.example.ticketing.service.LoginService;
import com.example.ticketing.user.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
)
public class SecurityConfig  {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.cors(withDefaults())
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((authorize)->authorize
                        .requestMatchers("/api/register/**","/api/login/**").permitAll()
                        .anyRequest().hasAuthority("ROLE_EXTERNAL"))
                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



//    @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//    @Autowired
//    private TokenConfig tokenConfig;





//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests()
//                .requestMatchers("/api/register/**").permitAll()
//                .requestMatchers("/api/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
////                .addFilter(new JwtAuthenticationFilter(tokenConfig))
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//
//        return httpSecurity.build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository){
//        return new CustomUserDetailsService(userRepository);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception{
//        return http.cors(withDefaults())
//                .csrf().disable()
//                .authorizeHttpRequests(auth->auth.requestMatchers("/api/register/**","/api/login").permitAll()
//                        .anyRequest().hasAuthority("ROLE_EXTERNAL"))
//                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }


}

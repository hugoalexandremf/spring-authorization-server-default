package com.hfernandes.springauthorizationserverdefault.config;

import com.hfernandes.springauthorizationserverdefault.controller.rest.OAuthRegistrationAPIController;
import com.hfernandes.springauthorizationserverdefault.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

     private static final Logger LOG = LoggerFactory.getLogger(SecurityConfiguration.class);

     @Autowired
     ObjectPostProcessor objectPostProcessor;
     @Autowired
     private CustomUserDetailsService customUserDetailsService;

     @Bean
     @Order(2)
     public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
          //insert basic authentication credentials through AuthenticationManagerBuilder
          AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
          authenticationManagerBuilder.inMemoryAuthentication()
                  //.passwordEncoder(new BCryptPasswordEncoder())
                  .withUser("bauser").password("{noop}bauser").roles("ADMIN");

          /*http
  			.securityMatchers((matchers) -> matchers
  				.requestMatchers("/api/**")
  			)
 			.securityMatchers((matchers) -> matchers
 				.requestMatchers("/oauth/**")
  			)
  			.authorizeHttpRequests((authorize) -> authorize
  				.anyRequest().hasRole("USER")
  			)
  			.httpBasic(withDefaults());*/

          httpSecurity
                  .csrf().disable()
                  .securityMatcher("/v1/oauthregistrationapi/health", "/v1/oauthregistrationapi/registerClient", "/v1/oauthregistrationapi/registerUser")
                  .authorizeHttpRequests((authz) -> authz
                          .requestMatchers(HttpMethod.GET, "/v1/oauthregistrationapi/health").permitAll()
                          .requestMatchers(HttpMethod.POST, "/v1/oauthregistrationapi/registerClient").hasRole("ADMIN")
                          .requestMatchers(HttpMethod.POST, "/v1/oauthregistrationapi/registerUser").hasRole("ADMIN")
                  )
                  .httpBasic()
                  .and()
                  .authenticationManager(authenticationManagerBuilder.build());

          return httpSecurity.build();
     }

     //SecurityFilterChain for /secureapi - OAuth2
     /*@Bean
     @Order(4)
     public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
          AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
          authenticationManagerBuilder.userDetailsService(customUserDetailsService);

          httpSecurity
                  //.csrf().disable()
                  //.securityMatcher("/login**", "/v1/secureapi/**")
                  .authorizeHttpRequests((authz) -> authz
                          //.requestMatchers("/login**").permitAll()
                          .anyRequest().authenticated()
                  )
                  // Form login handles the redirect to the login page from the authorization server filter chain
                  //.formLogin(Customizer.withDefaults())
                  .formLogin()
                  .loginPage("/login")
                  .loginProcessingUrl("/processing_login")
                  .and()
                  //.userDetailsService(customUserDetailsService);
                  .authenticationManager(authenticationManagerBuilder.build());

          return httpSecurity.build();
     }*/

     @Bean
     @Order(4)
     public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
          AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
          authenticationManagerBuilder.userDetailsService(customUserDetailsService);

          http
                  .authorizeHttpRequests((authorize) -> authorize
                          .anyRequest().authenticated()
                  )
                  // Form login handles the redirect to the login page from the
                  // authorization server filter chain
                  .formLogin(Customizer.withDefaults())
                  .authenticationManager(authenticationManagerBuilder.build());

          return http.build();
     }

     /*@Bean
     @Order(5)
     public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
             throws Exception {
          http
                  .authorizeHttpRequests((authorize) -> authorize
                          .anyRequest().authenticated()
                  )
                  // Form login handles the redirect to the login page from the
                  // authorization server filter chain
                  .formLogin(Customizer.withDefaults());

          return http.build();
     }*/

}

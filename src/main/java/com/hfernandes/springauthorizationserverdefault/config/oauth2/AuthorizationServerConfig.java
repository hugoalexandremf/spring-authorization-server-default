package com.hfernandes.springauthorizationserverdefault.config.oauth2;

import com.hfernandes.springauthorizationserverdefault.config.oauth2.oicd.CustomOidcUserInfoAuthenticationProvider;
import com.hfernandes.springauthorizationserverdefault.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.web.authentication.OidcClientRegistrationAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class AuthorizationServerConfig {

     private static final Logger LOG = LoggerFactory.getLogger(AuthorizationServerConfig.class);

     @Autowired
     private CustomOpaqueTokenIntrospector customOpaqueTokenInstropector;
     @Autowired
     private ObjectPostProcessor objectPostProcessor;
     @Autowired
     private CustomUserDetailsService customUserDetailsService;
     @Autowired
     private JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService;


     @Bean
     @Order(Ordered.HIGHEST_PRECEDENCE)
     public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
          OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
          httpSecurity.apply(authorizationServerConfigurer);

          DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
          daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

          //OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);

          //httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

          //customize token endpoint in order to receive additional parameters such as deviceID, oldClientID and oldClientSecret
          authorizationServerConfigurer
                  //.oidc(Customizer.withDefaults())
                  .oidc(oidc -> oidc
                          .userInfoEndpoint(userInfoEndpoint ->
                                  userInfoEndpoint
                                          //.userInfoRequestConverter(new CustomOidcClientRegistrationAuthenticationConverter())
                                          .authenticationProvider(new CustomOidcUserInfoAuthenticationProvider(jpaOAuth2AuthorizationService))
                          )
                  )
                  .authorizationServerMetadataEndpoint(Customizer.withDefaults())
                  .authorizationEndpoint((authorizationEndpoint) -> {
                       authorizationEndpoint.authenticationProvider(daoAuthenticationProvider);
                  });
                  //customize token endpoint naming
                  /*.authorizationServerSettings(AuthorizationServerSettings.builder()
                          .tokenEndpoint("/oauth/token")
                          .tokenIntrospectionEndpoint("/oauth/introspect")
                          .build());*/

          RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

          /*httpSecurity
                  .csrf().disable()
                  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and()
                  .authorizeHttpRequests()
                  .requestMatchers(endpointsMatcher).authenticated()
                  .requestMatchers("/v1/secureapi/**").hasAuthority("secureapi")
                  .and()
                  .authorizeHttpRequests()
                  .requestMatchers(HttpMethod.GET, "/v1/mappapi/health").permitAll()
                  .anyRequest().authenticated()
                  .and()
                  .oauth2ResourceServer((oAuth2ResourceServerConfigurer -> {
                       oAuth2ResourceServerConfigurer
                               .opaqueToken((opaqueTokenConfigurer -> {
                                    opaqueTokenConfigurer
                                            .introspector(customOpaqueTokenInstropector);
                               }));
                       //.opaqueToken();
                  }));
                  //.addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                  //.addFilterBefore(filterChainExceptionHandler, ExceptionTranslationFilter.class);
                  //.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);*/

          AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
          authenticationManagerBuilder.userDetailsService(customUserDetailsService);

          httpSecurity
                  .csrf().disable()
                  .securityMatchers((matchers) -> matchers
                          .requestMatchers(endpointsMatcher)
                  )
                  .securityMatchers((matchers) -> matchers
                          .requestMatchers("/secureapi/v1/**")
                  )
                  /*.securityMatcher(endpointsMatcher)
                  .securityMatcher("/v1/secureapi/**")*/
                  .authorizeHttpRequests((authz) -> authz
                          .requestMatchers(HttpMethod.POST, "/secureapi/v1/**").hasAuthority("secureapi")
                          .anyRequest().authenticated()
                  )
                  .oauth2ResourceServer((oAuth2ResourceServerConfigurer -> {
                       oAuth2ResourceServerConfigurer
                               .opaqueToken((opaqueTokenConfigurer -> {
                                    opaqueTokenConfigurer
                                            .introspector(customOpaqueTokenInstropector);
                               }));
                       //.opaqueToken();
                  }))
                  // Redirect to the login page when not authenticated from the authorization endpoint
                  .exceptionHandling((exceptions) -> exceptions
                          .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                  );
                  /*.authenticationManager(authenticationManagerBuilder.build())
                  .authenticationProvider(daoAuthenticationProvider);*/

          return httpSecurity.build();
     }

     @Bean
     public AuthorizationServerSettings authorizationServerSettings() {
          return AuthorizationServerSettings.builder().build();
     }

}
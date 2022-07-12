package com.mmelo.financial.config;

import java.util.Arrays;
import java.util.List;

import com.mmelo.financial.security.AuthenticationService;
import com.mmelo.financial.security.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final private AuthenticationService authenticationService;
    final private TokenAuthenticationFilter filter;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, AUTH_ENDPOINT).permitAll()
//                .antMatchers(HttpMethod.POST, CUSTOMER_ENDPOINT).permitAll()
//                .antMatchers(HttpMethod.POST, MATCH_TODAY_ENDPOINT).permitAll()
//                .antMatchers(HttpMethod.GET, RULE_ENDPOINT).permitAll()
//                .antMatchers(HttpMethod.GET, DASH_ENDPOINT).hasAnyRole("DASHBOARD", "ADM")
//                .antMatchers(HttpMethod.DELETE, ADMIN_ENDPOINT).hasRole("ADM")
//                .antMatchers(HttpMethod.GET, ALERT_ENDPOINT).hasRole("ADM")
//                .antMatchers(HttpMethod.GET, ENTRY_ENDPOINT).hasRole("ADM")
//                .antMatchers(HttpMethod.POST, ENTRY_ENDPOINT).hasRole("ADM")
//                .antMatchers(HttpMethod.PUT, ENTRY_ENDPOINT).hasRole("ADM")
//                .antMatchers(HttpMethod.GET, BET_ENDPOINT).hasAnyRole("DASHBOARD", "ADM")
//                .antMatchers(HttpMethod.POST, BET_ENDPOINT).hasAnyRole("DASHBOARD", "ADM")
//                .antMatchers(HttpMethod.PUT, BET_ENDPOINT).hasAnyRole("DASHBOARD", "ADM")
//                .antMatchers(HttpMethod.POST, NOTIFICATION_ENDPOINT).hasRole("ADM")
                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //Filter configuration
                .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        //Configurations for authentication
        auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) {
        //Configuration for static resources
//        web.ignoring().antMatchers(CUSTOMER_ENDPOINT);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public HttpFirewall getHttpFirewall() {
        StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
        strictHttpFirewall.setAllowSemicolon(true);
        strictHttpFirewall.setAllowBackSlash(true);
        strictHttpFirewall.setAllowedHttpMethods(Arrays.asList("GET","POST", "PUT","DELETE", "OPTIONS"));
        return strictHttpFirewall;
    }
}
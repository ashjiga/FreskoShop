package com.fresko.config;

import com.fresko.service.impl.DetallesUsuarioImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class ConfiguracionSeguridad {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(DetallesUsuarioImpl detalles) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(detalles);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filtro(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authProvider(null)) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/trabajador/**").hasAnyAuthority("TRABAJADOR", "ADMIN")
                .requestMatchers("/cliente/**").hasAnyAuthority("CLIENTE", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(f -> f
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(l -> l.logoutSuccessUrl("/auth/login?logout").permitAll())
            .csrf(c -> c.disable());
        return http.build();
    }
}
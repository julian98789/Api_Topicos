package com.example.demo.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indica que esta clase es una fuente de definiciones de beans para el contexto de la aplicación
@EnableWebSecurity // Habilita la seguridad web para esta aplicación
public class ConfiguracionSeguridad {

    @Autowired
    private FiltroSeguridad filtroSeguridad;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, FiltroSeguridad securityFilter) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de creación de sesiones como STATELESS
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(filtroSeguridad, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Obtiene y devuelve el AuthenticationManager desde la configuración de autenticación
    }

    // Define un bean para BCryptPasswordEncoder, que se utiliza para cifrar contraseñas
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // Crea y devuelve una instancia de BCryptPasswordEncoder
    }
}

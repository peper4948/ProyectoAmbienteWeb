package com.BLCMWEB;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/chat")
                )
                .authorizeHttpRequests(auth -> auth
                    // 1. Recursos estáticos y páginas públicas libres para todos
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/api/chat",
                            "/fonts/**", "/webjars/**", "/logo/**",
                            "/", "/inicio/listado", "/calendario/listado",
                            "/galeria/listado", "/contacto/listado",
                            "/audiciones/listado", "/login"
                    ).permitAll()
                    
                    // 2. Rutas exclusivas para ADMINISTRADORES y DIRECTORES (ej. gestión de usuarios y listados avanzados)
                    .requestMatchers("/usuario/listado", "/usuario/guardar/**", "/secciones/listadoDirector/**").hasAnyAuthority("ADMIN", "DIRECTOR")
                    
                    // 3. Rutas exclusivas para INTEGRANTES (ej. su panel de integrante y cambio de contraseña)
                    .requestMatchers("/integrante/**", "/usuario/cambiarPassword").hasAuthority("INTEGRANTE")
                    
                    // 4. Todo lo demás requiere que el usuario haya iniciado sesión
                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .permitAll()
                        .defaultSuccessUrl("/inicio/listado", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/inicio/listado")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/acceso_denegado"))
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }
}
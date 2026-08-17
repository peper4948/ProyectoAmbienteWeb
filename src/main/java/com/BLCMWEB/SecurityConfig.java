package com.BLCMWEB;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .ignoringRequestMatchers("/api/chat", "/audiciones/guardar")
                )
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/api/chat",
                        "/fonts/**", "/webjars/**", "/logo/**", "/uploads/**",
                        "/", "/inicio/listado", "/secciones/Calendariolistado",
                        "/galeria/listado", "/contacto/listado",
                        "/audiciones/listado", "/audiciones/guardar", "/login", "/acceso_denegado"
                ).permitAll()
                .requestMatchers("/secciones/nuevo", "/secciones/editar/**", "/secciones/guardar",
                        "/secciones/eliminar", "/secciones/activar")
                .hasRole("ADMIN")
                .requestMatchers("/galeria/nuevo", "/galeria/editar/**", "/galeria/guardar",
                        "/galeria/eliminar", "/galeria/activar")
                .hasAnyRole("ADMIN", "DIRECTOR")
                .requestMatchers("/usuario/listado", "/usuario/guardar/**", "/secciones/listadoDirector/**")
                .hasAnyRole("ADMIN", "DIRECTOR")
                .requestMatchers("/lider/asistencia/**", "/lider/anuncio/**")
                .hasAnyRole("LIDER", "ADMIN", "DIRECTOR")
                .requestMatchers("/lider/**")
                .hasAnyRole("ADMIN", "DIRECTOR")
                .requestMatchers("/integrante/**", "/usuario/cambiarPassword").hasRole("INTEGRANTE")
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

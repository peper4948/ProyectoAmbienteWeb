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
                // CSRF: ignorar el endpoint de guardado de audición si tu frontend hace POST sin token
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/chat", "/audiciones/guardar")
                )
                .authorizeHttpRequests(auth -> auth
                // Recursos estáticos y favicon
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/css/**", "/js/**", "/img/**", "/fonts/**", "/webjars/**", "/logo/**").permitAll()
                // Páginas públicas
                .requestMatchers("/", "/inicio/listado", "/calendario/listado", "/galeria/listado", "/contacto/listado", "/login").permitAll()
                // Permitir explícitamente GET para audiciones (página pública)
                .requestMatchers(HttpMethod.GET, "/audiciones/**").permitAll()
                // Permitir POST para guardar audición (si tu frontend lo usa)
                .requestMatchers(HttpMethod.POST, "/audiciones/guardar").permitAll()
                // Permitir OPTIONS (preflight CORS) para evitar bloqueos en fetch
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Otros endpoints públicos que uses
                .requestMatchers("/api/chat").permitAll()
                // Rutas con roles
                .requestMatchers("/usuario/listado", "/usuario/guardar/**", "/secciones/listadoDirector/**")
                .hasAnyAuthority("ADMIN", "DIRECTOR")
                .requestMatchers("/integrante/**", "/usuario/cambiarPassword").hasAuthority("INTEGRANTE")
                // El resto requiere autenticación
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

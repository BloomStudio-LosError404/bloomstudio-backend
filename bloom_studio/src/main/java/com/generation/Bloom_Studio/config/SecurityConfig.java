package com.generation.Bloom_Studio.config;

import com.generation.Bloom_Studio.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService; // Usará nuestro CustomUserDetailsService

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. LOGIN
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. REGISTRO DE USUARIOS
                        .requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll()

                        // 3. PÚBLICO: Ver productos (Cualquiera puede ver el catálogo)
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll()

                        // 4. SOLO ADMIN: Crear, Editar, Borrar Usuarios o Productos
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasRole("ADMIN")

                        // 4. EL RESTO: Requiere estar autenticado (Cliente o Admin)
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No usamos cookies/sesiones
                .authenticationProvider(authenticationProvider()) // Usamos nuestro proveedor
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Ponemos nuestro filtro

        return http.build();
    }*/

    //luego vemos con mas calma
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite cualquier origen (Frontend)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Verbos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Headers permitidos (Content-Type, Authorization, etc)
        configuration.setAllowCredentials(false); // Ponlo en true si necesitas cookies, pero con JWT suele ir false o manejarse distinto

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a TODAS las rutas
        return source;
    }
}

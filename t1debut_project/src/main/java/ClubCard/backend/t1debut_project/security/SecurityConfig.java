package ClubCard.backend.t1debut_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/profile.html").hasAnyRole("ADMIN", "USER", "SUPER-ADMIN")
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/admin.html").hasAnyRole("ADMIN", "SUPER-ADMIN")
                        .requestMatchers("/create-admin").permitAll()
                        .requestMatchers("/logout").hasAnyRole("ADMIN", "USER", "SUPER-ADMIN")
                        .requestMatchers("/api/profile/generate-qr").hasAnyRole("ADMIN", "USER", "SUPER-ADMIN")
                        .requestMatchers("/generate-qr").hasAnyRole("ADMIN", "USER", "SUPER-ADMIN")
                        .requestMatchers("/register").hasAnyRole("ADMIN", "SUPER-ADMIN")
                        .requestMatchers("/members/**").hasAnyRole("ADMIN", "SUPER-ADMIN")
                        .requestMatchers("/profile/**").hasAnyRole("ADMIN", "USER", "SUPER-ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


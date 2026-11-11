package br.edu.insper.avaliacoes;

import org.springframework.context.annotation.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
import java.util.*;
import java.util.stream.Stream;

@Configuration @EnableMethodSecurity
public class SecurityConfig {
    @org.springframework.beans.factory.annotation.Value("${CORS_ALLOWED_ORIGIN:http://localhost:5173}")
    private String allowedOrigin;
    @Bean SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(cors -> {}).authorizeHttpRequests(a -> a.requestMatchers("/actuator/health").permitAll().anyRequest().authenticated()).oauth2ResourceServer(o -> o.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }
    @Bean CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(allowedOrigin));
        config.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); return source;
    }
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities()); return converter;
    }
    private Converter<Jwt, Collection<GrantedAuthority>> authorities() {
        return jwt -> Stream.concat(claimValues(jwt, "https://avaliacoes.insper.br/roles").stream(), claimValues(jwt, "permissions").stream())
            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();
    }
    private List<String> claimValues(Jwt jwt, String name) {
        Object value = jwt.getClaim(name);
        if (value instanceof Collection<?> c) return c.stream().map(Object::toString).toList();
        if (value instanceof String s) return Arrays.stream(s.split("\\s+")).filter(v -> !v.isBlank()).toList();
        return List.of();
    }
}

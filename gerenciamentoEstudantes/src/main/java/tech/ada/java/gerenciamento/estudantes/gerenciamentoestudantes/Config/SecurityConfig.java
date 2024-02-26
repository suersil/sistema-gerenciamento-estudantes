package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                //Regras de autorizações para requisições HTTP:
                .authorizeHttpRequests(auth -> {

                            //Permite todas as requisições GET para o endpoint "/aluno" sem autenticação.
                            auth.requestMatchers(HttpMethod.GET, "/aluno").permitAll();
                            //Requer autenticação de todos os outros
                            auth.anyRequest().authenticated();
                        }
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}


package me.birch.businessserver.configs;

import lombok.RequiredArgsConstructor;
import me.birch.businessserver.beans.InitialAuthenticationFilter;
import me.birch.businessserver.beans.JwtAuthenticationFilter;
import me.birch.businessserver.beans.OtpAuthenticationProvider;
import me.birch.businessserver.beans.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity,
                                         InitialAuthenticationFilter initialAuthenticationFilter,
                                         JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class)
            .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
            .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider,
                                                       OtpAuthenticationProvider otpAuthenticationProvider) {
        return new ProviderManager(usernamePasswordAuthenticationProvider, otpAuthenticationProvider);
    }
}

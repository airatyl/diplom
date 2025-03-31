package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WevSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("operator").password("1234").roles("operator").build());
        return manager;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/home2").hasRole("operator")
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/home2")
                )
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

}

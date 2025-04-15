package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("Ivan").password("1234").roles("operator").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("Artem").password("1234").roles("operator").build());
        return manager;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers("/home/main").hasRole("operator")
                                .requestMatchers("/home/change").hasRole("operator")
                                .requestMatchers("/home/history").hasRole("operator")
                                .requestMatchers("/home/graphics").hasRole("operator")
                                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/home/login")
                        .permitAll()
                        .defaultSuccessUrl("/home/main",true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());


        return httpSecurity.build();
    }
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers(new AntPathRequestMatcher("/**"));
//    }

}

package stephenaamuah.prmnt_application.Config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import stephenaamuah.prmnt_application.model.Item;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.Role;
import stephenaamuah.prmnt_application.repository.AccessLogRepository;
import stephenaamuah.prmnt_application.repository.ItemRepository;
import stephenaamuah.prmnt_application.repository.UserRepository;
import stephenaamuah.prmnt_application.service.UserDetailsService;

import java.time.LocalDateTime;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    AccessLogRepository accessLogRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/procureapp/add-user", "/procureapp/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/procureapp/login").defaultSuccessUrl("/procureapp/afterLogin"))
                .logout(logout -> logout
                        .logoutUrl("/procureapp/logout")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler(accessLogRepository))
                        .clearAuthentication(true))
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }





    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailService() {
        return new UserDetailsService();
    }

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if(userRepository.findAppUserByEmail("ternor@gmail.com").isEmpty()){
                User adminAlfred = new User();
                adminAlfred.setFirstName("Alfred");
                adminAlfred.setSurname("Ternor");
                adminAlfred.setEmail("alfred@gmail.com");
                adminAlfred.setPassword(passwordEncoder().encode("super"));
                adminAlfred.setRoles(String.valueOf(Role.SUPER));
                adminAlfred.setCreated(LocalDateTime.now());
//                userRepository.save(adminAlfred);
                log.info("Super admin: {} has been added", adminAlfred.getFirstName());

            }
        };
    }
}

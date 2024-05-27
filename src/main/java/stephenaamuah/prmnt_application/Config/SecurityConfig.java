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
import stephenaamuah.prmnt_application.repository.ItemRepository;
import stephenaamuah.prmnt_application.repository.UserRepository;
import stephenaamuah.prmnt_application.service.UserDetailsService;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/procureapp/signup", "/procureapp/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form.loginPage("/procureapp/login").defaultSuccessUrl("/procureapp/success")
                ).logout((logout)->logout.logoutUrl("/procureapp/logout").clearAuthentication(true))
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
            if (userRepository.findAppUserByEmail("admin@gmail.com").isEmpty() || userRepository.findAppUserByEmail("user@gmail.com").isEmpty() || userRepository.findAppUserByEmail("superadmin@gmail.com").isEmpty() ) {
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder().encode("admin"));
                admin.setRoles(String.valueOf(Role.ADMIN));
                userRepository.save(admin);
                log.info("Admin user registered:::::::{}", admin);

                Item item1 = new Item();
                item1.setName("Washing Machine");
                item1.setDescription("This is the used for washing");
                item1.setQuantity(56);
                itemRepository.save(item1);

                Item item2 = new Item();
                item2.setName("Car");
                item2.setDescription("This is a car");
                item2.setQuantity(100);
                itemRepository.save(item2);

                Item item3 = new Item();
                item3.setName("Electric Stove");
                item3.setDescription("This is an electric stove");
                item3.setQuantity(200);
                itemRepository.save(item3);



                User user = new User();
                user.setEmail("user@gmail.com");
                user.setPassword(passwordEncoder().encode("user"));
                user.setRoles(String.valueOf(Role.USER));
                userRepository.save(user);
                log.info("User registered:::::::::::::{}", user);

                User superAdmin = new User();
                superAdmin.setEmail("superadmin@gmail.com");
                superAdmin.setPassword(passwordEncoder().encode("super"));
                superAdmin.setRoles(String.valueOf(Role.SUPER_ADMIN));
                userRepository.save(superAdmin);
                log.info("Super Admin registered:::::::::::::{}", superAdmin);

            } else {
                log.info("Admin user already registered:::::::");
            }
        };
    }


}

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
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/procureapp/add-user", "/procureapp/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/procureapp/login").defaultSuccessUrl("/procureapp/afterlogin"))
                .logout(logout -> logout.logoutUrl("/procureapp/logout").clearAuthentication(true))
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
            if (userRepository.findAppUserByEmail("ternor@gmail.com").isEmpty()) {
                User adminAlfred = new User();
                adminAlfred.setFirstName("Alfred");
                adminAlfred.setSurname("Ternor");
                adminAlfred.setEmail("ternor@gmail.com");
                adminAlfred.setPassword(passwordEncoder().encode("super"));
                adminAlfred.setRoles(String.valueOf(Role.SUPER));
                userRepository.save(adminAlfred);
                log.info("Admin {} has been added", adminAlfred.getFirstName());
            }



            Item item1 = new Item();
            item1.setName("Laptop");
            item1.setDescription("This is a laptop");
            item1.setQuantity(56);
            itemRepository.save(item1);

            Item item2 = new Item();
            item2.setName("Car");
            item2.setDescription("This is a car");
            item2.setQuantity(100);
            itemRepository.save(item2);

            Item item3 = new Item();
            item3.setName("Macbook");
            item3.setDescription("This is a macbook");
            item3.setQuantity(200);
            itemRepository.save(item3);

            Item item4 = new Item();
            item4.setName("Machine");
            item4.setDescription("This is a machine");
            item4.setQuantity(56);
            itemRepository.save(item4);

            Item item5 = new Item();
            item5.setName("Phone");
            item5.setDescription("This is a phone");
            item5.setQuantity(150);
            itemRepository.save(item5);

            Item item6 = new Item();
            item6.setName("Tablet");
            item6.setDescription("This is a tablet");
            item6.setQuantity(75);
            itemRepository.save(item6);

            Item item7 = new Item();
            item7.setName("Headphones");
            item7.setDescription("These are headphones");
            item7.setQuantity(300);
            itemRepository.save(item7);

            Item item8 = new Item();
            item8.setName("Printer");
            item8.setDescription("This is a printer");
            item8.setQuantity(50);
            itemRepository.save(item8);




        };
    }
}

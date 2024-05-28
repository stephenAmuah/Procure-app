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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
                        .requestMatchers("/procureapp/signup", "/procureapp/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/procureapp/login").defaultSuccessUrl("/procureapp/success"))
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
            if (userRepository.findAppUserByEmail("admin@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder().encode("admin"));
                admin.setRoles(String.valueOf(Role.ADMIN));
                userRepository.save(admin);
                log.info("Admin user registered: {}", admin);
            }

            if (userRepository.findAppUserByEmail("user@gmail.com").isEmpty()) {
                User user = new User();
                user.setEmail("user@gmail.com");
                user.setPassword(passwordEncoder().encode("user"));
                user.setRoles(String.valueOf(Role.USER));
                userRepository.save(user);
                log.info("User registered: {}", user);
            }

            if (userRepository.findAppUserByEmail("superadmin@gmail.com").isEmpty()) {
                User superAdmin = new User();
                superAdmin.setEmail("superadmin@gmail.com");
                superAdmin.setPassword(passwordEncoder().encode("super"));
                superAdmin.setRoles(String.valueOf(Role.SUPER_ADMIN));
                userRepository.save(superAdmin);
                log.info("Super Admin registered: {}", superAdmin);
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

            Item item9 = new Item();
            item9.setName("Monitor");
            item9.setDescription("This is a monitor");
            item9.setQuantity(120);
            itemRepository.save(item9);

            Item item10 = new Item();
            item10.setName("Keyboard");
            item10.setDescription("This is a keyboard");
            item10.setQuantity(200);
            itemRepository.save(item10);

            Item item11 = new Item();
            item11.setName("Mouse");
            item11.setDescription("This is a mouse");
            item11.setQuantity(180);
            itemRepository.save(item11);

            Item item12 = new Item();
            item12.setName("Speaker");
            item12.setDescription("This is a speaker");
            item12.setQuantity(95);
            itemRepository.save(item12);

            Item item13 = new Item();
            item13.setName("Webcam");
            item13.setDescription("This is a webcam");
            item13.setQuantity(60);
            itemRepository.save(item13);

            Item item14 = new Item();
            item14.setName("Router");
            item14.setDescription("This is a router");
            item14.setQuantity(85);
            itemRepository.save(item14);

            Item item15 = new Item();
            item15.setName("Smartwatch");
            item15.setDescription("This is a smartwatch");
            item15.setQuantity(110);
            itemRepository.save(item15);

        };
    }
}

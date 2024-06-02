package stephenaamuah.prmnt_application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.Role;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.repository.UserCreationLogRepository;
import stephenaamuah.prmnt_application.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Slf4j
public class UserService{

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCreationLogRepository userCreationLogRepository;

    public String addUser(User user, Authentication authentication) {
        try {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                user.setFirstName(user.getFirstName());
                user.setSurname(user.getSurname());
                user.setEmail(user.getEmail());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(user.getRoles());
                log.info("User added {}", user);
                userRepository.save(user);

                UserDetails userDetails = (UserDetails) principal;
                userCreationLogRepository.insertUserCreationLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getEmail(),userDetails.getRoles().get(0).getAuthority(),user.getFirstName(),user.getSurname(),user.getEmail(),user.getRoles(), LocalDateTime.now());
            }

            return "redirect:/procureapp/dashboard?success";
        } catch (Exception e) {
            log.error("An exception occurred in addUser: ",e);
            return "redirect:/procureapp/dashboard?error";
        }
    }




}

package stephenaamuah.prmnt_application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.Role;
import stephenaamuah.prmnt_application.repository.UserRepository;

@Service
@Slf4j
public class UserService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        user.setFirstName(user.getFirstName());
        user.setSurname(user.getSurname());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(String.valueOf(Role.USER));
        log.info("User added {}", user);
        userRepository.save(user);
        return user;
    }




}

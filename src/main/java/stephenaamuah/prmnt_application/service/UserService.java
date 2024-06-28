package stephenaamuah.prmnt_application.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.repository.UserCreationLogRepository;
import stephenaamuah.prmnt_application.repository.UserDeletionLogRepository;
import stephenaamuah.prmnt_application.repository.UserRepository;
import stephenaamuah.prmnt_application.repository.UserUpdateLogRepository;
import stephenaamuah.prmnt_application.utils.JsonUtility;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService{

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCreationLogRepository userCreationLogRepository;

    @Autowired
    UserUpdateLogRepository userUpdateLogRepository;

    @Autowired
    UserDeletionLogRepository userDeletionLogRepository;

    public String addUser(User user, Authentication authentication) {
        try {
            Object principal = authentication.getPrincipal();
            UserDetails userDetails = (UserDetails) principal;
            if (principal instanceof UserDetails) {
                user.setFirstName(user.getFirstName());
                user.setSurname(user.getSurname());
                user.setEmail(user.getEmail());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setRoles(user.getRoles());
                user.setAddedBy(userDetails.getFirstName().concat(" ").concat(userDetails.getSurname()));
                log.info("User added {}", user);
                userRepository.save(user);


                userCreationLogRepository.insertUserCreationLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getEmail(),userDetails.getRoles().get(0).getAuthority(),user.getFirstName(),user.getSurname(),user.getEmail(),user.getRoles(), LocalDateTime.now());
            }

            return "redirect:/procureapp/users?success";
        } catch (Exception e) {
            log.error("An exception occurred in addUser: ",e);
            return "redirect:/procureapp/users?error";
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }


    public String updateUser(User existingUser, User user, Authentication authentication) {
        String old_record = JsonUtility.toJson(existingUser);

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            existingUser.setFirstName(user.getFirstName());
            existingUser.setSurname(user.getSurname());
            existingUser.setEmail(user.getEmail());
            existingUser.setUpdatedBy(userDetails.getFirstName().concat(" ").concat(userDetails.getSurname()));
            if(Objects.nonNull(user.getRoles())){
                existingUser.setRoles(user.getRoles());
            }
            User savedUser = userRepository.save(existingUser);

            String new_record = JsonUtility.toJson(savedUser);
            userUpdateLogRepository.insertUpdateLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getUsername(), userDetails.getRoles().get(0).getAuthority(),old_record,new_record, new Date());

        }
        return "redirect:/procureapp/users";
    }

    public void deleteUser(int id, Authentication authentication) {
        User userToDelete = getUserById(id);
        log.info("userToDelete: {}", userToDelete);
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            userRepository.deleteById(id);

            String deleted_record = JsonUtility.toJson(userToDelete);
            userDeletionLogRepository.deleteUserLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getUsername(), userDetails.getRoles().get(0).getAuthority(),deleted_record, new Date());
        }

    }
}

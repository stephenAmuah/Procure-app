package stephenaamuah.prmnt_application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.model.User;
import stephenaamuah.prmnt_application.repository.UserRepository;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findAppUserByEmail(username);
        return user.map(UserDetails::new).orElseThrow(()->new UsernameNotFoundException("User does not exist"));
    }
}

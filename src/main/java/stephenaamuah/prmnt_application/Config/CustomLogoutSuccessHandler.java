package stephenaamuah.prmnt_application.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import stephenaamuah.prmnt_application.model.UserDetails;
import stephenaamuah.prmnt_application.repository.AccessLogRepository;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final AccessLogRepository accessLogRepository;

    public CustomLogoutSuccessHandler(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            accessLogRepository.insertAccessLog(userDetails.getFirstName(), userDetails.getSurname(), userDetails.getUsername(), userDetails.getRoles().get(0).getAuthority().toString(),"Logout", LocalDateTime.now());
        }

        response.sendRedirect("/procureapp/login");
    }
}

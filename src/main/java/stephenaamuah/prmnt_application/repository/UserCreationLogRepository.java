package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import stephenaamuah.prmnt_application.model.UserCreationLogs;

import java.time.LocalDateTime;


public interface UserCreationLogRepository extends JpaRepository<UserCreationLogs, Long> {

    @Procedure("USER_CREATION_LOG_INSERT")
    void insertUserCreationLog(String creator_first_name, String creator_surname, String creator_email, String creator_role, String new_user_first_name, String new_user_surname, String new_user_email, String new_user_role, LocalDateTime created);
}

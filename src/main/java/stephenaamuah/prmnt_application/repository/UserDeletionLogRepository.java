package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import stephenaamuah.prmnt_application.model.UserDeletionLogs;

import java.util.Date;

public interface UserDeletionLogRepository extends JpaRepository<UserDeletionLogs, Long> {
    @Procedure("USER_DELETION_LOG_INSERT")
    public void deleteUserLog(String first_name, String surname, String email, String role, String record, Date created);
}

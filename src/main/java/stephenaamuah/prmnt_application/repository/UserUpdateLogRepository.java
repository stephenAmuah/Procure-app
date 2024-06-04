package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import stephenaamuah.prmnt_application.model.UserUpdateLogs;

import java.util.Date;

public interface UserUpdateLogRepository extends JpaRepository<UserUpdateLogs, Long> {
    @Procedure("USER_UPDATE_LOG_INSERT")
    public void insertUpdateLog(String first_name, String surname, String email, String role, String old_record, String new_record, Date created);
}

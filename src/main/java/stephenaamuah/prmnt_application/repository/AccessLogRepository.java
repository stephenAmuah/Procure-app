package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import stephenaamuah.prmnt_application.model.AccessLogs;

import java.time.LocalDateTime;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogs, Long> {

    @Procedure("ACCESS_LOG_INSERT")
    public void insertAccessLog(String first_name, String surname, String email, String roles, String action, LocalDateTime created);
}

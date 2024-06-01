package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import stephenaamuah.prmnt_application.model.ItemDeletionLogs;

import java.util.Date;

public interface ItemDeletionLogRepository extends JpaRepository<ItemDeletionLogs, Long> {
    @Procedure("DELETION_LOG_INSERT")
    public void deleteItemLog(String first_name, String surname, String email, String role, String record, Date created);
}

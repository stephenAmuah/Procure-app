package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import stephenaamuah.prmnt_application.model.ItemCreationLogs;

import java.util.Date;

public interface ItemCreationLogRepository extends JpaRepository<ItemCreationLogs, Long> {
    @Procedure("CREATION_LOG_INSERT")
    public void insertItemLog(String first_name, String surname, String email, String role, String record, Date created);
}

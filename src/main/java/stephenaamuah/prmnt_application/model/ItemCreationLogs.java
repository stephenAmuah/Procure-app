package stephenaamuah.prmnt_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class ItemCreationLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CreatedByFirstName;
    private String CreatedBySurname;
    private String CreatedByRole;
    private String CreatedByEmail;
    private String record;
    private Date created;
}

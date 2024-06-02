package stephenaamuah.prmnt_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ItemDeletionLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String DeletedByFirstName;
    private String DeletedBySurname;
    private String DeletedByRole;
    private String DeletedByEmail;
    private String deletedRecord;
    private Date created;
}

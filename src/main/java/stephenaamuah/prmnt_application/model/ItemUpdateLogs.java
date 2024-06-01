package stephenaamuah.prmnt_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ItemUpdateLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String UpdatedByFirstName;
    private String UpdatedBySurname;
    private String UpdatedByRole;
    private String UpdatedByEmail;
    private String oldRecord;
    private String newRecord;
    private Date created;
}

package stephenaamuah.prmnt_application.model;


import jakarta.persistence.*;
import lombok.Data;
import java.sql.Blob;

@Data
@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int quantity;


    @Lob
    private Blob photo;

}

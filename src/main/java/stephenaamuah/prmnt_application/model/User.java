package stephenaamuah.prmnt_application.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(unique = true)
    private String email;
    private String password;
    private String roles;


}

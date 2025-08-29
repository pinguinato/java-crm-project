package it.gianotto.crm_project.contact.data.entity;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

}

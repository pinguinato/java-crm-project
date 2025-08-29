package it.gianotto.crm_project.contact.data.entity;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

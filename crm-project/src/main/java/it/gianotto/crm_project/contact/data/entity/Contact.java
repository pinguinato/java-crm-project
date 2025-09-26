package it.gianotto.crm_project.contact.data.entity;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "email") // Usa un "business key" per l'uguaglianza
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String firstName;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Il formato dell'email non è valido")
    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String companyName;

    @NotNull(message = "Lo stato non può essere nullo")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactStatus status;
}

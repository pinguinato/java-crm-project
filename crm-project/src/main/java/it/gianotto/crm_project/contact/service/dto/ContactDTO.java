package it.gianotto.crm_project.contact.service.dto;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String firstName;

    @NotBlank(message = "Il cognome non può essere vuoto")
    private String lastName;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Il formato dell'email non è valido")
    private String email;

    private String phone;

    private String companyName;

    @NotNull(message = "Lo stato non può essere nullo")
    private ContactStatus status;
}

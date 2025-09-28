package it.gianotto.crm_project.contact.service.dto;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
    private String firstName;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
    private String lastName;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Il formato dell'email non è valido")
    @Size(max = 100, message = "L'email non può superare i 100 caratteri")
    private String email;

    @Pattern(regexp = "^[+]?[0-9\\s\\-()]{10,20}$", message = "Formato telefono non valido")
    private String phone;

    @Size(max = 100, message = "Il nome dell'azienda non può superare i 100 caratteri")
    private String companyName;

    @NotNull(message = "Lo stato non può essere nullo")
    private ContactStatus status;
}


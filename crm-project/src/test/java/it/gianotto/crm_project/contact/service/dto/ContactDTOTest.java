package it.gianotto.crm_project.contact.service.dto;

import it.gianotto.crm_project.contact.ContactStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

class ContactDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidContactDTO_thenNoViolations() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .phone("1234567890")
                .companyName("Acme Corp")
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenFirstNameIsBlank_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("") // Blank
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2); // Ora aspetta 2 violazioni
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Il nome non può essere vuoto",
                        "Il nome deve essere tra 2 e 50 caratteri"
                );
    }

    @Test
    void whenLastNameIsBlank_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName(" ") // Blank
                .email("mario.rossi@example.com")
                .status(ContactStatus.CUSTOMER)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2); // Ora aspetta 2 violazioni
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "Il cognome non può essere vuoto",
                        "Il cognome deve essere tra 2 e 50 caratteri"
                );
    }

    @Test
    void whenEmailIsBlank_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("") // Blank
                .status(ContactStatus.CUSTOMER)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("L'email non può essere vuota");
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void whenEmailIsInvalidFormat_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("invalid-email") // Invalid format
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il formato dell'email non è valido");
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void whenStatusIsNull_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(null) // Null status
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Lo stato non può essere nullo");
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("status");
    }

    // Test aggiuntivi per le funzionalità generate da Lombok (opzionali ma utili per completezza)
    @Test
    void whenUsingLombok_thenGettersSettersWork() {
        ContactDTO dto = new ContactDTO();
        dto.setFirstName("Test");
        assertThat(dto.getFirstName()).isEqualTo("Test");
    }

    @Test
    void whenUsingLombok_thenEqualsAndHashCodeWork() {
        ContactDTO dto1 = ContactDTO.builder().email("test@example.com").build();
        ContactDTO dto2 = ContactDTO.builder().email("test@example.com").build();
        ContactDTO dto3 = ContactDTO.builder().email("another@example.com").build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1).isNotEqualTo(dto3);
    }

    @Test
    void whenUsingLombok_thenToStringWorks() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .build();
        assertThat(dto.toString()).contains("firstName=Mario", "lastName=Rossi", "email=mario.rossi@example.com");
    }

    @Test
    void whenFirstNameTooShort_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("A") // Solo 1 carattere
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il nome deve essere tra 2 e 50 caratteri");
    }

    @Test
    void whenFirstNameTooLong_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("A".repeat(51)) // 51 caratteri
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il nome deve essere tra 2 e 50 caratteri");
    }

    @Test
    void whenLastNameTooShort_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("R") // Solo 1 carattere
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il cognome deve essere tra 2 e 50 caratteri");
    }

    @Test
    void whenEmailTooLong_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("a".repeat(95) + "@test.com") // > 100 caratteri
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        "L'email non può superare i 100 caratteri",
                        "Il formato dell'email non è valido"
                );
    }


    @Test
    void whenPhoneInvalidFormat_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .phone("abc123") // Formato non valido
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Formato telefono non valido");
    }

    @Test
    void whenCompanyNameTooLong_thenViolation() {
        ContactDTO dto = ContactDTO.builder()
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .companyName("A".repeat(101)) // > 100 caratteri
                .status(ContactStatus.LEAD)
                .build();

        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il nome dell'azienda non può superare i 100 caratteri");
    }
}
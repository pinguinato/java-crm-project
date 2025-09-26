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
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il nome non può essere vuoto");
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("firstName");
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
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Il cognome non può essere vuoto");
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("lastName");
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
}
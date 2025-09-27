package it.gianotto.crm_project.contact.service.mapper;

import it.gianotto.crm_project.contact.ContactStatus;
import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.service.dto.ContactDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    private ContactMapper contactMapper;

    @BeforeEach
    void setUp() {
        // Inizializziamo il mapper prima di ogni test
        contactMapper = new ContactMapper();
    }

    @Test
    @DisplayName("Dovrebbe mappare correttamente da Contact a ContactDTO")
    void shouldMapContactToContactDTO() {
        // Arrange: prepariamo un'entità Contact completa
        Contact contact = Contact.builder()
                .id(1)
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .phone("1234567890")
                .companyName("Acme Corp")
                .status(ContactStatus.LEAD)
                .build();

        // Act: eseguiamo la mappatura
        ContactDTO contactDTO = contactMapper.toDTO(contact);

        // Assert: verifichiamo che tutti i campi siano stati mappati correttamente
        assertNotNull(contactDTO);
        assertEquals(contact.getId(), contactDTO.getId());
        assertEquals(contact.getFirstName(), contactDTO.getFirstName());
        assertEquals(contact.getLastName(), contactDTO.getLastName());
        assertEquals(contact.getEmail(), contactDTO.getEmail());
        assertEquals(contact.getPhone(), contactDTO.getPhone());
        assertEquals(contact.getCompanyName(), contactDTO.getCompanyName());
        assertEquals(contact.getStatus(), contactDTO.getStatus());
    }

    @Test
    @DisplayName("Dovrebbe mappare correttamente da ContactDTO a Contact")
    void shouldMapContactDTOToContact() {
        // Arrange: prepariamo un DTO completo
        ContactDTO contactDTO = ContactDTO.builder()
                .id(1)
                .firstName("Luigi")
                .lastName("Verdi")
                .email("luigi.verdi@example.com")
                .phone("0987654321")
                .companyName("Beta Inc")
                .status(ContactStatus.CUSTOMER)
                .build();

        // Act: eseguiamo la mappatura
        Contact contact = contactMapper.toEntity(contactDTO);

        // Assert: verifichiamo che tutti i campi siano stati mappati correttamente
        assertNotNull(contact);
        assertEquals(contactDTO.getId(), contact.getId());
        assertEquals(contactDTO.getFirstName(), contact.getFirstName());
        assertEquals(contactDTO.getLastName(), contact.getLastName());
        assertEquals(contactDTO.getEmail(), contact.getEmail());
        assertEquals(contactDTO.getPhone(), contact.getPhone());
        assertEquals(contactDTO.getCompanyName(), contact.getCompanyName());
        assertEquals(contactDTO.getStatus(), contact.getStatus());
    }

    @Test
    @DisplayName("toDTO dovrebbe restituire null se l'input Contact è null")
    void toDTO_shouldReturnNull_whenContactIsNull() {
        // Act & Assert
        assertNull(contactMapper.toDTO(null));
    }

    @Test
    @DisplayName("toEntity dovrebbe restituire null se l'input ContactDTO è null")
    void toEntity_shouldReturnNull_whenContactDTOIsNull() {
        // Act & Assert
        assertNull(contactMapper.toEntity(null));
    }

    @Test
    @DisplayName("Dovrebbe mappare correttamente un Contact con campi null a DTO")
    void shouldMapContactWithNullFieldsToDTO() {
        // Arrange: prepariamo un'entità con alcuni campi null
        Contact contact = Contact.builder()
                .id(2)
                .firstName("Paolo")
                .lastName("Bianchi")
                .email("paolo.bianchi@example.com")
                .phone(null) // Campo nullo
                .companyName(null) // Campo nullo
                .status(ContactStatus.ARCHIVED)
                .build();

        // Act
        ContactDTO contactDTO = contactMapper.toDTO(contact);

        // Assert
        assertNotNull(contactDTO);
        assertEquals("Paolo", contactDTO.getFirstName());
        assertNull(contactDTO.getPhone()); // Verifichiamo che il campo sia rimasto nullo
        assertNull(contactDTO.getCompanyName()); // Verifichiamo che il campo sia rimasto nullo
    }
}
package it.gianotto.crm_project.contact.service.impl;

import it.gianotto.crm_project.contact.ContactStatus;
import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.data.repository.ContactRepository;
import it.gianotto.crm_project.contact.service.dto.ContactDTO;
import it.gianotto.crm_project.contact.service.mapper.ContactMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatcServiceImplTest {

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContatcServiceImpl contactService;

    @Test
    void testGetContacts() {
        // Given: Prepariamo i dati di test
        Contact contactEntity = Contact.builder()
                .id(1)
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        ContactDTO contactDTO = ContactDTO.builder()
                .id(1)
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario.rossi@example.com")
                .status(ContactStatus.LEAD)
                .build();

        // Quando il repository viene chiamato, restituisce la nostra entità di test
        when(contactRepository.findAll()).thenReturn(Collections.singletonList(contactEntity));
        // Quando il mapper viene chiamato con l'entità, restituisce il nostro DTO di test
        when(contactMapper.toDTO(contactEntity)).thenReturn(contactDTO);

        // When: Eseguiamo il metodo da testare
        List<ContactDTO> result = contactService.getContacts();

        // Then: Verifichiamo i risultati
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(contactDTO, result.get(0));
        verify(contactRepository).findAll();
        verify(contactMapper).toDTO(contactEntity);
    }

    @Test
    void testAddNewContact_success() {
        // Given: Prepariamo il DTO in input e l'entità che verrà salvata
        ContactDTO inputDTO = ContactDTO.builder()
                .firstName("Nuovo")
                .lastName("Contatto")
                .email("nuovo@example.com")
                .status(ContactStatus.LEAD)
                .build();

        Contact contactToSave = new Contact(); // L'entità senza ID che viene passata al save

        Contact savedContact = Contact.builder() // L'entità come restituita dal DB, con ID
                .id(100)
                .firstName("Nuovo")
                .lastName("Contatto")
                .email("nuovo@example.com")
                .status(ContactStatus.LEAD)
                .build();

        // Mocking delle chiamate
        when(contactRepository.findByEmail(inputDTO.getEmail())).thenReturn(Optional.empty());
        when(contactMapper.toEntity(inputDTO)).thenReturn(contactToSave);
        when(contactRepository.save(contactToSave)).thenReturn(savedContact);
        when(contactMapper.toDTO(savedContact)).thenReturn(ContactDTO.builder().id(100).email("nuovo@example.com").build()); // DTO finale

        // When
        ContactDTO resultDTO = contactService.addNewContact(inputDTO);

        // Then
        assertNotNull(resultDTO);
        assertEquals(100, resultDTO.getId()); // Verifichiamo che l'ID sia stato popolato
        assertEquals("nuovo@example.com", resultDTO.getEmail());

        verify(contactRepository).findByEmail(inputDTO.getEmail());
        verify(contactRepository).save(contactToSave);
        verify(contactMapper).toDTO(savedContact);
    }

    @Test
    void testAddNewContact_emailExists() {
        // Given
        ContactDTO existingDTO = ContactDTO.builder().email("test@example.com").build();
        when(contactRepository.findByEmail(existingDTO.getEmail())).thenReturn(Optional.of(new Contact()));

        // When & Then
        assertThrows(IllegalStateException.class, () -> contactService.addNewContact(existingDTO));
        verify(contactRepository).findByEmail(existingDTO.getEmail());
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void testRemoveContact_success() {
        Integer id = 1;
        when(contactRepository.existsById(id)).thenReturn(true);

        contactService.removeContact(id);

        verify(contactRepository).existsById(id);
        verify(contactRepository).deleteById(id);
    }

    @Test
    void testRemoveContact_notFound() {
        Integer id = 1;
        when(contactRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalStateException.class, () -> contactService.removeContact(id));
        verify(contactRepository).existsById(id);
        verify(contactRepository, never()).deleteById(id);
    }

    @Test
    void testUpdateContact_success() {
        ContactStatus status = ContactStatus.LEAD;
        Integer id = 1;
        Contact existing = Contact.builder()
                .id(id)
                .firstName("Mario")
                .lastName("Rossi")
                .email("mario@old.com")
                .phone("123")
                .companyName("OldCo")
                .status(ContactStatus.LEAD)
                .build();

        Contact updated = Contact.builder()
                .firstName("Luigi")
                .lastName("Verdi")
                .email("luigi@new.com")
                .phone("456")
                .companyName("NewCo")
                .status(ContactStatus.CUSTOMER)
                .build();

        when(contactRepository.findById(id)).thenReturn(Optional.of(existing));
        when(contactRepository.save(any(Contact.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Contact result = contactService.updateContact(id, updated);

        assertEquals("Luigi", result.getFirstName());
        assertEquals("Verdi", result.getLastName());
        assertEquals("luigi@new.com", result.getEmail());
        assertEquals("456", result.getPhone());
        assertEquals("NewCo", result.getCompanyName());
        verify(contactRepository).findById(id);
        verify(contactRepository).save(existing);
    }

    @Test
    void testUpdateContact_notFound() {
        Integer id = 1;
        Contact updated = new Contact();
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> contactService.updateContact(id, updated));
        verify(contactRepository).findById(id);
        verify(contactRepository, never()).save(any(Contact.class));
    }
}
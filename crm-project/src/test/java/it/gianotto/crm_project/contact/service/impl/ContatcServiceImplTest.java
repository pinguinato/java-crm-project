package it.gianotto.crm_project.contact.service.impl;

import it.gianotto.crm_project.contact.ContactStatus;
import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.data.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContatcServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContatcServiceImpl contactService;

    @Test
    void testGetContacts() {
        // Given
        Contact contact = new Contact();
        List<Contact> contacts = Collections.singletonList(contact);
        when(contactRepository.findAll()).thenReturn(contacts);

        // When
        List<Contact> result = contactService.getContacts();

        // Then
        assertEquals(contacts, result);
        verify(contactRepository).findAll();
    }

    @Test
    void testAddNewContact_success() {
        // Given
        Contact contact = Contact.builder().email("test@example.com").build();
        when(contactRepository.findByEmail(contact.getEmail())).thenReturn(Optional.empty());
        when(contactRepository.save(contact)).thenReturn(contact);

        // When
        Contact result = contactService.addNewContact(contact);

        // Then
        assertEquals(contact, result);
        verify(contactRepository).findByEmail(contact.getEmail());
        verify(contactRepository).save(contact);
    }

    @Test
    void testAddNewContact_emailExists() {
        // Given
        Contact contact = Contact.builder().email("test@example.com").build();
        when(contactRepository.findByEmail(contact.getEmail())).thenReturn(Optional.of(contact));

        // When & Then
        assertThrows(IllegalStateException.class, () -> contactService.addNewContact(contact));
        verify(contactRepository).findByEmail(contact.getEmail());
        verify(contactRepository, never()).save(contact);
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
                .build();

        Contact updated = Contact.builder()
                .firstName("Luigi")
                .lastName("Verdi")
                .email("luigi@new.com")
                .phone("456")
                .companyName("NewCo")
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
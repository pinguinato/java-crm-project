package it.gianotto.crm_project.contact.service.impl;

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
}
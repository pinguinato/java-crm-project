package it.gianotto.crm_project.contact.data.entity;

import it.gianotto.crm_project.contact.ContactStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactTest {

    @Test
    void testContactCreation() {
        Contact contact = new Contact();
        contact.setId(1);
        contact.setFirstName("Mario");
        contact.setLastName("Rossi");
        contact.setEmail("mario.rossi@example.com");
        contact.setPhone("123456789");
        contact.setCompanyName("ACME Inc.");
        contact.setStatus(ContactStatus.LEAD);

        assertEquals(1, contact.getId());
        assertEquals("Mario", contact.getFirstName());
        assertEquals("Rossi", contact.getLastName());
        assertEquals("mario.rossi@example.com", contact.getEmail());
        assertEquals("123456789", contact.getPhone());
        assertEquals("ACME Inc.", contact.getCompanyName());
        assertEquals(ContactStatus.LEAD, contact.getStatus());
    }
}
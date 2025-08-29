package it.gianotto.crm_project.contact.service;

import it.gianotto.crm_project.contact.data.entity.Contact;

import java.util.List;

public interface ContactService {
    /**
     * Retrieve all Contacts
     * @return a list of all Contacts entities
     */
    List<Contact> getContacts();

    /**
     * Adds a new contact to the system after validating it
     * @param contact The cotact object to be added
     * @return The saved entity, including its generated ID.
     */
    Contact addNewContact(Contact contact);
}

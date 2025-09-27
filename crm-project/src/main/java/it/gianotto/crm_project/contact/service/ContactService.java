package it.gianotto.crm_project.contact.service;

import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.service.dto.ContactDTO;

import java.util.List;

public interface ContactService {
    /**
     * Retrieve all Contacts
     * @return a list of all Contacts dto
     */
    List<ContactDTO> getContacts();

    /**
     * Adds a new contact to the system after validating it
     * @param contact The cotact object to be added
     * @return The saved entity, including its generated ID.
     */
    Contact addNewContact(Contact contact);

    /**
     * Removes a contact from the system by its ID
     * @param contactId The ID of the contact to be removed
     */
    void removeContact(Integer contactId);

    /**
     * Updates an existing contact's information
     * @param id The ID of the contact to be updated
     * @param updatedContact The contact object containing updated information
     * @return The updated contact entity
     */
    Contact updateContact(Integer id, Contact updatedContact);
}

package it.gianotto.crm_project.contact.service.impl;

import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.data.repository.ContactRepository;
import it.gianotto.crm_project.contact.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContatcServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private static final String SERVICE_NAME = ContatcServiceImpl.class.getName();

    @Override
    public List<Contact> getContacts() {
        log.info("{} - getContacts()", SERVICE_NAME);

        return contactRepository.findAll();
    }

    @Override
    public Contact addNewContact(Contact contact) {
        log.info("{} - addNewContact({})", SERVICE_NAME, contact);

        Optional<Contact> contactOptional = contactRepository.findByEmail(contact.getEmail());

        if (contactOptional.isPresent()) {
            log.error("A contact with email: {} already exists:", contact.getEmail());
            throw new IllegalStateException("A contact with email: " + contact.getEmail() + " already exists.");
        }

        return contactRepository.save(contact);
    }

    @Override
    public void removeContact(Integer id) {
        log.info("{} - removeContact({})", SERVICE_NAME, id);
        if (!contactRepository.existsById(id)) {
            log.error("Contact with id: {} does not exist", id);
            throw new IllegalStateException("Contact with id: " + id + " does not exist.");
        }
        contactRepository.deleteById(id);
    }

    @Override
    public Contact updateContact(Integer id, Contact updatedContact) {
        log.info("{} - updateContact({}, {})", SERVICE_NAME, id, updatedContact);
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Contact with id: " + id + " does not exist."));

        existingContact.setFirstName(updatedContact.getFirstName());
        existingContact.setLastName(updatedContact.getLastName());
        existingContact.setEmail(updatedContact.getEmail());
        existingContact.setPhone(updatedContact.getPhone());
        existingContact.setCompanyName(updatedContact.getCompanyName());
        existingContact.setStatus(updatedContact.getStatus());

        return contactRepository.save(existingContact);
    }
}

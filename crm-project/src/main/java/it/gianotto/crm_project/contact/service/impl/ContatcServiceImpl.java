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
}

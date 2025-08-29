package it.gianotto.crm_project.contact.service.impl;

import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.data.repository.ContactRepository;
import it.gianotto.crm_project.contact.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContatcServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public List<Contact> getContacts() {
        return null;
    }

    @Override
    public Contact addNewContact(Contact contact) {
        return null;
    }
}

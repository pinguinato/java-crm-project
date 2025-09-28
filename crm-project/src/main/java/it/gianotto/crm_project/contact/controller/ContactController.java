package it.gianotto.crm_project.contact.controller;

import it.gianotto.crm_project.contact.service.ContactService;
import it.gianotto.crm_project.contact.service.dto.ContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/contacts")
public class ContactController {

    private static final String CONTROLLER_NAME = ContactController.class.getName();
    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("[{}] - getAllContacts()", CONTROLLER_NAME);

        List<ContactDTO> contacts = contactService.getContacts();

        return ResponseEntity.ok(contacts);
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO) {
        log.info("[{}] - createContact(): first name : {} last name: {}", CONTROLLER_NAME, contactDTO.getFirstName(), contactDTO.getLastName());

        ContactDTO createdContact = contactService.addNewContact(contactDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }



}

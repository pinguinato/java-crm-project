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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/contacts")
public class ContactController {


    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {

        List<ContactDTO> contacts = contactService.getContacts();

        return ResponseEntity.ok(contacts);
    }
}

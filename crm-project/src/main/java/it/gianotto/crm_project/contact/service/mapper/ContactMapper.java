package it.gianotto.crm_project.contact.service.mapper;

import it.gianotto.crm_project.contact.data.entity.Contact;
import it.gianotto.crm_project.contact.service.dto.ContactDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        if (Objects.isNull(contact))
            return null;

        return ContactDTO.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .companyName(contact.getCompanyName())
                .status(contact.getStatus())
                .build();
    }

    public Contact toEntity(ContactDTO contactDTO) {
        if (Objects.isNull(contactDTO))
            return null;

        return Contact.builder()
                .id(contactDTO.getId())
                .firstName(contactDTO.getFirstName())
                .lastName(contactDTO.getLastName())
                .email(contactDTO.getEmail())
                .phone(contactDTO.getPhone())
                .companyName(contactDTO.getCompanyName())
                .status(contactDTO.getStatus())
                .build();
    }
}

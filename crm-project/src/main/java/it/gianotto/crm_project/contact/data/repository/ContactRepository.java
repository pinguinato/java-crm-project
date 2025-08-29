package it.gianotto.crm_project.contact.data.repository;

import it.gianotto.crm_project.contact.data.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    /**
     * Search a Contact by email
     * @param email contacts email
     * @return a Contact entity object
     */
    Optional<Contact> findByEmail(String email);
}

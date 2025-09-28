package it.gianotto.crm_project.contact.controller;

import it.gianotto.crm_project.contact.service.dto.ContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/test")
public class TestController {

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping
    public ResponseEntity<Map<String, String>> getMicroserviceInfo() {
        log.info("Request received for microservice info");

        String springVersion = SpringBootVersion.getVersion();

        Map<String, String> info = Map.of(
                "microserviceName", applicationName,
                "springBootVersion", springVersion,
                "greeting", "Ciao dal " + applicationName + "!"
        );

        return ResponseEntity.ok(info);
    }
}

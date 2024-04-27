package com.edcarlitos.conctactapi.controller;

import com.edcarlitos.conctactapi.dto.ContactResponse;
import com.edcarlitos.conctactapi.entity.Contact;
import com.edcarlitos.conctactapi.exceptions.ContactNotFoundException;
import com.edcarlitos.conctactapi.service.ContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/contact")
@AllArgsConstructor
@EnableMethodSecurity
public class ContactController {

    private final ContactService contactService;


    @GetMapping("/{idUser}")
    @PreAuthorize("#idUser == authentication.principal.id")
    public ResponseEntity<Page<Contact>> getAllContacts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @PathVariable Long idUser){

        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllContacts(pageNumber,pageSize,idUser));
    }

    @PostMapping("/")
    @PreAuthorize("#contact.user.id == authentication.principal.id")
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody Contact contact){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.saveContact(contact));
    }


    @PutMapping("/{id}")
    @PreAuthorize("#contact.user.id == authentication.principal.id")
    public ResponseEntity<ContactResponse> updateContact(@Valid @RequestBody Contact contact, @PathVariable Long id) throws ContactNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.updateContact(contact, id));
    }


    @DeleteMapping("/{idUser}/{idContact}")
    @PreAuthorize("#idUser == authentication.principal.id")
    public ResponseEntity<?> deleteContact(@PathVariable Long idUser, @PathVariable Long idContact) throws ContactNotFoundException{
        contactService.deleteContact(idContact);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}










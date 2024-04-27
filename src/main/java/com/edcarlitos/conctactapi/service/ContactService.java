package com.edcarlitos.conctactapi.service;

import com.edcarlitos.conctactapi.dto.ContactResponse;
import com.edcarlitos.conctactapi.entity.Contact;
import com.edcarlitos.conctactapi.exceptions.ContactNotFoundException;
import com.edcarlitos.conctactapi.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Page<Contact> getAllContacts(Integer pageNumber, int pageSize,Long idUser) {
        Pageable pageable =  PageRequest.of(pageNumber, pageSize, Sort.by("fullName").descending());
        return contactRepository.findAllByUserId(idUser,pageable);
    }

    public ContactResponse saveContact(Contact contact) {
        Contact contact1 =  contactRepository.save(contact);
        return new ContactResponse(contact1.getId(), contact1.getFullName(),contact1.getPhoneNumber(),contact1.getUser().getId());
    }

    public ContactResponse updateContact(Contact contact,Long id) throws ContactNotFoundException {
        Contact contactFind = contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("El contacto no existe"));

        contactFind.setPhoneNumber(contact.getPhoneNumber());
        contactFind.setFullName(contact.getFullName());

        contactFind =  contactRepository.save(contactFind);
        return new ContactResponse(contactFind.getId(), contactFind.getFullName(),contactFind.getPhoneNumber(),contactFind.getUser().getId());
    }

    public void deleteContact(Long id) throws ContactNotFoundException {
        contactRepository.findById(id).orElseThrow(() -> new ContactNotFoundException("El contacto no existe"));


        contactRepository.deleteById(id);
    }


}

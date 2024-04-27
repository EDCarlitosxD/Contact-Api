package com.edcarlitos.conctactapi.repository;

import com.edcarlitos.conctactapi.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories()
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findAllByUserId(Long userId, Pageable pageable);

}

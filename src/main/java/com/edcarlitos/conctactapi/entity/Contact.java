package com.edcarlitos.conctactapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name = "contacts")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;
    @NotBlank
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private UserEntity user;
}

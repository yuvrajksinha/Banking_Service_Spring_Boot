package com.javaSpringProject.BankingService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Contacts")
@Entity
public class Contact {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String landmark;
    private String pincode;
    private String state;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

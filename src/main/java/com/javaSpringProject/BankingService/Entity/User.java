package com.javaSpringProject.BankingService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name="First_name")
    private String firstName;
    @Column(name="Middle_name")
    private String middleName;
    @Column(name="Last_name")
    private String lastName;
    @Column(name="Date_Of_Birth")
    private LocalDate dateOfBirth;
    private String gender;
    private String prefix;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Contact contact;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();   //declare here so that there is no null pointer creation

}

package com.javaSpringProject.BankingService.Repository;

import com.javaSpringProject.BankingService.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}

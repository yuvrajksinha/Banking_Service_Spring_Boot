package com.javaSpringProject.BankingService.Repository;

import com.javaSpringProject.BankingService.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}

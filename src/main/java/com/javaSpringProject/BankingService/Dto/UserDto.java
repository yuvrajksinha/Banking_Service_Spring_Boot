package com.javaSpringProject.BankingService.Dto;

import java.time.LocalDate;

public record UserDto(Long id, String firstName, String middleName, String lastName, LocalDate dateOfBirth,String gender,String prefix,ContactDto contactDto) {}

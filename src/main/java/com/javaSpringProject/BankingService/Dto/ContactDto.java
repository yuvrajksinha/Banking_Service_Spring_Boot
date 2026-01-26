package com.javaSpringProject.BankingService.Dto;

public record ContactDto(Long id,String email,String phoneNumber1,String phoneNumber2,String addressLine1,String addressLine2,String city,String landmark,String pincode,String state) {}

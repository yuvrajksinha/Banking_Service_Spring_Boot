package com.javaSpringProject.BankingService.Dto;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class AccountDto {
//    private Long id;
//    private String accountHolderName;
//    private double balance;
//}

public record AccountDto(Long id,String accountType,String accountNumber,String branchIfsc,double balance,double funds){}



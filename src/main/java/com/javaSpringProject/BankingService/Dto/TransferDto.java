package com.javaSpringProject.BankingService.Dto;

public record TransferDto(Long sourceId,Long targetId,double amount){
}

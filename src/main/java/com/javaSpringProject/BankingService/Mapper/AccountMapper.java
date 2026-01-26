package com.javaSpringProject.BankingService.Mapper;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Entity.Account;

public class AccountMapper {
//    public static Account mapToAccount(AccountDto accountDto){
//        Account account = new Account(accountDto.id(),
//                accountDto.accountHolderName(),
//                accountDto.balance(),
//                accountDto.accountType(),
//                accountDto.funds());
//        return account;
//    }
    public static AccountDto mapToAccountDto(Account account){
        return new AccountDto(account.getId(),
                account.getAccountType(),
                account.getAccountNumber(),
                account.getBranchIfsc(),
                account.getBalance(),
                account.getFunds());
    }
}

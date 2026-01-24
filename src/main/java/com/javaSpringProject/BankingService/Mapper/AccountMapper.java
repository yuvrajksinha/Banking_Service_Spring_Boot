package com.javaSpringProject.BankingService.Mapper;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Entity.Account;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account(accountDto.id(),
                accountDto.accountHolderName(),
                accountDto.balance(),
                accountDto.accountType(),
                accountDto.funds());
        return account;
    }
    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto(account.getId(),
                account.getAccountHolderName(),
                account.getBalance(),
                account.getAccountType(),
                account.getFunds());
        return accountDto;
    }
}

package com.javaSpringProject.BankingService.Mapper;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.CurrentsAccount;
import com.javaSpringProject.BankingService.Entity.SavingsAccount;

public class AccountMapper {
    public static Account mapToAccount(AccountDto accountDto){
        if("SAVINGS".equalsIgnoreCase(accountDto.accountType())){
            SavingsAccount savings = new SavingsAccount();
            savings.setId(accountDto.id());
            savings.setAccountHolderName(accountDto.accountHolderName());
            savings.setBalance(accountDto.balance());
            return savings;
        } else {
            CurrentsAccount currents = new CurrentsAccount();
            currents.setId(accountDto.id());
            currents.setAccountHolderName(accountDto.accountHolderName());
            currents.setBalance(accountDto.balance());
            currents.setFunds(accountDto.funds());
            return currents;
        }
    }
    public static AccountDto mapToAccountDto(Account account){
        String type = "";
        Double funds=null;
        if(account instanceof SavingsAccount){
            type = "SAVINGS";
        } else if(account instanceof CurrentsAccount current){
            type = "CURRENT";
            funds = current.getFunds();
        }

        return new AccountDto(account.getId(),
                account.getAccountHolderName(),
                account.getBalance(),
                type,
                funds);
    }
}

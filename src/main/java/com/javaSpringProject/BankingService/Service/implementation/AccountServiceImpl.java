package com.javaSpringProject.BankingService.Service.implementation;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.CurrentsAccount;
import com.javaSpringProject.BankingService.Entity.SavingsAccount;
import com.javaSpringProject.BankingService.Exception.AccountException;
import com.javaSpringProject.BankingService.Mapper.AccountMapper;
import com.javaSpringProject.BankingService.Repository.AccountRepository;
import com.javaSpringProject.BankingService.Service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Transactional
    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        double total=account.getBalance()+amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        account.withdraw(amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        // return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        //OR
        // return accounts.stream().map(AccountMapper::mapToAccountDto).toList();
        //OR
        List<AccountDto> accountsDto = new ArrayList<>();
        for(Account account:accounts){
            AccountDto accountDto = AccountMapper.mapToAccountDto(account);
            accountsDto.add(accountDto);
        }
        return accountsDto;
    }

    @Override
    public void deleteAccount(Long id) {
//        Account account = accountRepository
//                .findById(id)
//                .orElseThrow(()->new RuntimeException("Account does not exist"));
        //OR
        if(!accountRepository.existsById(id)){
            throw new AccountException("Account does not exist");
        }
        accountRepository.deleteById(id);
    }

    @Override
    public String getAccountType(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(()->new AccountException("Account not Found"));
        if(account instanceof SavingsAccount){
            return "SAVINGS";
        } else if(account instanceof CurrentsAccount){
            return "CURRENT";
        }
        return "UNKNOWN";
    }

}

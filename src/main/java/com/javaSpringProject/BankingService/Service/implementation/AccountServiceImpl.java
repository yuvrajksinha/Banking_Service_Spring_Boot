package com.javaSpringProject.BankingService.Service.implementation;

import com.javaSpringProject.BankingService.Dto.AccountDto;
import com.javaSpringProject.BankingService.Dto.UserDto;
import com.javaSpringProject.BankingService.Dto.UserRegistrationDto;
import com.javaSpringProject.BankingService.Entity.Account;
import com.javaSpringProject.BankingService.Entity.User;
import com.javaSpringProject.BankingService.Exception.AccountException;
import com.javaSpringProject.BankingService.Mapper.AccountMapper;
import com.javaSpringProject.BankingService.Mapper.UserMapper;
import com.javaSpringProject.BankingService.Repository.AccountRepository;
import com.javaSpringProject.BankingService.Repository.UserRepository;
import com.javaSpringProject.BankingService.Service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository,UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDto createAccount(UserRegistrationDto userRegistrationDto) {
        User user = UserMapper.mapToUserEntity(userRegistrationDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserProfile(Long userId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(()->new AccountException("User not Found"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public String getAccountType(Long id) {
        Account account = accountRepository.
                findById(id).
                orElseThrow(()->new AccountException("Account not Found"));
        return account.getAccountType();
    }

    @Transactional
    @Override
    public AccountDto addAccountById(Long id, AccountDto accountDto) {
        User user = userRepository.
                findById(id).
                orElseThrow(()->new AccountException("User not found"));
        Account newAccount = new Account();
        newAccount.setAccountNumber(accountDto.accountNumber());
        newAccount.setAccountType(accountDto.accountType());
        newAccount.setBranchIfsc(accountDto.branchIfsc());
        newAccount.setBalance(accountDto.balance());
        newAccount.setFunds(accountDto.funds());

        newAccount.setUser(user);

        Account savedAccount = accountRepository.save(newAccount);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->new AccountException("Account does not exist"));
        account.setBalance(account.getBalance()+amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository
                .findByIdWithLock(id)
                .orElseThrow(()->new AccountException("Account does not exist"));

        if("SAVINGS".equalsIgnoreCase(account.getAccountType())){
            if(account.getBalance() < amount){
                throw new AccountException("Insufficient Balance");
            }
        } else {
            if((account.getBalance()+account.getFunds()) < amount){
                throw new AccountException("Insufficient Funds");
            }
        }

        account.setBalance(account.getBalance()-amount);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Transactional
    @Override
    public void transferMoney(Long sourceId, Long targetId, double amount) {
        if(sourceId.equals(targetId)){
            throw new AccountException("Cannot transfer to the same account");
        }
        Long firstId = Math.min(sourceId,targetId);
        Long secondId = Math.max(sourceId,targetId);
        accountRepository.findByIdWithLock(firstId).orElseThrow(()->new AccountException("Account "+firstId+" not found"));
        accountRepository.findByIdWithLock(secondId).orElseThrow(()->new AccountException("Account "+secondId+" not found"));

        Account source = accountRepository.findById(sourceId).get();
        Account target = accountRepository.findById(targetId).get();

        if("SAVINGS".equalsIgnoreCase(source.getAccountType())){
            if(source.getBalance() < amount){
                throw new AccountException("Insufficient Balance to transfer");
            }
        } else { //Account type is current
            if((source.getBalance()+source.getFunds())<amount){
                throw new AccountException("Insufficient Balance and funds");
            }
        }

        source.setBalance(source.getBalance()-amount);
        target.setBalance(target.getBalance()+amount);

        accountRepository.save(source);
        accountRepository.save(target);
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
    public List<AccountDto> getAccountsByUserId(Long userId) {
        User user = userRepository.
                findById(userId).
                orElseThrow(()->new AccountException("User not Found"));
        return user.getAccounts().stream().
                map(AccountMapper::mapToAccountDto).toList();
    }

    @Transactional
    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));

        User user = account.getUser();

        if(user.getAccounts().size()<=1){
            userRepository.delete(user);
        } else {
            accountRepository.delete(account);
        }
    }

}

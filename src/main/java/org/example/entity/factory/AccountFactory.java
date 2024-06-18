package org.example.entity.factory;

import org.example.entity.Account;
import org.example.entity.dto.AccountDto;

public class AccountFactory {

    public static Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setValue(dto.getValue());
        account.setCurrency(dto.getCurrency());
        return account;
    }

    public static AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCurrency(account.getCurrency());
        dto.setValue(account.getValue());
        return dto;
    }
}

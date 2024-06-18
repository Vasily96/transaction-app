package org.example.entity.factory;

import org.example.entity.Bank;
import org.example.entity.dto.BankDto;

public class BankFactory {

    public static Bank toEntity(BankDto bankDto) {
        Bank bank = new Bank();
        bank.setName(bankDto.getName());
        bank.setIndividualCommission(bankDto.getIndividualCommission());
        bank.setLegalCommission(bankDto.getLegalCommission());
        return bank;
    }

    public static BankDto toDto(Bank bank) {
        BankDto bankDto = new BankDto();
        bankDto.setName(bank.getName());
        bankDto.setIndividualCommission(bank.getIndividualCommission());
        bankDto.setLegalCommission(bank.getLegalCommission());
        return bankDto;
    }
}

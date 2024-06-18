package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.Client;
import org.example.entity.dto.BankDto;
import org.example.entity.factory.BankFactory;
import org.example.repository.BankRepository;
import org.example.utils.validator.BankValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BankService {

    private final BankRepository bankRepository;
    private final BankValidator bankValidator;

    @Transactional(readOnly = true)
    public Bank getBank(Long id) {
        return bankRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public BankDto check(BankDto bankDto) {
        final DataBinder dataBinder = new DataBinder(bankDto);
        dataBinder.addValidators(bankValidator);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().forEach(
                    objectError ->
                            log.error(objectError.getDefaultMessage())
            );
            log.error("Bank wasn't created");
            return null;
        }
        return bankDto;
    }

    public Bank createBank(BankDto bankDto) {
        if (check(bankDto) == null) return null;
        if (bankRepository.findBankByName(bankDto.getName()).isPresent()) {
            log.error("Bank with name {} is exist", bankDto.getName());
            return null;
        }
        Bank bank = BankFactory.toEntity(bankDto);
        log.info("bank with name {} was created", bankDto.getName());
        return bankRepository.save(bank);
    }

    public void updateBank(Long id, BankDto bankDto) {
        if (bankRepository.findBankByName(bankDto.getName()).isPresent()) {
            log.error("Bank with name {} is exist", bankDto.getName());
            return;
        }
        bankRepository.findById(id).ifPresentOrElse(client -> {
            if (bankDto.getName() != null)
                client.setName(bankDto.getName());
            if (bankDto.getIndividualCommission() != null)
                client.setIndividualCommission(bankDto.getIndividualCommission());
            if (bankDto.getLegalCommission() != null)
                client.setLegalCommission(bankDto.getLegalCommission());
        }, () -> {
            throw new NoSuchElementException();
        });
    }

    public void deleteBank(Long id) {
        bankRepository.deleteById(id);
        log.info("bank with id {} was removed", id);
    }

    public List<Account> getAccounts(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow();
        return bank.getAccounts();
    }

    @Transactional(readOnly = true)
    public Bank fetchAccounts(Long id) {
        return bankRepository.fetchBankWithAccount(id).orElseThrow();
    }

    public List<Client> getClients(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow();
        return bank.getAccounts().stream().map(Account::getClient).distinct().toList();
    }
}

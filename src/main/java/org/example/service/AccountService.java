package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.entity.dto.AccountDto;
import org.example.entity.dto.ClientDto;
import org.example.entity.factory.AccountFactory;
import org.example.repository.*;
import org.example.utils.enums.ClientsType;
import org.example.utils.validator.AccountValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;
    private final CurrencyRepository currencyRepository;
    private final TransactionRepository transactionRepository;
    private final AccountValidator accountValidator;

    @Transactional(readOnly = true)
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public AccountDto check(AccountDto accountDto) {
        final DataBinder dataBinder = new DataBinder(accountDto);
        dataBinder.addValidators(accountValidator);
        dataBinder.validate();

        if (dataBinder.getBindingResult().hasErrors()) {
            dataBinder.getBindingResult().getAllErrors().forEach(
                    objectError ->
                            log.error(objectError.getDefaultMessage())
            );
            log.error("Account wasn't created");
            return null;
        }
        return accountDto;
    }

    @SneakyThrows
    public Account createAccount(AccountDto accountDto, Long clientId, Long bankId) {
        if (check(accountDto) == null) return null;
        if (accountRepository.findAccountByAccountNumber(accountDto.getAccountNumber()).isPresent()) {
            log.error("Account with name {} is exist", accountDto.getAccountNumber());
            return null;
        }
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        Client client = clientRepository.findById(clientId).orElseThrow();
        Account account = AccountFactory.toEntity(accountDto);
        account.setClient(client);
        account.setBank(bank);
        log.info("account with number {} was created", accountDto.getAccountNumber());
        return accountRepository.save(account);
    }

    public void transferMoney(Long accountIdFrom, Long accountIdTo, Double value) {
        Account sender = accountRepository.findById(accountIdFrom).orElseThrow();
        Account recipient = accountRepository.findById(accountIdTo).orElseThrow();
        Bank bank = recipient.getBank();
        double commission = 0d;
        if (!sender.getBank().getId().equals(bank.getId())) {
            commission = value *
                    (recipient.getClient().getType().equals(ClientsType.INDIVIDUAL) ?
                            bank.getIndividualCommission() :
                            bank.getLegalCommission()) / 100;
        }
        if (sender.getValue() < value + commission) {
            throw new NoSuchElementException();
        }
        double recipientValue = value;
        if (!sender.getCurrency().equals(recipient.getCurrency())) {
            CurrencyExchange exchange = currencyRepository
                    .findCurrencyExchangeByFirstAndSecond(sender.getCurrency(), recipient.getCurrency());
            if (exchange == null) throw new NoSuchElementException();
            recipientValue = recipientValue * exchange.getRate();
        }
        sender.setValue(sender.getValue() - (value + commission));
        String sendString = "%s sends to %s %s %s and commission is %s".formatted(
                sender.getAccountNumber(), recipient.getAccountNumber(), value, sender.getCurrency().toString(), commission);
        log.info(sendString);
        transactionRepository.save(new Transaction(null, sender, sendString, LocalDateTime.now()));
        recipient.setValue(recipient.getValue() + recipientValue);
        String recipientString = "%s receive from %s %s %s".formatted(
                recipient.getAccountNumber(), sender.getAccountNumber(), recipientValue, recipient.getCurrency().toString());
        log.info(recipientString);
        transactionRepository.save(new Transaction(null, recipient, recipientString, LocalDateTime.now()));
        accountRepository.save(sender);
        accountRepository.save(recipient);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
        log.info("account with {} was removed", id);

    }

    public void updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> {
            log.error("Current account is not exists");
            return new NoSuchElementException();
        });
        if (accountRepository.findAccountByAccountNumber(account.getAccountNumber()).isPresent()
                && !account.getAccountNumber().equals(accountDto.getAccountNumber())) {
            log.error("Account with name {} is exist", accountDto.getAccountNumber());
            return;
        }

        if (accountDto.getAccountNumber() != null)
            account.setAccountNumber(accountDto.getAccountNumber());
        if (accountDto.getValue() != null)
            account.setValue(accountDto.getValue());
        if (accountDto.getCurrency() != null)
            account.setCurrency(accountDto.getCurrency());
        log.info("Client {} was updated", account.getAccountNumber());
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsWithTime(Long accountId, LocalDateTime after, LocalDateTime before) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return transactionRepository.getAllByAccountAndTimestampAfterAndTimestampBefore(account, after, before);
    }
}

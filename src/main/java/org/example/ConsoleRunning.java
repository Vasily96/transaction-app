package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.configuration.ApplicationContextProvider;
import org.example.entity.Account;
import org.example.entity.Bank;
import org.example.entity.Client;
import org.example.entity.dto.AccountDto;
import org.example.entity.dto.BankDto;
import org.example.entity.dto.ClientDto;
import org.example.service.AccountService;
import org.example.service.BankService;
import org.example.service.ClientService;
import org.example.utils.enums.ClientsType;
import org.example.utils.enums.Currency;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Slf4j
public class ConsoleRunning {

    private static AccountService accountService;
    private static ClientService clientService;
    private static BankService bankService;
    private static DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");

    private final static String ILLEGAL_ARGUMENT = "IllegalArgument,check the data please";
    private final static String HELP = "interaction with the application occurs through the console/terminal.\n" +
            "Command format: command param1 param2 ... paramN\n" +
            "addAccount [accountNumber] [moneyValue] [currency] [clientId] [bankId]\n" +
            "addClient [clientName] [clientType]\n" +
            "addBank [bankName] [individualCommission] [legalCommission]\n" +
            "removeAccount [accountId]\n" +
            "removeClient [clientId]\n" +
            "removeBank [bankId]\n" +
            "getAccount [accountId]\n" +
            "getClient [clientId]\n" +
            "getBank [bankId]\n" +
            "getClientsAccounts [clientId]\n" +
            "getBankAccounts [bankId]\n" +
            "transfer [senderId] [recipientId] [money]\n" +
            "getAccountTransactions [accountId] [after] [before] data format is dd-MM-yyyy_HH:mm:ss\n" +
            "exit\n";

    public static void run() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        accountService = context.getBean(AccountService.class);
        clientService = context.getBean(ClientService.class);
        bankService = context.getBean(BankService.class);
        System.out.println("Hello World!");
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\n-------------------");
            System.out.println("BANK    OF     JAVA");
            System.out.println("-------------------\n");
            System.out.println("If you need help, type help ");
            System.out.print("\nEnter your choice : \n");
            String choice = sc.nextLine();
            String[] commandArgs = choice.split(" ");
            switch (commandArgs[0]) {
                case "help":
                    System.out.println(HELP);
                    break;
                case "addAccount":
                    addAccount(commandArgs);
                    break;
                case "addClient":
                    addClient(commandArgs);
                    break;
                case "addBank":
                    addBank(commandArgs);
                    break;
                case "removeAccount":
                    deleteAccount(commandArgs);
                    break;
                case "removeClient":
                    deleteClient(commandArgs);
                    break;
                case "removeBank":
                    deleteBank(commandArgs);
                    break;
                case "getAccount":
                    getAccount(commandArgs);
                    break;
                case "getClient":
                    getClient(commandArgs);
                    break;
                case "getBank":
                    getBank(commandArgs);
                    break;
                case "getClientsAccounts":
                    getClientsAccounts(commandArgs);
                    break;
                case "getBankAccounts":
                    getBankAccounts(commandArgs);
                    break;
                case "transfer":
                    transfer(commandArgs);
                    break;
                case "getTransactions":
                    getTransactions(commandArgs);
                    break;
                case "exit":
                    System.out.println("\nThank you for choosing Bank Of Java.");
                    System.exit(1);
                default:
                    System.out.println("Wrong choice, please try again or enter command help !");
            }
        }
    }

    private static void getTransactions(String[] commandArgs) {
        if (commandArgs.length != 4) {
            return;
        }
        try {
            Long accountId = Long.valueOf(commandArgs[1]);
            LocalDateTime after = LocalDateTime.parse(commandArgs[2], formatter1);
            LocalDateTime before = LocalDateTime.parse(commandArgs[3], formatter1);
            accountService.getTransactionsWithTime(accountId, after, before).forEach(
                    transaction -> System.out.println(transaction.toString())
            );
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void transfer(String[] commandArgs) {
        if (commandArgs.length != 4) {
            return;
        }
        try {
            Long senderId = Long.valueOf(commandArgs[1]);
            Long recipientId = Long.valueOf(commandArgs[2]);
            Double money = Double.valueOf(commandArgs[3]);
            if (money < 1) throw new IllegalArgumentException();
            accountService.transferMoney(senderId, recipientId, money);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void getBankAccounts(String[] commandArgs) {
        if (commandArgs.length != 2) {
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            Bank bank = bankService.fetchAccounts(id);

            System.out.println("Account's list of %s".formatted(bank.getName()));
            bank.getAccounts().forEach(
                    account -> System.out.println(account.toString())
            );
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void getClientsAccounts(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            Client client = clientService.getAccount(id);

            System.out.println("Account's list of %s".formatted(client.getName()));
            client.getAccounts().forEach(
                    account -> System.out.println(account.toString())
            );
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void getBank(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            Bank bank = bankService.getBank(id);
            if (bank != null)
                System.out.println(bank.toString());
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void getClient(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            Client client = clientService.getClient(id);
            if (client != null)
                System.out.println(client.toString());
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void getAccount(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            Account account = accountService.getAccount(id);
            if (account != null)
                System.out.println(account.toString());
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void addAccount(String[] commandArgs) {
        if (commandArgs.length != 6) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            String accountNumber = commandArgs[1];
            Double value = Double.valueOf(commandArgs[2]);
            Currency currency = Currency.valueOf(commandArgs[3].toUpperCase());
            Long userId = Long.valueOf(commandArgs[4]);
            Long bankId = Long.valueOf(commandArgs[5]);
            AccountDto dto = new AccountDto();
            dto.setAccountNumber(accountNumber);
            dto.setValue(value);
            dto.setCurrency(currency);
            accountService.createAccount(dto, userId, bankId);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void addBank(String[] commandArgs) {
        if (commandArgs.length != 4) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            String name = commandArgs[1];
            Double individualCommission = Double.valueOf(commandArgs[2]);
            Double legalCommission = Double.valueOf(commandArgs[3]);
            BankDto bankDto = new BankDto(name, individualCommission, legalCommission);
            bankService.createBank(bankDto);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void addClient(String[] commandArgs) {
        if (commandArgs.length != 3) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }

        try {

            String name = commandArgs[1];
            ClientsType type = ClientsType.valueOf(commandArgs[2].toUpperCase());
            ClientDto dto = new ClientDto(name, type);
            clientService.createClient(dto);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void deleteAccount(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            accountService.deleteAccount(id);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void deleteBank(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            bankService.deleteBank(id);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }

    private static void deleteClient(String[] commandArgs) {
        if (commandArgs.length != 2) {
            log.error(ILLEGAL_ARGUMENT);
            return;
        }
        try {
            Long id = Long.valueOf(commandArgs[1]);
            clientService.deleteClient(id);
        } catch (Exception e) {
            log.error(ILLEGAL_ARGUMENT);
        }
    }
}

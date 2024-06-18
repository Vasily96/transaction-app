# transaction-app
Необходимо реализовать приложение, которое позволяет управлять счетами пользователя в банке и выполнять транзакции по переводу средств.


Пользователь взаимодействует с приложением через консоль/терминал.
Формат команд: command param1 param2 ... paramN


Должна быть команда, которая выводит список команд с их описанием.


### Перечень функций приложения:
* crud операции по управлению банками
* crud операции по добавлению/управлению клиентами банка
* примечание: при добавлении клиента в банк, нужно открыть хотя бы один счет в этом банке
* клиенты банка могут переводить средства на счета других клиентов того же самого банка без комиссии
* клиенты банка могут переводить средства на счета клиентов других банков с фиксированной комиссией (комиссия указывается при добавлении банка)
* существуют 2 вида клиентов, физические и юридические, банки взимают разные комиссии для разных видов клиентов
* клиент может иметь счета в разных банках
* каждый счет имеет свою валюту, которая должна учитываться при переводах
* приложение должно позволять выводить счета клиента и кол-во средств на них
* приложение должно позволять выводить все транзакции, проведенные клиентом за указанный период
* данные можно хранить либо в json-файле (библиотека jackson) либо в базе данных (соответствующий jdbc драйвер)

### Команды для приложения:

* addAccount [accountNumber] [moneyValue] [currency] [clientId] [bankId]
* updateAccount [accountId] [accountNumber] [moneyValue] [currency] [clientId] [bankId]
* addClient [clientName] [clientType]
* updateClient [clientId] [clientName] [clientType]
* addBank [bankName] [individualCommission] [legalCommission]
* updateBank [bankId] [bankName] [individualCommission] [legalCommission]
* removeAccount [accountId]
* removeClient [clientId]
* removeBank [bankId]
* getAccount [accountId]
* getClient [clientId]
* getBank [bankId]
* getClientsAccounts [clientId]
* getBankAccounts [bankId]
* transfer [senderId] [recipientId] [money]
* getAccountTransactions [accountId] [after] [before]
* exit

### Примечания:

* Для простоты тестирования использовалась база данных dbc:h2:mem:testdb, которую можно открыть в браузере:
> http://localhost:9001/h2-console
* Также для простоты тестирования были добавлены данные из файла data.sql.
* В качестве валют были использованы: BYN, EUR, USD, RUB.
* Тип клиента: INDIVIDUAL, LEGAL.
* В качестве формата данных использовать следующий паттерн: dd-MM-yyyy_HH:mm:ss

### Примеры команд:

* addAccount DefaultAccount 4000 USD 1 1
* trasnfer 1 4 10000
* getAccountTransactions 2 01-01-2022_12:12:12 05-07-2024_10:10:10


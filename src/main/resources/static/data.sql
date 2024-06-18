INSERT INTO client(name,client_type) values ('Client1','INDIVIDUAL');
INSERT INTO client(name,client_type) values ('Client2','INDIVIDUAL');
INSERT INTO client(name,client_type) values ('Client3','INDIVIDUAL');
INSERT INTO client(name,client_type) values ('Company1','LEGAL');
INSERT INTO client(name,client_type) values ('Company2','LEGAL');

INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('USD','BYN',3.2);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('EUR','BYN',3.42);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('RUB','BYN',0.0358);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('BYN','USD',0.3125);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('EUR','USD',1.075);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('RUB','USD',0.011);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('BYN','EUR',0.292);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('USD','EUR',0.93);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('RUB','EUR',0.0104);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('BYN','RUB',27.82);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('USD','RUB',89.04);
INSERT INTO exchange(first_currency,second_currency,currency_rate) values ('EUR','RUB',95.39);

INSERT INTO bank(name,individual_commission,legal_commission) values ('AlfaBank',5,3);
INSERT INTO bank(name,individual_commission,legal_commission) values ('MTBank',4,3);

INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN1',50000,'BYN',1,1);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321USD2',50000,'USD',1,1);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321USD2',50000,'EUR',1,2);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN3',60000,'BYN',2,1);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN3',60000,'BYN',2,2);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN4',5000,'BYN',3,2);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN4',5000,'EUR',3,1);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN5',100000,'BYN',4,2);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN5',9876543,'RUB',4,1);
INSERT INTO account (account_number,account_value,currency_account,client_id,bank_id) values ('321BYN5',4444444,'RUB',5,2);

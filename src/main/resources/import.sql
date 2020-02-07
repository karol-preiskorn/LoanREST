--
-- import sample loans to HSQLDB
-- tips: datetime conversion: http://hsqldb.org/doc/guide/builtinfunctions-chapt.html#bfc_datetime_functions
--
insert into loans (Amount, CreatedAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (123456.01, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Cabacki', false, TIMESTAMP '2020-03-06 23:19:44', null);
insert into loans (Amount, CreatedAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (300000.02, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Abacki', false, TIMESTAMP '2020-03-06 23:19:44', null);
insert into loans (Amount, CreatedAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (600000.03, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Babacki', false, TIMESTAMP '2020-03-06 23:19:44', null);

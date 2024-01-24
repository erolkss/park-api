insert into users(id_user, username, password, role) values (100, 'ana@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_ADMIN');
insert into users(id_user, username, password, role) values (101, 'bia@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_CLIENT');
insert into users(id_user, username, password, role) values (102, 'bob@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx26', 'ROLE_CLIENT');

insert into clients(id, name, cpf, id_user) VALUES (21, 'Beatriz Rodrigues', '95536891081', 101);
insert into clients(id, name, cpf, id_user) VALUES (22, 'Rodrigo Silva', '44504393093', 102);

insert into parking_spot(id, code_parking_spot, status) values (100, 'A-01', 'BUSY');
insert into parking_spot(id, code_parking_spot, status) values (200, 'A-02', 'BUSY');
insert into parking_spot(id, code_parking_spot, status) values (300, 'A-03', 'BUSY');
insert into parking_spot(id, code_parking_spot, status) values (400, 'A-04', 'FREE');
insert into parking_spot(id, code_parking_spot, status) values (500, 'A-05', 'FREE');

insert into clients_parking_spot(number_receipt, plate, brand, model, color, entry_date, id_client, id_parking_spot) values ('20240118-205706', 'FIT-1020', 'FIAT', 'PALIO', 'GREEN', '2024-01-18 20:57:06', 21, 100);
insert into clients_parking_spot(number_receipt, plate, brand, model, color, entry_date, id_client, id_parking_spot) values ('20240119-205706', 'SIE-1020', 'HONDA', 'SIENA', 'WHITE', '2024-01-19 20:57:06', 22, 200);
insert into clients_parking_spot(number_receipt, plate, brand, model, color, entry_date, id_client, id_parking_spot) values ('20240120-205706', 'FIT-1020', 'FIAT', 'PALIO', 'GREEN', '2024-01-20 20:57:06', 22, 300);
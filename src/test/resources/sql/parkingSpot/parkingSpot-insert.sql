insert into users(id_user, username, password, role) values (100, 'ana@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_ADMIN');
insert into users(id_user, username, password, role) values (101, 'bia@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_CLIENT');
insert into users(id_user, username, password, role) values (102, 'bob@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx26', 'ROLE_CLIENT');

insert into parking_spot(id, code, status) values (10, 'A-01', 'FREE');
insert into parking_spot(id, code, status) values (20, 'A-02', 'FREE');
insert into parking_spot(id, code, status) values (30, 'A-03', 'BUSY');
insert into parking_spot(id, code, status) values (40, 'A-04', 'FREE');
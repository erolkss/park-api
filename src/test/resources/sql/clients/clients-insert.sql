insert into users(id_user, username, password, role) values (100, 'ana@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_ADMIN');
insert into users(id_user, username, password, role) values (101, 'bia@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_CLIENT');
insert into users(id_user, username, password, role) values (102, 'bob@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_CLIENT');
insert into users(id_user, username, password, role) values (103, 'toby@email.com', '$2a$10$GWq9ghBfi452Crgs5ja0QeLx9E47D0bdm7hOurFv1bcqFNF/Cosx2', 'ROLE_CLIENT');



insert into clients(id, name, cpf, id_user) VALUES (10, 'Bianca Silva', '95536891081', 101);
insert into clients(id, name, cpf, id_user) VALUES (20, 'Roberto Gomez', '44504393093', 102);
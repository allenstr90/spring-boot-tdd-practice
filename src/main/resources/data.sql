insert into authority (name)
values ('ADMIN');
insert into authority (name)
values ('USER');

insert into ecasa_user (username, email, password, is_active)
values ('admin', 'admin@mail.com', '$2a$10$UaoK5PUbtdIQftNNijs9FOLRpOAYFMKQuWgr/5W.nugQ.peYfzgQ.', true);

insert into user_authority (user_id, authority_name)
values (1, 'ADMIN');
insert into user_authority (user_id, authority_name)
values (1, 'USER');
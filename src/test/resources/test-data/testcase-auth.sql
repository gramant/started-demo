INSERT INTO users(id, email, enabled) VALUES('user-1', 'user1@mail.ru', true);

INSERT INTO authorities(user_id, role_id) VALUES('user-1', 'MANAGER');
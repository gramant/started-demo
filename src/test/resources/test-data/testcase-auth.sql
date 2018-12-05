INSERT INTO users(id, email, enabled, non_locked) VALUES('user-1', 'user1@mail.ru', true, true);

INSERT INTO authorities(user_id, role_id) VALUES('user-1', 'MANAGER');
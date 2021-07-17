INSERT INTO users (id, archive, email, name, password, role)
VALUES (1, false, 'mail@mail.ru', 'admin', '$2a$10$7ADr06ynxs9I0tF4IF.hdeHgGCqQOcu1kxFKJbjDaTAoBedxyQQae', 'ADMIN');

ALTER SEQUENCE user_seq RESTART WITH 2;
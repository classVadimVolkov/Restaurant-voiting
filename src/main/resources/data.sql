INSERT INTO USERS (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)
VALUES ('user@gmail.com', 'User_First', 'User_Last', '{noop}password'),
       ('admin@javaops.ru', 'Admin_First', 'Admin_Last', '{noop}admin');

INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, user_id)
VALUES ('Винтаж', 2),
       ('Bellagio', 2),
       ('Габрово', 2);

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Мясо по-французски', 550, 1),
       ('Картофель по-деревенски', 340, 1),
       ('Сырный суп', 380, 2),
       ('Драники со сметаной', 450, 2),
       ('Рассольник', 290, 3),
       ('Скумбрия запеченная', 530, 3);
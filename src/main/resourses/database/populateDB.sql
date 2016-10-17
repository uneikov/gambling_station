DELETE FROM horses;
DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM wallets;
DELETE FROM horses;
DELETE FROM stakes;

/*DELETE FROM stats;*/

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, registered)
VALUES
  ('User1', 'user1@yandex.ru', 'password1', '2016-10-1 10:00:00'),
  ('User2', 'user2@yandex.ru', 'password2', '2016-10-1 10:00:00');

INSERT INTO users (name, email, password, registered)
VALUES ('Admin', 'admin@gmail.com', 'admin', '2016-10-1 10:00:00');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002);

INSERT INTO horses (name, ru_name, age, wins) VALUES
  ('Black Ghost', 'Черный призрак', 5, 0),  -- HORSE_1
  ('White Ghost', 'Белый призрак', 5, 0),   -- HORSE_2
  ('Enisei', 'Енисей', 3, 0),               -- HORSE_3
  ('Thunderbird', 'Гром', 5, 0),            -- HORSE_4
  ('Ruby Rose', 'Рубироуз', 4, 0),          -- HORSE_5
  ('Predator', 'Хищник', 5, 0),             -- HORSE_6
  ('Alien', 'Чужой', 6, 0),                 -- HORSE_7
  ('Gulfstream', 'Гольфстрим', 3, 0),       -- HORSE_8
  ('Rabindranate', 'Рабиндранат', 5, 0),    -- HORSE_9
  ('Angelfire', 'Энджелфае', 5, 0);         -- HORSE_10

INSERT INTO STAKES (user_id, horse_id, stake_value, date_time, wins, amount) VALUES
  (100000, 100006, 100.25, '2016-05-30 10:00:00', TRUE , 0.0),
  (100001, 100007, 100.25, '2016-06-12 13:30:00', FALSE, 0.0),
  (100000, 100008, 100.25, '2016-06-13 19:45:00', FALSE, 0.0),
  (100001, 100006, 100.25, '2016-08-05 10:09:00', FALSE, 0.0),
  (100000, 100006, 100.25, '2016-08-05 10:10:00', FALSE, 0.0);

INSERT INTO wallets (user_id, cash_value) VALUES
  (100000, 10.0),
  (100001, 15.0),
  (100002, 0.0);

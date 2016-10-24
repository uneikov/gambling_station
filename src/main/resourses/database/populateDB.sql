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

INSERT INTO users (name, email, password, registered)
VALUES ('Station', 'station@gamblestation.com', 'stationpass', '2016-10-1 10:00:00');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_STATION', 100003);

INSERT INTO horses (name, ru_name, age, wins) VALUES
  ('Black Ghost', 'Черный призрак', 5, 0),  -- HORSE_1  100004
  ('White Ghost', 'Белый призрак', 5, 0),   -- HORSE_2  100005
  ('Enisei', 'Енисей', 3, 0),               -- HORSE_3  100006
  ('Thunderbird', 'Гром', 5, 0),            -- HORSE_4  100007
  ('Ruby Rose', 'Рубироуз', 4, 0),          -- HORSE_5  100008
  ('Predator', 'Хищник', 5, 0),             -- HORSE_6  100009
  ('Alien', 'Чужой', 6, 0),                 -- HORSE_7  100010
  ('Gulfstream', 'Гольфстрим', 3, 0),       -- HORSE_8  100011
  ('Rabindranate', 'Рабиндранат', 5, 0),    -- HORSE_9  100012
  ('Angelfire', 'Энджелфае', 5, 0);         -- HORSE_10 100013

INSERT INTO stakes (user_id, horse_id, stake_value, date_time, wins, amount) VALUES
  (100000, 100007, 100.25, '2016-05-30 10:00:00', TRUE , 0.0),
  (100001, 100008, 100.25, '2016-06-12 13:30:00', FALSE, 0.0),
  (100000, 100009, 100.25, '2016-06-13 19:45:00', FALSE, 0.0),
  (100001, 100007, 100.25, '2016-08-05 10:09:00', FALSE, 0.0),
  (100000, 100007, 100.25, '2016-08-05 10:10:00', FALSE, 0.0);

INSERT INTO wallets (user_id, cash_value) VALUES
  (100000, 10.0),
  (100001, 15.0),
  (100002, 0.0),
  (100003, 0.0);

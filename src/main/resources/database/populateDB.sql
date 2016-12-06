DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM wallets;
DELETE FROM horses;
DELETE FROM stakes;
DELETE FROM races;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, registered)
VALUES
  ('User1', 'user1@yandex.ru', '$2a$10$Gk4kXU24ryWq66oKA0f8AeaguhyMNx7.pR2x/euooqK0NG.IyK1jC', '2016-10-1 10:00:00'),
  ('User2', 'user2@yandex.ru', '$2a$10$3Om1jqFdbcLtir5XsQbS2uiBG/7kv2twth/3BGg1jPTjbqgaqplw.', '2016-10-1 10:00:00');
-- pass::password1 password2
INSERT INTO users (name, email, password, registered)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$WejOLxVuXRpOgr4IlzQJ.eT4UcukNqHlAiOVZj1P/nmc8WbpMkiju', '2016-10-1 10:00:00');
-- pass:admin
INSERT INTO users (name, email, password, registered)
VALUES ('Station', 'station@gamblingstation.com', '$2a$10$4BB/oW0v.c54SFiRB47gue22MjbPnG88iSRSo//uESs1T1NMrnpUK', '2016-10-1 10:00:00');
-- pass:stationpass
INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_ADMIN', 100002),
  ('ROLE_STATION', 100003);

INSERT INTO horses (name, ru_name, age, wins, ready) VALUES
  ('Black Ghost', 'Черный призрак', 5, 0, TRUE ),  -- HORSE_1  100004
  ('White Ghost', 'Белый призрак', 5, 0, TRUE ),   -- HORSE_2  100005
  ('Enisei', 'Енисей', 3, 0, FALSE ),              -- HORSE_3  100006
  ('Thunderbird', 'Гром', 5, 0, TRUE ),            -- HORSE_4  100007
  ('Ruby Rose', 'Рубироуз', 4, 0, TRUE ),          -- HORSE_5  100008
  ('Predator', 'Хищник', 5, 0, TRUE ),             -- HORSE_6  100009
  ('Alien', 'Чужой', 6, 0, TRUE ),                 -- HORSE_7  100010
  ('Gulfstream', 'Гольфстрим', 3, 0, FALSE ),      -- HORSE_8  100011
  ('Rabindranate', 'Рабиндранат', 5, 0, FALSE ),   -- HORSE_9  100012
  ('Angelfire', 'Энджелфае', 5, 0, FALSE );        -- HORSE_10 100013



INSERT INTO wallets (user_id, cash_value) VALUES
  (100000, 10.0),
  (100001, 15.0),
  (100002, 0.0),
  (100003, 200.5);

INSERT INTO races (start, finish, horses, winning) VALUES
  (
    '2016-05-30 10:00:00',
    '2016-05-30 10:45:00',
    'Alien:Чужой,Black Ghost:Черный призрак,White Ghost:Белый призрак,Enisei:Енисей,Thunderbird:Гром,Ruby Rose:Рубироуз',
    'Ghost:Черный призрак'
  ), -- 100014
  (
    '2016-06-12 13:00:00',
    '2016-06-12 13:45:00',
    'Alien:Чужой,Black Ghost:Черный призрак,White Ghost:Белый призрак,Enisei:Енисей,Thunderbird:Гром,Ruby Rose:Рубироуз',
    'Thunderbird:Гром'
  ), -- 100015
  (
    '2016-06-13 19:00:00',
    '2016-06-13 19:45:00',
    'Predator:Хищник,Gulfstream:Гольфстрим,Rabindranate:Рабиндранат,Ruby Rose:Рубироуз,White Ghost:Белый призрак,Angelfire:Энджелфае',
    'Predator:Хищник'
  ), -- 100016
  (
    '2016-08-05 10:00:00',
    '2016-08-05 10:45:00',
    'Predator:Хищник,Gulfstream:Гольфстрим,Rabindranate:Рабиндранат,Ruby Rose:Рубироуз,White Ghost:Белый призрак,Angelfire:Энджелфае',
    'Ruby Rose:Рубироуз'
  ); -- 100017

INSERT INTO stakes (user_id, horse_id, race_id, stake_value, date_time, wins, amount, editable) VALUES
  (100000, 100007, 100014, 100.25, '2016-05-30 10:00:00', TRUE, 10.0, false),  -- 100018
  (100001, 100008, 100015, 100.25, '2016-06-12 13:30:00', FALSE, 0.0, false),  -- 100019
  (100000, 100009, 100016, 100.25, '2016-06-13 19:45:00', FALSE, 0.0, false),  -- 100020
  (100001, 100007, 100017, 100.25, '2016-08-05 10:09:00', FALSE, 0.0, false),  -- 100021 : change
  (100000, 100007, 100017, 100.25, '2016-08-05 10:10:00', FALSE, 0.0, false);  -- 100022
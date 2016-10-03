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
  ('Black Ghost', 'Черный призрак', 5, 0),
  ('White Ghost', 'Белый призрак', 5, 0),
  ('Enisei', 'Енисей', 3, 0),
  ('Thunderbird', 'Гром', 5, 0),
  ('Ruby Rose', 'Рубироуз', 4, 0),
  ('Predator', 'Хищник', 5, 0),
  ('Alien', 'Чужой', 6, 0),
  ('Gulfstream', 'Гольфстрим', 3, 0),
  ('Rabindranate', 'Рабиндранат', 5, 0),
  ('Angelfire', 'Энджелфае', 5, 0);

/*INSERT INTO STAKES (user_id, horse_id, stake_value, date_time, wins) VALUES
  (100000, 100006, 100.25, '2016-05-30 10:00:00', FALSE),
  (100001, 100007, 100.25, '2016-06-12 13:30:00', FALSE),
  (100000, 100008, 100.25, '2016-06-13 19:45:00', FALSE),
  (100001, 100006, 100.25, '2016-07-21  9:15:00', FALSE),
  (100000, 100006, 100.25, '2016-08-5  10:10:00', FALSE);*/

INSERT INTO wallets (user_id, cash_value) VALUES
  (100000, 10),
  (100001, 10);
/*
public static final Stake STAKE_1 = new Stake(100.25, of(2016, Month.MAY, 30, 10, 0), false);
    public static final Stake STAKE_2 = new Stake(100.25, of(2016, Month.JUNE, 12, 13, 30), false);
    public static final Stake STAKE_3 = new Stake(100.25, of(2016, Month.JUNE, 13, 19, 45), false);
    public static final Stake STAKE_4 = new Stake(100.25, of(2016, Month.JULY, 21, 9, 15), false);
    public static final Stake STAKE_5 = new Stake(100.25, of(2016, Month.AUGUST, 5, 10, 10), false);
*/
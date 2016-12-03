DROP TABLE IF EXISTS stakes;
DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS wallets;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS horses;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

-----------------------------------------------------------------
CREATE TABLE horses
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL,
  ru_name    VARCHAR NOT NULL,
  age        INTEGER NOT NULL,
  wins       INTEGER NOT NULL,
  ready      BOOLEAN DEFAULT FALSE
);
CREATE UNIQUE INDEX horses_unique_name_idx ON horses (name);
-----------------------------------------------------------------
CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR NOT NULL,
  email      VARCHAR NOT NULL,
  password   VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  enabled    BOOL DEFAULT TRUE
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);
-----------------------------------------------------------------
CREATE TABLE user_roles
(
  user_id    INTEGER NOT NULL,
  role       VARCHAR ,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
-----------------------------------------------------------------
CREATE TABLE wallets
(
  user_id     INTEGER NOT NULL,
  cash_value  DOUBLE PRECISION NOT NULL,
  CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX wallets_unique_user_id_idx ON wallets (user_id);
-----------------------------------------------------------------
CREATE TABLE races
(
  id        INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  start     TIMESTAMP NOT NULL,
  finish    TIMESTAMP ,
  horses    TEXT NOT NULL,
  winning   VARCHAR
);
CREATE UNIQUE INDEX races_unique_id_idx ON races (id);
-----------------------------------------------------------------
CREATE TABLE stakes
(
  id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  user_id      INTEGER NOT NULL ,
  horse_id     INTEGER NOT NULL ,
  race_id      INTEGER NOT NULL ,
  stake_value  DOUBLE PRECISION NOT NULL ,
  date_time    TIMESTAMP DEFAULT now(),
  wins         BOOLEAN DEFAULT FALSE ,
  amount       DOUBLE PRECISION NOT NULL ,
  editable     BOOL DEFAULT TRUE ,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (horse_id) REFERENCES horses (id) ON DELETE CASCADE,
  FOREIGN KEY (race_id) REFERENCES races (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX stakes_unique_user_id_idx ON stakes (user_id, date_time);
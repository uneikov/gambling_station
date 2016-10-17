DROP TABLE stakes IF EXISTS;
DROP TABLE wallets IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE horses IF EXISTS;

/*DROP TABLE stats IF EXISTS;*/

DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;
-----------------------------------------------------------------
CREATE TABLE horses
(
  id         INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  ru_name    VARCHAR(255) NOT NULL,
  age        INTEGER NOT NULL,
  wins       INTEGER NOT NULL
);
CREATE UNIQUE INDEX horses_unique_name_idx ON horses (name);
-----------------------------------------------------------------
CREATE TABLE users
(
  id         INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  name       VARCHAR(255) NOT NULL,
  email      VARCHAR(255) NOT NULL,
  password   VARCHAR(255) NOT NULL,
  registered TIMESTAMP DEFAULT now()
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);
-----------------------------------------------------------------
CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
-----------------------------------------------------------------

CREATE TABLE wallets
(
  /*id          INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,*/
  user_id     INTEGER NOT NULL,
  cash_value  DOUBLE NOT NULL,
  CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX wallets_unique_user_id_idx ON wallets (user_id);

-----------------------------------------------------------------
CREATE TABLE stakes
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  user_id     INTEGER NOT NULL ,
  horse_id    INTEGER NOT NULL ,
  stake_value DOUBLE NOT NULL ,
  date_time   TIMESTAMP ,
  wins        BOOLEAN DEFAULT FALSE ,
  amount      DOUBLE NOT NULL ,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (horse_id) REFERENCES horses (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX stakes_unique_user_id_idx ON stakes (user_id, date_time);
-----------------------------------------------------------------

/*CREATE TABLE stats
(
  id        INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  user_id   INTEGER NOT NULL,
  horse_id  INTEGER NOT NULL,
  date_time TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (horse_id) REFERENCES horses (id) ON DELETE CASCADE
);*/
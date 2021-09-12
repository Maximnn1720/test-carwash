create table if not exists orders
(
  id            INT UNIQUE NOT NULL,
  time_execute  TIMESTAMP  NOT NULL,
  id_user       INT        NOT NULL,
  time_creation TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (id_user) REFERENCES users(id)
);



create table if not exists users
(
  id                INT          NOT NULL,
  user_name         VARCHAR(100) NOT NULL,
  e_mail            VARCHAR(100) NOT NULL,
  password          VARCHAR(100) NOT NULL,
  is_admin          INT default 0,
  time_registration TIMESTAMP,
  PRIMARY KEY (id)
);



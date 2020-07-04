CREATE TABLE stocks (
  id integer PRIMARY KEY,
  name varchar(256) NOT NULL,
  quantity decimal(10,2) NOT NULL DEFAULT 0.0
);

CREATE TABLE documents (
  id integer PRIMARY KEY,
  quantity decimal(10,2) NOT NULL DEFAULT 0.0,
  processed boolean NOT NULL DEFAULT false,
  stock integer REFERENCES stocks
);

INSERT INTO stocks (id, name, quantity) VALUES
(1, 'сыр', 56.4),
(2, 'молоко', 26.8);

INSERT INTO documents (id, quantity, stock) VALUES
(1, 15.6, 1),
(2, 26.1, 1);
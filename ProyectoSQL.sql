DROP DATABASE IF EXISTS proyecto;
CREATE DATABASE proyecto;
USE proyecto;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS editorials;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS ratings;
DROP TABLE IF EXISTS messages;

CREATE TABLE users (
id_user int AUTO_INCREMENT PRIMARY KEY,
username varchar(50) DEFAULT NULL,
profile_image LONGBLOB DEFAULT NULL,
email varchar(50) UNIQUE DEFAULT NULL,
pass varchar(255) DEFAULT NULL,
role_id VARCHAR(50) DEFAULT 'ROLE_USER'
);

CREATE TABLE editorials (
id_editorial int AUTO_INCREMENT PRIMARY KEY,
name_editorial varchar(250) UNIQUE DEFAULT NULL
);

CREATE TABLE books (
id_book int AUTO_INCREMENT PRIMARY KEY,
author varchar(250) DEFAULT NULL,
title varchar(250) DEFAULT NULL,
isbn varchar(20) DEFAULT NULL,
category VARCHAR(50) DEFAULT NULL,
cover_image LONGBLOB DEFAULT NULL,
aviable BOOLEAN DEFAULT true,
description VARCHAR(255) DEFAULT NULL,
id_user int DEFAULT NULL,
CONSTRAINT user_FK FOREIGN KEY (id_user) REFERENCES users (id_user),
id_editorial int DEFAULT NULL,
CONSTRAINT editorial_FK FOREIGN KEY (id_editorial) REFERENCES editorials (id_editorial)
);

CREATE TABLE ratings (
id_rating int AUTO_INCREMENT PRIMARY KEY,
id_user_rating int,
CONSTRAINT id_user_fk FOREIGN KEY (id_user_rating) REFERENCES users (id_user),
id_book_rating int,
CONSTRAINT id_book_fk FOREIGN KEY (id_book_rating) REFERENCES books (id_book),
rating int,
coment VARCHAR(255) DEFAULT NULL
);

CREATE TABLE messages (
id_message int AUTO_INCREMENT PRIMARY KEY,
id_user_origin int DEFAULT NULL,
CONSTRAINT user_FK1 FOREIGN KEY (id_user_origin) REFERENCES users (id_user),
id_user_destiny int DEFAULT NULL,
CONSTRAINT user_FK2 FOREIGN KEY (id_user_destiny) REFERENCES users (id_user),
id_book int DEFAULT NULL,
CONSTRAINT book_FK FOREIGN KEY (id_book) REFERENCES books (id_book),
content varchar(250),
sent_date DATETIME
);

INSERT INTO users (username, email, pass, role_id) VALUES ('admin', 'admin@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_ADMIN');
INSERT INTO users (username, email, pass, role_id) VALUES ('juan123', 'juan@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_USER');
INSERT INTO users (username, email, pass, role_id) VALUES ('maria456', 'maria@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_USER');
INSERT INTO users (username, email, pass, role_id) VALUES ('pedro789', 'pedro@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_USER');
INSERT INTO users (username, email, pass, role_id) VALUES ('laura101', 'laura@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_USER');
INSERT INTO users (username, email, pass, role_id) VALUES ('carlos2022', 'carlos@example.com', '$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS', 'ROLE_USER');

INSERT INTO editorials (name_editorial) VALUES ('Editorial Alfaguara');
INSERT INTO editorials (name_editorial) VALUES ('Editorial Planeta');
INSERT INTO editorials (name_editorial) VALUES ('Editorial Tusquets');
INSERT INTO editorials (name_editorial) VALUES ('Editorial Anagrama');
INSERT INTO editorials (name_editorial) VALUES ('Editorial Destino');

INSERT INTO books (author, title, isbn, category, description, id_user, id_editorial) 
VALUES ('Gabriel Garcia Marquez', 'Cien años de soledad', '9789877403399', 'Novela', 'Descripcion libro id 1', 1, 2);
INSERT INTO books (author, title, isbn, category, description, id_user, id_editorial) 
VALUES ('Isabel Allende', 'La casa de los espíritus', '9788496583589', 'Novela', 'Descripcion libro id 2', 2, 1);
INSERT INTO books (author, title, isbn, category, description, id_user, id_editorial) 
VALUES ('Mario Vargas Llosa', 'La fiesta del chivo', '9788483465821', 'Novela', 'Descripcion libro id 3', 3, 3);
INSERT INTO books (author, title, isbn, category, description, id_user, id_editorial) 
VALUES ('Julio Cortázar', 'Rayuela', '9789871220726', 'Novela', 'Descripcion libro id 4', 4, 4);
INSERT INTO books (author, title, isbn, category, description, id_user, id_editorial) 
VALUES ('Jorge Luis Borges', 'Ficciones', '9789877381085', 'Cuento', 'Descripcion libro id 5', 5, 5);

INSERT INTO ratings (id_user_rating, id_book_rating, rating)
VALUES(1, 2, 5);
INSERT INTO ratings (id_user_rating, id_book_rating, rating)
VALUES(2, 1, 5);
INSERT INTO ratings (id_user_rating, id_book_rating, rating)
VALUES(3, 3, 4);
INSERT INTO ratings (id_user_rating, id_book_rating, rating)
VALUES(4, 4, 3);

INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (1, 2, 1, 'Hola, ¿qué tal?', '2023-04-28 09:00:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (2, 1, 3, '¿Qué opinas de este libro?', '2023-04-28 10:30:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (3, 1, 2, 'Te recomiendo este libro', '2023-04-28 11:45:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (4, 5, 2, 'Me flipa tu libro', '2023-04-28 16:55:00');

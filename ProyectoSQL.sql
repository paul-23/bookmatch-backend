DROP DATABASE IF EXISTS proyecto;
CREATE DATABASE proyecto;
USE proyecto;

CREATE TABLE users (
id_user int AUTO_INCREMENT PRIMARY KEY,
username varchar(50) DEFAULT NULL,
email varchar(50) DEFAULT NULL,
pass varchar(50) DEFAULT NULL
);

CREATE TABLE editorial (
id_editorial int AUTO_INCREMENT PRIMARY KEY,
name_editorial varchar(250) DEFAULT NULL
);

CREATE TABLE books (
id_book int AUTO_INCREMENT PRIMARY KEY,
author varchar(250) DEFAULT NULL,
title varchar(250) DEFAULT NULL,
isbn varchar(20) DEFAULT NULL,
rating int DEFAULT NULL,
category VARCHAR(50),
id_user int DEFAULT NULL,
CONSTRAINT user_FK FOREIGN KEY (id_user) REFERENCES users (id_user),
id_editorial int DEFAULT NULL,
CONSTRAINT editorial_FK FOREIGN KEY (id_editorial) REFERENCES editorial (id_editorial)
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

INSERT INTO users (username, email, pass) VALUES ('juan123', 'juan@example.com', 'pass123');
INSERT INTO users (username, email, pass) VALUES ('maria456', 'maria@example.com', 'pass456');
INSERT INTO users (username, email, pass) VALUES ('pedro789', 'pedro@example.com', 'pass789');
INSERT INTO users (username, email, pass) VALUES ('laura101', 'laura@example.com', 'pass101');
INSERT INTO users (username, email, pass) VALUES ('carlos2022', 'carlos@example.com', 'pass2022');

INSERT INTO editorial (name_editorial) VALUES ('Editorial Alfaguara');
INSERT INTO editorial (name_editorial) VALUES ('Editorial Planeta');
INSERT INTO editorial (name_editorial) VALUES ('Editorial Tusquets');
INSERT INTO editorial (name_editorial) VALUES ('Editorial Anagrama');
INSERT INTO editorial (name_editorial) VALUES ('Editorial Destino');

INSERT INTO books (author, title, isbn, rating, category, id_user, id_editorial) 
VALUES ('Gabriel Garcia Marquez', 'Cien años de soledad', '978-987-740-339-9', 5, 'Novela', 1, 2);
INSERT INTO books (author, title, isbn, rating, category, id_user, id_editorial) 
VALUES ('Isabel Allende', 'La casa de los espíritus', '978-84-9658-358-9', 4, 'Novela', 2, 1);
INSERT INTO books (author, title, isbn, rating, category, id_user, id_editorial) 
VALUES ('Mario Vargas Llosa', 'La fiesta del chivo', '978-84-8346-582-1', 4, 'Novela', 3, 3);
INSERT INTO books (author, title, isbn, rating, category, id_user, id_editorial) 
VALUES ('Julio Cortázar', 'Rayuela', '978-987-1220-72-6', 5, 'Novela', 4, 4);
INSERT INTO books (author, title, isbn, rating, category, id_user, id_editorial) 
VALUES ('Jorge Luis Borges', 'Ficciones', '978-987-738-108-5', 4, 'Cuento', 5, 5);

INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (1, 2, 1, 'Hola, ¿qué tal?', '2023-04-28 09:00:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (2, 1, 3, '¿Qué opinas de este libro?', '2023-04-28 10:30:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (3, 1, 2, 'Te recomiendo este libro', '2023-04-28 11:45:00');
INSERT INTO messages (id_user_origin, id_user_destiny, id_book, content, sent_date) 
VALUES (4, 5,

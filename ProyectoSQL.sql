DROP DATABASE IF EXISTS proyecto;
CREATE DATABASE proyecto;
USE proyecto;

CREATE TABLE users (
id_user int AUTO_INCREMENT PRIMARY KEY,
username varchar(50) DEFAULT NULL,
pass varchar(50) DEFAULT NULL,
email varchar(50) DEFAULT NULL
);

CREATE TABLE editorial (
id_editorial int AUTO_INCREMENT PRIMARY KEY,
name_editorial varchar(250) DEFAULT NULL
);

CREATE TABLE books (
id_book int AUTO_INCREMENT PRIMARY KEY,
title varchar(250) DEFAULT NULL,
author varchar(250) DEFAULT NULL,
ISBN varchar(20) DEFAULT NULL,
rating int DEFAULT NULL,
id_user int DEFAULT NULL,
CONSTRAINT user_FK FOREIGN KEY (id_user) REFERENCES users (id_user),
id_editorial int DEFAULT NULL,
CONSTRAINT editorial_FK FOREIGN KEY (id_editorial) REFERENCES editorial (id_editorial)
);

CREATE TABLE messages (
id_Message int AUTO_INCREMENT PRIMARY KEY,
content varchar(250),
id_user_origin int DEFAULT NULL,
CONSTRAINT user_FK1 FOREIGN KEY (id_user_origin) REFERENCES users (id_user),
id_user_destiny int DEFAULT NULL,
CONSTRAINT user_FK2 FOREIGN KEY (id_user_destiny) REFERENCES users (id_user),
id_book int DEFAULT NULL,
CONSTRAINT book_FK FOREIGN KEY (id_book) REFERENCES books (id_book)
);

INSERT INTO users (username, pass, email) VALUES ('user1', 'pass1', 'user1@gmail.com');
INSERT INTO users (username, pass, email) VALUES ('user2', 'pass2', 'user2@gmail.com');
INSERT INTO users (username, pass, email) VALUES ('user3', 'pass3', 'user3@gmail.com');

INSERT INTO editorial (id_editorial) VALUES (1);
INSERT INTO editorial (id_editorial) VALUES (2);
INSERT INTO editorial (id_editorial) VALUES (3);

INSERT INTO books (title, author, ISBN, rating, id_user, id_editorial) VALUES ('title1', 'autor1', '11122233A',4 ,1, 1);
INSERT INTO books (title, author, ISBN, rating, id_user, id_editorial) VALUES ('title2', 'autor2', '33344455B', 5,2, 2);
INSERT INTO books (title, author, ISBN, rating, id_user, id_editorial) VALUES ('title3', 'autor3', '55566677C', 7, 3, 3);

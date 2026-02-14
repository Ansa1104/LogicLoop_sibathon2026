DROP DATABASE IF EXISTS student_management_database;

CREATE DATABASE student_management_database;
USE student_management_database;

CREATE TABLE students (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL
);

INSERT INTO students VALUES
('1','Ali','ali@gmail.com','Computer Science'),
('2','Sara','sara@gmail.com','Software Engineering');

SELECT * FROM students;
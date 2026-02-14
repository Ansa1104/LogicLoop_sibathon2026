CREATE DATABASE smart_student_management;
USE smart_student_management;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) DEFAULT 'admin'
);
INSERT INTO users (username, password, role) VALUES
('hina', 'hina123', 'admin'),
('ali', 'ali456', 'teacher'),
('aslam', 'aslam789', 'staff');
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    roll_no VARCHAR(20) UNIQUE NOT NULL,
    department VARCHAR(50),
    semester INT,
    email VARCHAR(100),
    phone VARCHAR(15)
);
INSERT INTO students (name, roll_no, department, semester, email, phone)
VALUES
('Fatima', 'CS101', 'Computer Science', 2, 'fatima@example.com', '03001234567'),
('Noor', 'EE102', 'Electrical Engineering', 3, 'noor@example.com', '03007654321'),
('Sooraj', 'ME103', 'Mechanical Engineering', 4, 'sooraj@example.com', '03009876543'),
('Sajad', 'CS104', 'Computer Science', 1, 'sajad@example.com', '03003456789');
CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    total_classes INT NOT NULL,
    attended_classes INT NOT NULL,
    attendance_percentage DOUBLE,
    FOREIGN KEY (student_id) REFERENCES students(id)
        ON DELETE CASCADE
);
INSERT INTO attendance (student_id, total_classes, attended_classes, attendance_percentage)
VALUES
(1, 40, 36, (36/40)*100),
(2, 40, 32, (32/40)*100),
(3, 40, 38, (38/40)*100),
(4, 40, 30, (30/40)*100);
CREATE TABLE marks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    subject VARCHAR(50),
    marks_obtained INT,
    grade VARCHAR(5),
    FOREIGN KEY (student_id) REFERENCES students(id)
        ON DELETE CASCADE
);
INSERT INTO marks (student_id, subject, marks_obtained, grade)
VALUES
-- Fatima
(1, 'Mathematics', 85, 'A'),
(1, 'Physics', 78, 'B+'),
(1, 'Chemistry', 82, 'A-'),
(1, 'English', 90, 'A+'),
(1, 'Computer Science', 88, 'A'),

-- Noor
(2, 'Mathematics', 92, 'A+'),
(2, 'Physics', 88, 'A'),
(2, 'Chemistry', 85, 'A'),
(2, 'English', 80, 'B+'),
(2, 'Computer Science', 95, 'A+'),

-- Sooraj
(3, 'Mathematics', 76, 'B'),
(3, 'Physics', 81, 'B+'),
(3, 'Chemistry', 79, 'B+'),
(3, 'English', 74, 'C+'),
(3, 'Computer Science', 83, 'A-'),

-- Sajad
(4, 'Mathematics', 69, 'C+'),
(4, 'Physics', 74, 'B'),
(4, 'Chemistry', 71, 'B-'),
(4, 'English', 77, 'B'),
(4, 'Computer Science', 80, 'B+');

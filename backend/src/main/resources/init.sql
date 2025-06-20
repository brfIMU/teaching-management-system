-- 创建数据库
CREATE DATABASE IF NOT EXISTS teaching_management;
USE teaching_management;

-- 创建课程表
CREATE TABLE IF NOT EXISTS courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL,
    teacher VARCHAR(50) NOT NULL,
    credit INT NOT NULL,
    description TEXT
);

-- 创建学生表
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    major VARCHAR(50) NOT NULL,
    enrollment_year INT NOT NULL
);

-- 创建作业表
CREATE TABLE IF NOT EXISTS assignments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    deadline DATETIME NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- 创建成绩表
CREATE TABLE IF NOT EXISTS grades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    assignment_id INT,
    score DECIMAL(5,2),
    comment TEXT,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (assignment_id) REFERENCES assignments(id)
);

-- 创建请销假表
CREATE TABLE IF NOT EXISTS leaves (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason TEXT NOT NULL,
    status ENUM('pending', 'approved', 'rejected') NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

-- 插入初始数据
INSERT INTO courses (course_name, teacher, credit, description) VALUES
('Java编程', '张教授', 4, 'Java语言基础与高级编程'),
('数据结构', '李教授', 3, '常见数据结构与算法'),
('数据库系统', '王教授', 3, '数据库设计与管理'),
('计算机网络', '赵教授', 3, '计算机网络原理与应用');

INSERT INTO students (student_id, name, gender, major, enrollment_year) VALUES
('2023001', '张三', '男', '计算机科学与技术', 2023),
('2023002', '李四', '女', '软件工程', 2023),
('2023003', '王五', '男', '数据科学与大数据技术', 2023);

INSERT INTO assignments (course_id, title, content, deadline) VALUES
(1, 'Java基础作业', '完成教材第1-3章习题', '2023-09-30 23:59:59'),
(2, '链表实现', '实现链表的增删改查操作', '2023-10-15 23:59:59'),
(3, 'SQL查询练习', '完成数据库基本查询操作', '2023-10-10 23:59:59');

INSERT INTO grades (student_id, course_id, assignment_id, score, comment) VALUES
(1, 1, 1, 90.5, '完成度高，代码规范'),
(2, 1, 1, 85.0, '基本完成，有小错误'),
(1, 2, 2, 92.0, '实现完整，效率较高');

INSERT INTO leaves (student_id, start_date, end_date, reason, status) VALUES
(1, '2023-09-10', '2023-09-12', '生病住院', 'approved'),
(2, '2023-09-15', '2023-09-16', '家中有事', 'pending');    
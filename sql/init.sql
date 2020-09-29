-- -------------------------------------------------------------
-- TablePlus 3.9.1(342)
--
-- https://tableplus.com/
--
-- Database: demoSpringSecurity
-- Generation Time: 2020-09-30 01:12:06.2720
-- -------------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE TABLE `department` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1t68827l97cwyxo9r1u6t4p7d` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `birthday` date NOT NULL,
  `email` varchar(200) NOT NULL,
  `fullname` varchar(250) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `salary` double DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6djefmjx26crewgafuih51sad` (`fullname`),
  KEY `FKbejtwvg9bxus2mffsm3swj3u9` (`department_id`),
  CONSTRAINT `FKbejtwvg9bxus2mffsm3swj3u9` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `refresh_token` (
  `token_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `expiry_dt` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `UK_r4k4edos30bx9neoq81mdvwph` (`token`),
  UNIQUE KEY `UK_ga3cqp73wyumau3ghjfx7vq94` (`token`),
  UNIQUE KEY `UK_f95ixxe7pa48ryn1awmh2evt7` (`user_id`),
  CONSTRAINT `FKfgk1klcib7i15utalmcqo7krt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(200) NOT NULL,
  `fullname` varchar(250) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `department` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `description`, `name`) VALUES
('1', '2020-09-29 17:07:42.693000', NULL, b'1', NULL, NULL, 'this is department 01', 'department 01'),
('2', '2020-09-29 17:10:53.833000', NULL, b'1', NULL, NULL, 'this is department 02', 'department 02'),
('3', '2020-09-29 17:11:01.054000', NULL, b'1', NULL, NULL, 'this is department 03', 'department 03'),
('4', '2020-09-29 17:11:07.942000', NULL, b'1', NULL, NULL, 'this is department 04', 'department 04'),
('5', '2020-09-29 17:11:15.315000', NULL, b'1', NULL, NULL, 'this is department 05', 'department 05'),
('6', '2020-09-29 17:11:21.753000', NULL, b'1', NULL, NULL, 'this is department 06', 'department 06'),
('7', '2020-09-29 17:11:28.560000', NULL, b'1', NULL, NULL, 'this is department 07', 'department 07'),
('8', '2020-09-29 17:11:35.036000', NULL, b'1', NULL, NULL, 'this is department 08', 'department 08'),
('9', '2020-09-29 17:11:40.988000', NULL, b'1', NULL, NULL, 'this is department 09', 'department 09'),
('10', '2020-09-29 17:11:48.426000', NULL, b'1', NULL, NULL, 'this is department 010', 'department 010'),
('11', '2020-09-29 17:11:57.085000', NULL, b'0', '2020-09-29 17:13:27.831000', NULL, 'this is department 021', 'department 021');

INSERT INTO `employee` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `avatar`, `birthday`, `email`, `fullname`, `gender`, `phone`, `salary`, `department_id`) VALUES
('1', '2020-09-29 17:17:02.667000', NULL, b'1', NULL, NULL, 'img01.png', '2000-08-18', 'employee01@gmail.com', 'employee01', 'OTHER', '09392155555', '2023132131', '1'),
('2', '2020-09-29 17:17:45.684000', NULL, b'1', NULL, NULL, 'img02.png', '2000-05-18', 'employee01@gmail.com', 'employee02', 'MALE', '09392155330', '10000', '2'),
('3', '2020-09-29 17:18:11.380000', NULL, b'1', NULL, NULL, 'img03.png', '2001-05-18', 'employee03@gmail.com', 'employee03', 'MALE', '09392130222', '2000', '2'),
('4', '2020-09-29 17:19:14.522000', NULL, b'1', NULL, NULL, 'img04.png', '2001-05-03', 'employee03@gmail.com', 'employee04', 'MALE', '0939213221', '2000000', '4'),
('5', '2020-09-29 17:19:41.644000', NULL, b'1', NULL, NULL, 'img05.png', '2001-05-03', 'employee05@gmail.com', 'employee05', 'MALE', '0939213177', '1200000', '7'),
('6', '2020-09-29 17:20:03.623000', NULL, b'1', NULL, NULL, 'img06.png', '2001-05-03', 'employee06@gmail.com', 'employee06', 'MALE', '0939213177', '1200000', '5'),
('7', '2020-09-29 17:20:19.096000', NULL, b'0', '2020-09-29 17:21:35.131000', NULL, 'img077.png', '2001-05-07', 'employee077@gmail.com', 'employee077', 'MALE', '093921777', '1200001', '7');

INSERT INTO `refresh_token` (`token_id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `expiry_dt`, `token`, `user_id`) VALUES
('1', '2020-09-29 17:08:41.002000', NULL, b'1', NULL, NULL, '2020-10-29 17:08:40.979000', 'd5ff84e1-5a76-41b7-846d-4d85440095e4', '1'),
('4', '2020-09-29 17:38:25.722000', NULL, b'1', NULL, NULL, '2020-10-29 17:38:25.706000', '9fcd99c4-0281-4247-9bbe-63355b50f3cf', '2');

INSERT INTO `role` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `name`) VALUES
('1', '2020-09-29 16:46:21.453000', NULL, b'1', NULL, NULL, 'ADMIN'),
('2', '2020-09-29 16:46:29.445000', NULL, b'1', NULL, NULL, 'USER');

INSERT INTO `user` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `avatar`, `email`, `fullname`, `password`, `phone`, `username`) VALUES
('1', '2020-09-29 17:05:44.037000', NULL, b'1', NULL, NULL, 'https://thumbs-prod.si-cdn.com/nnJARGtKrLypH4y3Vov2zGTG4xw=/fit-in/1600x0/filters:focal(554x699:555x700)/https://public-media.si-cdn.com/filer/a4/04/a404c799-7118-459a-8de4-89e4a44b124f/img_1317.jpg', 'admin@gmail.com', 'admin amdin', '$2a$10$iWo3gKA0REFkYOUwwd9r4e2TtKn9MKnfCSUOkkKrEZ37b44TP1qEm', '09784765976', 'admin'),
('2', '2020-09-29 17:10:12.042000', NULL, b'1', NULL, NULL, 'https://thumbs-prod.si-cdn.com/nnJARGtKrLypH4y3Vov2zGTG4xw=/fit-in/1600x0/filters:focal(554x699:555x700)/https://public-media.si-cdn.com/filer/a4/04/a404c799-7118-459a-8de4-89e4a44b124f/img_1317.jpg', 'genson1@gmail.com', 'genson 01', '$2a$10$/3kcoT/IZNHh6kD.6TtZD.pQb7f1Z1clKS78BHv1ZYOrKHzzkgIde', '09784765976', 'genson1');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
('1', '1'),
('2', '2');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
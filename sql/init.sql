-- -------------------------------------------------------------
-- TablePlus 3.9.1(342)
--
-- https://tableplus.com/
--
-- Database: SpringSecurityApp
-- Generation Time: 2020-10-11 00:18:27.1420
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
  `department_code` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`department_code`),
  UNIQUE KEY `UK_1t68827l97cwyxo9r1u6t4p7d` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6djefmjx26crewgafuih51sad` (`fullname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_index` int DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKfg4e2mb2318tph615gh44ll3` (`menu_id`),
  CONSTRAINT `FKfg4e2mb2318tph615gh44ll3` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `FKqyvxw2gg2qk0wld3bqfcb58vq` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
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

CREATE TABLE `user_department` (
  `user_id` bigint NOT NULL,
  `department_code` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`department_code`),
  KEY `FKtd5l0mlq11jo1sx4ijg8qlwsm` (`department_code`),
  CONSTRAINT `FK1alh47saqbwnimxd9o564o4vm` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKtd5l0mlq11jo1sx4ijg8qlwsm` FOREIGN KEY (`department_code`) REFERENCES `department` (`department_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `department` (`department_code`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `description`, `name`) VALUES
('ADMIN', '2020-10-08 12:00:59.144000', NULL, b'1', NULL, NULL, 'Bộ phận điều hành', 'Bộ phận điều hành'),
('RCN', '2020-10-08 12:13:23.611000', NULL, b'1', NULL, NULL, 'Bộ phận tiếp nhận', 'Bộ phận tiếp nhận');

INSERT INTO `menu` (`id`, `created_at`, `is_active`, `updated_at`, `updated_by`, `name`, `order_index`, `parent_id`, `url`) VALUES
('1', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'danh sách phòng ban', '-1', '0', '/management/departments'),
('2', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'thêm ', '1', '1', '/management/departments/add'),
('3', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'sửa', '2', '1', '/management/departments/edit'),
('4', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'danh sách nguời dùng', '-1', '0', '/management/users'),
('5', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'thêm', '1', '4', '/management/users/add'),
('6', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'sửa', '2', '4', '/management/users/edit'),
('7', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'danh sách sản phẩm', '-1', '0', '/management/products'),
('8', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'thêm', '1', '7', '/management/products/add'),
('9', '2020-10-10 12:41:52.000000', b'1', NULL, NULL, 'sửa', '2', '7', '/management/products/edit'),
('10', '2020-10-10 13:42:02.207000', b'1', NULL, NULL, 'sửa', '3', '7', '/management/products/details'),
('11', '2020-10-10 13:49:20.696000', b'1', NULL, NULL, 'chi tiết', '3', '7', '/management/products/detail'),
('12', '2020-10-10 13:50:50.947000', b'1', NULL, NULL, 'danh sách nhân viên', '-1', '0', '/management/employees'),
('13', '2020-10-10 13:51:39.841000', b'1', NULL, NULL, 'thêm', '1', '12', '/management/employees/add');

INSERT INTO `refresh_token` (`token_id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `expiry_dt`, `token`, `user_id`) VALUES
('2', '2020-10-08 12:19:39.579000', NULL, b'1', NULL, NULL, '2020-11-07 12:19:39.512000', 'd269420a-1280-472b-bcd6-ca758012ad73', '2'),
('15', '2020-10-10 15:14:29.036000', NULL, b'1', NULL, NULL, '2020-11-09 15:14:29.002000', '740c76e8-24ae-467a-93cc-5dd257abcb48', '1');

INSERT INTO `role` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `name`, `description`) VALUES
('1', '2020-10-08 12:00:59.292000', NULL, b'1', NULL, NULL, 'ADMIN', 'admin'),
('2', '2020-10-08 12:13:23.665000', NULL, b'1', NULL, NULL, 'RCN', 'RCN');

INSERT INTO `role_menu` (`role_id`, `menu_id`) VALUES
('1', '1'),
('1', '2'),
('1', '3'),
('1', '4'),
('1', '5'),
('1', '6'),
('1', '7'),
('1', '8'),
('1', '9'),
('2', '7'),
('2', '8'),
('2', '9');

INSERT INTO `user` (`id`, `created_at`, `created_by`, `is_active`, `updated_at`, `updated_by`, `avatar`, `email`, `fullname`, `password`, `phone`, `username`) VALUES
('1', '2020-10-08 12:01:20.819000', NULL, b'1', NULL, NULL, 'https://thumbs-prod.si-cdn.com/nnJARGtKrLypH4y3Vov2zGTG4xw=/fit-in/1600x0/filters:focal(554x699:555x700)/https://public-media.si-cdn.com/filer/a4/04/a404c799-7118-459a-8de4-89e4a44b124f/img_1317.jpg', 'admin@gmail.com', 'admin', '$2a$10$8F7mVF0PdFD9DiTN3vgd1eS0sFmu1VasoBgxsp6rNSubgEvvw6mpu', '09784765976', 'admin'),
('2', '2020-10-08 12:19:25.468000', NULL, b'1', '2020-10-08 12:49:07.384000', NULL, 'https://thumbs-prod.si-cdn.com/nnJARGtKrLypH4y3Vov2zGTG4xw=/fit-in/1600x0/filters:focal(554x699:555x700)/https://public-media.si-cdn.com/filer/a4/04/a404c799-7118-459a-8de4-89e4a44b124f/img_1317.jpg', 'son01@gmail.com', 'son01', '$2a$10$ZWY2HZyoaMTSSu1mDrwoHe2.NYnG6Bl4RD0A70mBZl819YvAcISjy', '0985346823', 'son01');

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
('1', '1');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
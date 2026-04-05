-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.29 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for quantum_retail_pro
CREATE DATABASE IF NOT EXISTS `quantum_retail_pro` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quantum_retail_pro`;

-- Dumping structure for table quantum_retail_pro.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `product_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_brand_product_status1_idx` (`product_status_id`),
  CONSTRAINT `fk_brand_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.brand: ~68 rows (approximately)
INSERT INTO `brand` (`id`, `brand`, `product_status_id`) VALUES
	(1, 'Manchee', 5),
	(2, 'Maliban', 5),
	(3, 'Vijaya', 5),
	(4, 'watawala', 5),
	(5, 'Kotagala', 5),
	(6, 'Ran', 5),
	(7, 'Zelora', 5),
	(8, 'Maggi', 5),
	(9, 'Amritha', 5),
	(10, 'Cycle', 5),
	(11, 'Ninja', 5),
	(12, 'Laxapana', 5),
	(13, 'Nestomalt', 5),
	(14, 'Raththi', 5),
	(15, 'Nespray', 5),
	(16, 'Palawaththa', 5),
	(17, 'Milo', 5),
	(18, 'Sooriya', 5),
	(19, 'puredale', 5),
	(20, 'Melko', 5),
	(21, 'Aunchor', 5),
	(22, 'Anlene', 5),
	(23, 'Nan', 5),
	(24, 'Other', 5),
	(25, 'Nestee', 5),
	(26, 'Jayathilaka', 5),
	(27, 'CBL', 5),
	(28, 'Kindo', 5),
	(29, 'Champion', 5),
	(30, 'Bellose', 5),
	(31, 'Dettol', 5),
	(32, 'Samahan', 5),
	(33, 'Lysol', 5),
	(34, 'Harpic', 5),
	(35, 'Sunquick', 5),
	(36, 'Harischandra', 5),
	(37, 'denta', 5),
	(38, 'Clogate', 5),
	(39, 'Wim', 5),
	(40, 'Comfort', 5),
	(41, 'Vaseline', 5),
	(42, 'Pears', 5),
	(43, 'Lifebuoy', 5),
	(44, 'Panda', 5),
	(45, 'Signal', 5),
	(46, 'Ayush', 5),
	(47, 'CloseUp', 5),
	(48, 'Sudantha', 5),
	(49, 'Lakmee', 5),
	(50, 'Unic', 5),
	(51, 'Sera', 5),
	(52, 'Ruhunu', 5),
	(53, 'Prima', 5),
	(54, 'Rath Mal', 5),
	(55, 'Wonderlight', 5),
	(56, 'Vendol', 5),
	(57, 'Rani', 5),
	(58, 'Kohomba', 5),
	(59, 'Lak', 5),
	(60, 'Lux', 5),
	(61, 'Freelan', 5),
	(62, 'Lanka', 5),
	(63, 'Sapumal', 5),
	(64, 'Neeroga', 5),
	(65, 'Sanstha', 5),
	(66, 'Ocean Star', 5),
	(67, 'Milan', 5),
	(68, 'Ravan', 5);

-- Dumping structure for table quantum_retail_pro.cash_withdrawal
CREATE TABLE IF NOT EXISTS `cash_withdrawal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `amount` double NOT NULL,
  `reason` text NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cash_withdrawal_session1_idx` (`session_id`),
  CONSTRAINT `fk_cash_withdrawal_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.cash_withdrawal: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  `product_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_product_status1_idx` (`product_status_id`),
  CONSTRAINT `fk_category_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.category: ~14 rows (approximately)
INSERT INTO `category` (`id`, `category`, `product_status_id`) VALUES
	(1, 'Biscuit', 5),
	(2, 'spices', 5),
	(3, 'noodles', 5),
	(4, 'Tea', 5),
	(5, 'හදුන්කූරු', 5),
	(6, 'Home Care', 5),
	(7, 'Battery', 5),
	(8, 'Milk Products', 5),
	(9, 'Other', 5),
	(10, 'Grain Products', 5),
	(11, 'Personal Care', 5),
	(12, 'Health', 5),
	(13, 'Drinks', 5),
	(14, 'Food', 5);

-- Dumping structure for table quantum_retail_pro.category_has_brand
CREATE TABLE IF NOT EXISTS `category_has_brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int NOT NULL,
  `brand_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_has_brand_category1_idx` (`category_id`),
  KEY `fk_category_has_brand_brand1_idx` (`brand_id`),
  CONSTRAINT `fk_category_has_brand_brand1` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
  CONSTRAINT `fk_category_has_brand_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.category_has_brand: ~81 rows (approximately)
INSERT INTO `category_has_brand` (`id`, `category_id`, `brand_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 2, 3),
	(4, 3, 3),
	(5, 4, 3),
	(6, 4, 4),
	(7, 4, 5),
	(8, 4, 6),
	(9, 4, 7),
	(10, 2, 8),
	(11, 3, 8),
	(12, 4, 2),
	(13, 5, 10),
	(14, 5, 9),
	(15, 6, 11),
	(16, 7, 12),
	(17, 8, 13),
	(18, 8, 14),
	(19, 8, 15),
	(20, 8, 16),
	(21, 8, 17),
	(22, 8, 18),
	(23, 8, 19),
	(24, 8, 20),
	(25, 8, 21),
	(26, 8, 22),
	(27, 8, 2),
	(28, 8, 23),
	(29, 8, 24),
	(30, 8, 25),
	(31, 10, 26),
	(32, 9, 27),
	(33, 11, 28),
	(34, 11, 29),
	(35, 11, 30),
	(36, 11, 31),
	(37, 12, 32),
	(38, 6, 33),
	(39, 6, 34),
	(40, 13, 35),
	(41, 2, 36),
	(42, 11, 37),
	(43, 11, 38),
	(44, 6, 39),
	(45, 6, 40),
	(46, 11, 41),
	(47, 11, 42),
	(48, 11, 43),
	(49, 11, 44),
	(50, 11, 45),
	(51, 11, 46),
	(52, 11, 47),
	(53, 11, 48),
	(54, 6, 10),
	(55, 2, 49),
	(56, 9, 50),
	(57, 3, 51),
	(58, 3, 52),
	(59, 3, 53),
	(60, 11, 54),
	(61, 11, 36),
	(62, 11, 55),
	(63, 11, 56),
	(64, 11, 57),
	(65, 11, 58),
	(66, 11, 59),
	(67, 11, 60),
	(68, 14, 49),
	(69, 14, 61),
	(70, 14, 62),
	(71, 9, 63),
	(72, 13, 36),
	(73, 12, 64),
	(74, 12, 56),
	(75, 3, 36),
	(76, 10, 36),
	(77, 2, 65),
	(78, 14, 66),
	(79, 14, 67),
	(80, 11, 68),
	(81, 11, 24),
	(82, 6, 29),
	(83, 10, 46),
	(84, 14, 46),
	(85, 12, 30);

-- Dumping structure for table quantum_retail_pro.close_sale
CREATE TABLE IF NOT EXISTS `close_sale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `c5000` int NOT NULL,
  `c1000` int NOT NULL,
  `c500` int NOT NULL,
  `c100` int NOT NULL,
  `c50` int NOT NULL,
  `c20` int NOT NULL,
  `c10` int NOT NULL,
  `c5` int NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_close_sale_session1_idx` (`session_id`),
  CONSTRAINT `fk_close_sale_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.close_sale: ~0 rows (approximately)
INSERT INTO `close_sale` (`id`, `session_id`, `c5000`, `c1000`, `c500`, `c100`, `c50`, `c20`, `c10`, `c5`, `date_time`) VALUES
	(1, 6, 1, 1, 2, 0, 0, 0, 0, 0, '2026-03-01 22:02:52');

-- Dumping structure for table quantum_retail_pro.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(150) NOT NULL,
  `telephone_1` varchar(10) NOT NULL,
  `telephone_2` varchar(10) NOT NULL,
  `supply_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_supply_status1_idx` (`supply_status_id`),
  CONSTRAINT `fk_company_supply_status1` FOREIGN KEY (`supply_status_id`) REFERENCES `supply_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.company: ~0 rows (approximately)
INSERT INTO `company` (`id`, `name`, `address`, `telephone_1`, `telephone_2`, `supply_status_id`) VALUES
	(1, 'ASD', 'asd', '0719892932', '0719892932', 1);

-- Dumping structure for table quantum_retail_pro.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mobile` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `credit_amount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.customer: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.customer_has_invoice
CREATE TABLE IF NOT EXISTS `customer_has_invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `invoice_id` int NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_has_invoice_invoice1_idx` (`invoice_id`),
  KEY `fk_customer_has_invoice_customer1_idx` (`customer_id`),
  CONSTRAINT `fk_customer_has_invoice_customer1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `fk_customer_has_invoice_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.customer_has_invoice: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.damage_expire
CREATE TABLE IF NOT EXISTS `damage_expire` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `reason` text NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_damage_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_damage_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.damage_expire: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.damage_item
CREATE TABLE IF NOT EXISTS `damage_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `damage_expire_id` int NOT NULL,
  `stock_batch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_damage_item_damage_expire1_idx` (`damage_expire_id`),
  KEY `fk_damage_item_stock1_idx` (`stock_batch_id`),
  CONSTRAINT `fk_damage_item_damage_expire1` FOREIGN KEY (`damage_expire_id`) REFERENCES `damage_expire` (`id`),
  CONSTRAINT `fk_damage_item_stock1` FOREIGN KEY (`stock_batch_id`) REFERENCES `stock` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.damage_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.employee
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `employee_role_id` int NOT NULL,
  `employee_status_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `pin` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_employee_role1_idx` (`employee_role_id`),
  KEY `fk_employee_employee_status1_idx` (`employee_status_id`),
  CONSTRAINT `fk_employee_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_role` (`id`),
  CONSTRAINT `fk_employee_employee_status1` FOREIGN KEY (`employee_status_id`) REFERENCES `employee_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee: ~3 rows (approximately)
INSERT INTO `employee` (`id`, `name`, `employee_role_id`, `employee_status_id`, `username`, `password`, `pin`) VALUES
	(1, 'Wijakoon Super', 1, 1, 'Admin', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', '2237'),
	(2, 'Wijakoon Super', 2, 1, 'Cashier', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', NULL),
	(3, 'Developers', 3, 1, 'Developer', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', '2237');

-- Dumping structure for table quantum_retail_pro.employee_panel
CREATE TABLE IF NOT EXISTS `employee_panel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_panel: ~3 rows (approximately)
INSERT INTO `employee_panel` (`id`, `type`) VALUES
	(1, 'admin'),
	(2, 'cashier'),
	(3, 'developer');

-- Dumping structure for table quantum_retail_pro.employee_role
CREATE TABLE IF NOT EXISTS `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  `employee_panel_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_role_employee_panel1_idx` (`employee_panel_id`),
  CONSTRAINT `fk_employee_role_employee_panel1` FOREIGN KEY (`employee_panel_id`) REFERENCES `employee_panel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_role: ~3 rows (approximately)
INSERT INTO `employee_role` (`id`, `role`, `employee_panel_id`) VALUES
	(1, 'admin', 1),
	(2, 'cashier', 2),
	(3, 'developer', 3);

-- Dumping structure for table quantum_retail_pro.employee_role_has_interface
CREATE TABLE IF NOT EXISTS `employee_role_has_interface` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_role_id` int NOT NULL,
  `interface_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_role_has_interface_employee_role1_idx` (`employee_role_id`),
  KEY `fk_employee_role_has_interface_interface1_idx` (`interface_id`),
  CONSTRAINT `fk_employee_role_has_interface_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_role` (`id`),
  CONSTRAINT `fk_employee_role_has_interface_interface1` FOREIGN KEY (`interface_id`) REFERENCES `interface` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_role_has_interface: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.employee_status
CREATE TABLE IF NOT EXISTS `employee_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_status: ~3 rows (approximately)
INSERT INTO `employee_status` (`id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Inactive'),
	(3, 'Suspended');

-- Dumping structure for table quantum_retail_pro.grn
CREATE TABLE IF NOT EXISTS `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_code` varchar(20) NOT NULL,
  `date_time` datetime NOT NULL,
  `discount` double NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.grn: ~0 rows (approximately)
INSERT INTO `grn` (`id`, `grn_code`, `date_time`, `discount`, `supplier_id`) VALUES
	(1, '321654', '2026-02-08 20:48:16', 0, 1);

-- Dumping structure for table quantum_retail_pro.grn_item
CREATE TABLE IF NOT EXISTS `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `grn_id` int NOT NULL,
  `qty` double NOT NULL,
  `cost_price` double NOT NULL,
  `sale_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_product1_idx` (`product_id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.grn_item: ~0 rows (approximately)
INSERT INTO `grn_item` (`id`, `product_id`, `grn_id`, `qty`, `cost_price`, `sale_price`) VALUES
	(1, 247, 1, 50000, 1050, 1200);

-- Dumping structure for table quantum_retail_pro.interface
CREATE TABLE IF NOT EXISTS `interface` (
  `id` int NOT NULL AUTO_INCREMENT,
  `interface` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.interface: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `bill_amount` double NOT NULL,
  `paid_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `session_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_session1_idx` (`session_id`),
  CONSTRAINT `fk_invoice_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice: ~4 rows (approximately)
INSERT INTO `invoice` (`id`, `date_time`, `bill_amount`, `paid_amount`, `credit_amount`, `session_id`) VALUES
	(13, '2026-02-08 20:32:20', 720, 800, 0, 4),
	(14, '2026-02-08 21:08:29', 100, 100, 0, 4),
	(15, '2026-02-18 19:33:11', 960, 1000, 0, 5),
	(16, '2026-02-18 19:50:18', 960, 1000, 0, 5),
	(17, '2026-03-01 21:54:16', 460, 500, 0, 6);

-- Dumping structure for table quantum_retail_pro.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `sale_price` double NOT NULL,
  `cost_price` double NOT NULL,
  `discount` double NOT NULL,
  `invoice_id` int NOT NULL,
  `invoice_item_type_id` int NOT NULL,
  `product_id` int NOT NULL,
  `stock_batch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_invoice_item_type1_idx` (`invoice_item_type_id`),
  KEY `fk_invoice_item_product1_idx` (`product_id`),
  KEY `fk_invoice_item_stock1_idx` (`stock_batch_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_item_invoice_item_type1` FOREIGN KEY (`invoice_item_type_id`) REFERENCES `invoice_item_type` (`id`),
  CONSTRAINT `fk_invoice_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_invoice_item_stock1` FOREIGN KEY (`stock_batch_id`) REFERENCES `stock` (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice_item: ~4 rows (approximately)
INSERT INTO `invoice_item` (`id`, `qty`, `sale_price`, `cost_price`, `discount`, `invoice_id`, `invoice_item_type_id`, `product_id`, `stock_batch_id`) VALUES
	(13, 3, 300, 200, 60, 13, 1, 248, 2),
	(14, 1, 150, 100, 50, 14, 1, 18, 3),
	(15, 4, 300, 200, 60, 15, 1, 248, 2),
	(16, 4, 300, 200, 60, 16, 1, 248, 2),
	(17, 2, 230, 200, 0, 17, 1, 168, 4);

-- Dumping structure for table quantum_retail_pro.invoice_item_type
CREATE TABLE IF NOT EXISTS `invoice_item_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'selling, returning   ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice_item_type: ~2 rows (approximately)
INSERT INTO `invoice_item_type` (`id`, `type`) VALUES
	(1, 'selling'),
	(2, 'returning');

-- Dumping structure for table quantum_retail_pro.location
CREATE TABLE IF NOT EXISTS `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(150) NOT NULL,
  `telephone_1` varchar(10) NOT NULL,
  `telephone_2` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.location: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.location_supply
CREATE TABLE IF NOT EXISTS `location_supply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `employee_id` int NOT NULL,
  `location_id` int NOT NULL,
  `location_supply_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_return_employee1_idx` (`employee_id`),
  KEY `fk_location_return_location1_idx` (`location_id`),
  KEY `fk_location_supply_location_supply_type1_idx` (`location_supply_type_id`),
  CONSTRAINT `fk_location_return_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_location_return_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_location_supply_location_supply_type1` FOREIGN KEY (`location_supply_type_id`) REFERENCES `location_supply_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.location_supply: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.location_supply_item
CREATE TABLE IF NOT EXISTS `location_supply_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` double NOT NULL,
  `location_supply_id` int NOT NULL,
  `stock_batch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_return_item_product1_idx` (`product_id`),
  KEY `fk_location_supply_item_location_supply1_idx` (`location_supply_id`),
  KEY `fk_location_supply_item_stock1_idx` (`stock_batch_id`),
  CONSTRAINT `fk_location_return_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_location_supply_item_location_supply1` FOREIGN KEY (`location_supply_id`) REFERENCES `location_supply` (`id`),
  CONSTRAINT `fk_location_supply_item_stock1` FOREIGN KEY (`stock_batch_id`) REFERENCES `stock` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.location_supply_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.location_supply_type
CREATE TABLE IF NOT EXISTS `location_supply_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'defauld, damage, expired\\\\ndefault return goes to store table\\\\ndamage & expired goes to damage table',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.location_supply_type: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product` varchar(45) NOT NULL,
  `measure` float NOT NULL,
  `product_status_id` int NOT NULL,
  `category_has_brand_id` int NOT NULL,
  `cost_price` double NOT NULL,
  `sale_price` double NOT NULL,
  `discount` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_product_status1_idx` (`product_status_id`),
  KEY `fk_product_category_has_brand1_idx` (`category_has_brand_id`),
  CONSTRAINT `fk_product_category_has_brand1` FOREIGN KEY (`category_has_brand_id`) REFERENCES `category_has_brand` (`id`),
  CONSTRAINT `fk_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product: ~237 rows (approximately)
INSERT INTO `product` (`id`, `product`, `measure`, `product_status_id`, `category_has_brand_id`, `cost_price`, `sale_price`, `discount`) VALUES
	(4, 'විජය මිරිස් කුඩු  50g', 1, 5, 3, 73, 80, 0),
	(5, 'විජය මිරිස් කුඩු 100g', 1, 5, 3, 140, 150, 0),
	(6, 'විජය මිරිස් කුඩු 250g', 1, 5, 3, 350, 400, 0),
	(7, 'විජය මිරිස් කුඩු 500g', 1, 5, 3, 600, 700, 0),
	(8, 'විජය කෑලි මිරිස් 100g', 1, 5, 3, 145, 160, 0),
	(9, 'විජය කෑලි මිරිස් 50g', 1, 5, 3, 73, 80, 0),
	(10, 'විජය කෑලි මිරිස් 250g', 1, 5, 3, 350, 400, 0),
	(11, 'විජය තුනපහ 50g', 1, 5, 3, 75, 85, 0),
	(12, 'විජය තුනපහ 100g', 1, 5, 3, 150, 170, 0),
	(13, 'විජය තුනපහ 250g', 1, 5, 3, 380, 425, 0),
	(14, 'විජය ගම්මිරිස් කුඩු 50g', 1, 5, 3, 215, 240, 0),
	(15, 'විජය ගම්මිරිස් කුඩු 25g', 1, 5, 3, 115, 125, 0),
	(16, 'විජය කහ කුඩු 50g', 1, 5, 3, 230, 250, 0),
	(17, 'විජය කහ කුඩු 25g', 1, 5, 3, 120, 130, 0),
	(18, 'බැදපු කුනපහ කුඩු 50g', 1, 5, 3, 95, 105, 0),
	(19, 'බැදපු කුනපහ කුඩු 100g', 1, 5, 3, 190, 210, 0),
	(20, 'බැදපු කුනපහ කුඩු 250g', 1, 5, 3, 470, 525, 0),
	(21, 'විජය මස්කරි 25g', 1, 5, 3, 55, 65, 0),
	(22, 'විජය මස්කරි 50g', 1, 5, 3, 105, 120, 0),
	(23, 'විජය පපඩම් 70g', 1, 5, 3, 135, 145, 0),
	(24, 'විජය නූඩ්ල්ස් 400g', 1, 5, 4, 210, 230, 0),
	(25, 'විජය නූඩ්ල්ස් 500g', 1, 5, 4, 222, 260, 0),
	(26, 'විජය තේ කුඩු 100g', 1, 5, 5, 240, 270, 0),
	(27, 'විජය තේ කුඩු 50g', 1, 5, 5, 120, 135, 0),
	(28, 'වටවල තේ කුඩු  50g', 1, 5, 6, 135, 145, 0),
	(29, 'වටවල තේ කුඩු  100g', 1, 5, 6, 265, 285, 0),
	(30, 'වටවල තේ කුඩු  200g', 1, 5, 6, 520, 560, 0),
	(31, 'කොටගල තේ කුඩු  100g', 1, 5, 7, 245, 270, 0),
	(32, 'කොටගල තේ කුඩු  50g', 1, 5, 7, 125, 135, 0),
	(33, 'කොටගල තේ කුඩු  200g', 1, 5, 7, 485, 535, 0),
	(34, 'රන් කහට 50g', 1, 5, 8, 83, 90, 0),
	(35, 'රන් කහට 100g', 1, 5, 8, 185, 195, 0),
	(36, 'Zelora Red Tea 50g', 1, 5, 9, 125, 135, 0),
	(37, 'Zelora Red Tea 100g', 1, 5, 9, 245, 265, 0),
	(38, 'Zelora Red Tea 200g', 1, 5, 9, 430, 500, 0),
	(39, 'Zelora Blue Tea 50g', 1, 5, 9, 90, 100, 0),
	(40, 'Zelora Blue Tea 200g', 1, 5, 9, 280, 330, 0),
	(41, 'Zelora Red Tea 250g', 1, 5, 9, 560, 650, 0),
	(42, 'Maggi coco. powder 300g', 1, 5, 10, 1050, 1100, 0),
	(43, 'Maggi coco. powder 125g', 1, 5, 10, 450, 480, 0),
	(44, 'Maggi coco. powder 25g', 1, 5, 10, 105, 110, 0),
	(45, 'Maggi කොත්තු මී 80g', 1, 5, 11, 120, 130, 0),
	(46, 'Maggi චිකන් noodles', 1, 5, 11, 105, 115, 0),
	(47, 'මැලිබන් තේ කුඩු 100g', 1, 5, 12, 250, 270, 0),
	(48, 'මැලිබන් තේ කුඩු 50g', 1, 5, 12, 138, 150, 0),
	(49, 'සයිකල් හදුන්කූරු  small', 1, 5, 13, 30, 50, 0),
	(50, 'සයිකල් හදුන්කූරු  large', 1, 5, 13, 70, 100, 0),
	(51, 'අම්රිතා හදුන්කූරු ', 1, 5, 14, 90, 100, 0),
	(52, 'නින්ජා මදුරු දගර ', 1, 5, 15, 165, 180, 0),
	(53, 'බැටරි කෑලි', 1, 5, 16, 115, 125, 0),
	(54, 'නෙස්ටමෝල්ට් 400g WF', 1, 5, 17, 729, 760, 0),
	(55, 'නෙස්ටමෝල්ට් 400g BOX', 1, 5, 17, 739, 770, 0),
	(56, 'නෙස්ටමෝල්ට් 600g ', 1, 5, 17, 1050, 1100, 0),
	(57, 'රත්තී 400g', 1, 5, 18, 1110, 1150, 0),
	(58, 'රත්තී 200g', 1, 5, 18, 580, 600, 0),
	(59, 'රත්තී 18g', 1, 5, 18, 68, 70, 0),
	(60, 'රත්තී 75g', 1, 5, 18, 223, 230, 0),
	(61, 'රත්තී 1kg', 1, 5, 18, 2750, 2850, 0),
	(62, 'නෙස්ටමෝල්ට් 300g', 1, 5, 17, 555, 590, 0),
	(63, 'නෙස්ටමෝල්ට් 28g', 1, 5, 17, 56, 60, 0),
	(64, 'නෙස්ප්‍රේ 350g', 1, 5, 19, 755, 800, 0),
	(65, 'පැලවත්ත  කිරිපිටි 400g ', 1, 5, 20, 1055, 1100, 0),
	(66, 'මයිලෝ bib 400g', 1, 5, 21, 843, 880, 0),
	(67, 'සූරිය කිරි 400g', 1, 5, 22, 1060, 1100, 0),
	(68, 'පියෝඩේල්  400g BOX', 1, 5, 23, 1060, 1100, 0),
	(69, 'පියෝඩේල්  400g  WF', 1, 5, 23, 840, 880, 0),
	(70, 'මෙල්කෝ කිරි 400g', 1, 5, 24, 1060, 1090, 0),
	(71, 'ඇන්කර් පිටි  200g', 1, 5, 25, 580, 600, 0),
	(72, 'ඇන්කර් පිටි  400g', 1, 5, 25, 1110, 1150, 0),
	(73, 'ඇන්කර් Pediapro 1-3  400g', 1, 5, 25, 1370, 1430, 0),
	(74, 'ඇන්කර් Pediapro 3-5  400g', 1, 5, 25, 1280, 1360, 0),
	(75, 'ඇන්ලීන් 400g', 1, 5, 26, 1255, 1300, 0),
	(76, 'මැලිබන් කිරි 200g', 1, 5, 27, 575, 600, 0),
	(77, 'NAN 1', 1, 5, 28, 1990, 2100, 0),
	(78, 'NAN 1', 1, 5, 28, 1990, 2100, 0),
	(79, 'Maggi සුප් කැට', 1, 5, 10, 23, 50, 0),
	(80, 'Maggi රස මුසු 6g', 1, 5, 10, 28, 30, 0),
	(81, 'මිල්ක්මේඩ් 397g', 1, 5, 29, 755, 790, 0),
	(82, 'මිල්ක්මේඩ් 510g', 1, 5, 29, 950, 990, 0),
	(83, 'පියෝඩේල්  කිරි 25g', 1, 5, 23, 57, 60, 0),
	(84, 'නෙස්ප්‍රේ 18g', 1, 5, 19, 56, 60, 0),
	(85, 'Nestee', 1, 5, 30, 67, 70, 0),
	(86, 'ජයතිලක ඉදිආප්ප පිටි - සුදු', 1, 5, 31, 290, 310, 0),
	(87, 'ජයතිලක ඉදිආප්ප පිටි - රතු', 1, 5, 31, 290, 310, 0),
	(88, 'මැලිබන් ලෙමන් පෆ් 200g', 1, 5, 2, 225, 250, 0),
	(89, 'මැලිබන් ගෝල්ඩ් මාරි 100g', 1, 5, 2, 78, 90, 0),
	(90, 'මැලිබන් නයිස් 100g', 1, 5, 2, 95, 110, 0),
	(91, 'මැලිබන් ගෝල්ඩ් මාරි 360g', 1, 5, 2, 290, 330, 0),
	(92, 'චොකලට් ක්‍රීම් 100g', 1, 5, 2, 115, 130, 0),
	(93, 'චොකලට් ක්‍රීම් 400g', 1, 5, 2, 385, 450, 0),
	(94, 'චොකලට් මාරි 90g', 1, 5, 2, 90, 100, 0),
	(95, 'චොකලට් මාරි 400g', 1, 5, 2, 355, 400, 0),
	(96, 'චොකලට් පෆ්  200g', 1, 5, 2, 225, 250, 0),
	(97, 'ජින්ජර් බිස්කට් 240g', 1, 5, 2, 245, 280, 0),
	(98, 'ජින්ජර් බිස්කට් 370g', 1, 5, 2, 358, 420, 0),
	(99, 'මැලිබන් ලෙමන් පෆ් 100g', 1, 5, 2, 115, 130, 0),
	(100, 'ලයිට් මාරි 50g', 1, 5, 2, 45, 50, 0),
	(101, 'මිල්ක්ෂෝට් බිස්කට්  200g', 1, 5, 2, 205, 230, 0),
	(102, 'මැලිබන් නයිස් 400g', 1, 5, 2, 370, 430, 0),
	(103, 'ක්‍රීම් ක්‍රැකර් 85g', 1, 5, 2, 80, 90, 0),
	(104, 'ක්‍රීම් ක්‍රැකර් 125g', 1, 5, 2, 125, 140, 0),
	(105, 'ක්‍රීම් ක්‍රැකර් 190g', 1, 5, 2, 175, 200, 0),
	(106, 'ක්‍රීම් ක්‍රැකර් 230g', 1, 5, 2, 205, 230, 0),
	(107, 'Waf cream බිස්කට් 90g', 1, 5, 2, 110, 120, 0),
	(108, 'Waf cream බිස්කට් 225g', 1, 5, 2, 260, 300, 0),
	(109, 'Waf cream බිස්කට් 400g', 1, 5, 2, 445, 520, 0),
	(110, 'ක්‍රීම් ක්‍රැකර් 500g', 1, 5, 2, 370, 420, 0),
	(111, ' ක්‍රැකර් 120g', 1, 5, 2, 105, 120, 0),
	(112, 'බ්‍රැන් ක්‍රැකර් 140g', 1, 5, 2, 180, 200, 0),
	(113, 'බ්‍රැන් ක්‍රැකර් 210g', 1, 5, 2, 260, 290, 0),
	(114, 'මැලිබන් යෝ යෝ 240g', 1, 5, 2, 188, 220, 0),
	(115, 'මන්චී ක්‍රීම් ක්‍රැකර් 500g', 1, 5, 1, 360, 420, 0),
	(116, 'මන්චී ක්‍රීම් ක්‍රැකර් 125g', 1, 5, 1, 125, 140, 0),
	(117, 'මන්චී ක්‍රීම් ක්‍රැකර් 190g', 1, 5, 1, 175, 200, 0),
	(118, 'මන්චී ක්‍රීම් ක්‍රැකර් 85g', 1, 5, 1, 80, 90, 0),
	(119, 'මන්චී ක්‍රීම් ක්‍රැකර් 250g', 1, 5, 1, 205, 230, 0),
	(120, 'මන්චී චීස් ක්‍රැකර් 200g', 1, 5, 1, 240, 270, 0),
	(121, 'මන්චී පොටැටෝ ක්‍රැකර් 110g', 1, 5, 1, 160, 180, 0),
	(122, 'මන්චී සන් ක්‍රැකර් 95g', 1, 5, 1, 105, 120, 0),
	(123, 'මන්චී සන් ක්‍රැකර් 90g', 1, 5, 1, 100, 110, 0),
	(124, 'මන්චී ටිකිරි මාරි  80g', 1, 5, 1, 78, 90, 0),
	(125, 'මන්චී චොකලට් මාරි  90g', 1, 5, 1, 90, 100, 0),
	(126, 'මන්චී චොකලට් ක්‍රීම් 100g', 1, 5, 1, 115, 130, 0),
	(127, 'මන්චී නයිස්  100g', 1, 5, 1, 95, 110, 0),
	(128, 'මන්චී නයිස්  200g', 1, 5, 1, 195, 220, 0),
	(129, 'මන්චී ජින්ජර් බිස්කට් 85g', 1, 5, 1, 105, 120, 0),
	(130, 'මන්චී ටිකිරි මාරි  230g', 1, 5, 1, 200, 230, 0),
	(131, 'මන්චී චොකලට් මාරි  200g', 1, 5, 1, 200, 220, 0),
	(132, 'මන්චී ටිෆින් බිස්කට් 125g', 1, 5, 1, 170, 190, 0),
	(133, 'මන්චී හවායින්කුකිස් 200g', 1, 5, 1, 215, 240, 0),
	(134, 'මන්චී මිල්ක්ෂෝට් බිස්කට්  85g', 1, 5, 1, 108, 120, 0),
	(135, 'මන්චී මිල්ක් cake බිස්කට්  200g', 1, 5, 1, 205, 230, 0),
	(136, 'මන්චී ටිකිරි මාරි  360g', 1, 5, 1, 275, 330, 0),
	(137, 'මන්චී චොකලට් මාරි  400g', 1, 5, 1, 355, 400, 0),
	(138, 'මන්චී ලයිට් මාරි 250g', 1, 5, 1, 195, 220, 0),
	(139, 'මන්චී චොකලට් ක්‍රීම් 365g', 1, 5, 1, 350, 410, 0),
	(140, 'මන්චී නයිස්  400g', 1, 5, 1, 370, 430, 0),
	(141, 'මන්චී Waf cream බිස්කට් 400g', 1, 5, 1, 445, 520, 0),
	(142, 'සමපෝෂ 200g', 1, 5, 32, 163, 185, 0),
	(143, 'සමපෝෂ 500g', 1, 5, 32, 365, 420, 0),
	(144, 'සමපෝෂ 700g', 1, 5, 32, 520, 580, 0),
	(145, 'ජයතිලක ඉදිආප්ප පිටි 5kg', 1, 5, 31, 1425, 1600, 0),
	(146, ' උම්බලකඩ Bittle', 1, 5, 10, 240, 350, 0),
	(147, 'කින්ඩෝ සබන්', 1, 5, 33, 102, 110, 0),
	(148, 'Champion සබන් 65g', 1, 5, 34, 86, 95, 0),
	(149, 'Bellose Hair color men', 1, 5, 35, 240, 280, 0),
	(150, 'Bellose Hair color women', 1, 5, 35, 320, 380, 0),
	(151, 'Dettol ප්ලාස්ටර්', 1, 5, 36, 17, 20, 0),
	(152, 'Dettol handwash 125 ml', 1, 5, 36, 360, 390, 0),
	(153, 'Dettol 100ml', 1, 5, 36, 320, 340, 0),
	(154, 'Dettol 60ml', 1, 5, 36, 190, 210, 0),
	(155, 'සමහන්', 1, 5, 37, 53, 60, 0),
	(156, 'Lysol 200ml', 1, 5, 38, 245, 250, 0),
	(157, 'Harpic 500ml', 1, 5, 39, 400, 470, 0),
	(158, 'Lysol 500ml', 1, 5, 38, 465, 500, 0),
	(159, 'සන්කුයික් 700ml', 1, 5, 40, 1200, 1270, 0),
	(160, 'හරිස්චන්ද්‍ර සොයා සෝස් 350ml', 1, 5, 41, 245, 280, 0),
	(161, 'Denta toothbrush', 1, 5, 42, 105, 130, 0),
	(162, 'Colgate toothbrash', 1, 5, 43, 108, 120, 0),
	(163, 'විම් 500ml', 1, 5, 44, 385, 415, 0),
	(164, 'Comfort 210ml', 1, 5, 45, 275, 300, 0),
	(165, 'Vaseline  100ml', 1, 5, 46, 415, 450, 0),
	(166, 'Vaseline  100ml', 1, 5, 46, 375, 410, 0),
	(167, 'Vaseline refill pack 100ml', 1, 5, 46, 270, 300, 0),
	(168, 'Pears baby cream 100ml', 1, 5, 47, 330, 360, 0),
	(169, 'Lifebuoy shampoo 175ml', 1, 5, 48, 470, 500, 0),
	(170, 'Panda baby කලෝන් box 100ml', 1, 5, 49, 500, 550, 0),
	(171, 'Panda baby Powder 100g', 1, 5, 49, 290, 320, 0),
	(172, 'Panda baby Cream 100ml', 1, 5, 49, 325, 360, 0),
	(173, 'Panda baby Shampoo 100g', 1, 5, 49, 245, 270, 0),
	(174, 'Panda baby කලෝන් 100g', 1, 5, 49, 435, 490, 0),
	(175, 'Pears baby කලෝන් 50ml', 1, 5, 47, 320, 360, 0),
	(176, 'Panda baby two pack', 1, 5, 49, 185, 200, 0),
	(177, 'Signal Toothpaste 120g', 1, 5, 50, 218, 230, 0),
	(178, 'Signal Toothpaste 160g', 1, 5, 50, 260, 275, 0),
	(179, 'Signal Toothpaste 200g', 1, 5, 50, 315, 330, 0),
	(180, 'Ayush Toothpaste 110g', 1, 5, 51, 260, 280, 0),
	(181, 'ColseUp Toothpaste 120g', 1, 5, 52, 345, 360, 0),
	(182, 'Sudantha Toothpaste 45g', 1, 5, 53, 135, 145, 0),
	(183, 'Sudantha Toothpaste 120g', 1, 5, 53, 255, 270, 0),
	(184, 'Suwada Sambrani ', 1, 5, 54, 60, 70, 0),
	(185, 'Lakmee සෝස් 200g', 1, 5, 55, 175, 190, 0),
	(186, 'නිල්මා නිල් දියර J', 1, 5, 56, 210, 250, 0),
	(187, 'නිල්මා නිල් දියර L new', 1, 5, 56, 80, 100, 0),
	(188, 'නිල්මා නිල් දියර M', 1, 5, 56, 43, 50, 0),
	(189, 'විජය බිරියානි මසාලා 60g', 1, 5, 3, 250, 270, 0),
	(190, 'Lakmee සෝස් 400g', 1, 5, 55, 280, 300, 0),
	(191, 'Sera Festive Noodles 400g', 1, 5, 57, 285, 320, 0),
	(192, 'Sera Noodles Regular 400g', 1, 5, 57, 220, 250, 0),
	(193, 'Ruhunu Noodles', 1, 5, 58, 220, 250, 0),
	(194, 'Prima Family Pack  385g', 1, 5, 59, 340, 370, 0),
	(195, 'Maggi  Family Pack ', 1, 5, 59, 320, 350, 0),
	(196, 'රත්මල් දෙළුම් සබන් 75g', 1, 5, 60, 120, 130, 0),
	(197, 'හරිස්චන්ද්‍ර ඇත්තෝර සබන්  ', 1, 5, 61, 108, 120, 0),
	(198, 'හරිස්චන්ද්‍ර සමන් සබන්  ', 1, 5, 61, 108, 120, 0),
	(199, 'Lifebuoy pack', 1, 5, 48, 345, 370, 0),
	(200, 'Wonderlight සබන්  110g', 1, 5, 62, 98, 110, 0),
	(201, 'Dettol සබන්  70g', 1, 5, 36, 125, 135, 0),
	(202, 'Vendol Venivel සබන්  ', 1, 5, 63, 110, 120, 0),
	(203, 'රානි සබන්  ', 1, 5, 64, 115, 125, 0),
	(204, 'රානි සබන්  Pack', 1, 5, 64, 485, 520, 0),
	(205, 'කොහොඔ බෙබි සබන්  ', 1, 5, 65, 120, 130, 0),
	(206, 'කොහොඔ බෙබි සබන්  original', 1, 5, 65, 115, 125, 0),
	(207, 'කොහොඔ බෙබි Pack', 1, 5, 65, 465, 490, 0),
	(208, 'කොහොඔ Pack සබන්  ', 1, 5, 65, 485, 520, 0),
	(209, 'Panda baby pack සබන්  ', 1, 5, 49, 545, 580, 0),
	(210, 'විම් කැට 200g', 1, 5, 44, 124, 135, 0),
	(211, 'ලක් බාර් සබන්  ', 1, 5, 66, 495, 580, 0),
	(212, 'Lux සබන්  70g', 1, 5, 67, 105, 120, 0),
	(213, 'Lakmee soy - කලපු ඉස්සෝ ', 1, 5, 68, 180, 180, 0),
	(214, 'Lakmee soy -  දඩයම් බට්ටා', 1, 5, 68, 150, 150, 0),
	(215, 'Lakmee soy -  චිකන් අයියා', 1, 5, 68, 200, 200, 0),
	(216, 'Freelanකුකුල් මස් රස සොයා', 1, 5, 69, 50, 50, 0),
	(217, 'Freelan ‌ඩෙවිල් චිකන් සොයා', 1, 5, 69, 120, 120, 0),
	(218, 'Lanka soy බජට් පැක්', 1, 5, 70, 60, 60, 0),
	(219, 'සපුමල් ඉටිපන්දම් S', 1, 5, 71, 14, 16, 0),
	(220, 'හරිස්චන්ද්‍ර කෝපි 100g', 1, 5, 72, 400, 430, 0),
	(221, 'හරිස්චන්ද්‍ර කෝපි 50g', 1, 5, 72, 200, 215, 0),
	(222, 'හරිස්චන්ද්‍ර කෝපි 20g', 1, 5, 72, 80, 90, 0),
	(223, 'නීරෝගා අසමෝදගම්', 1, 5, 73, 180, 200, 0),
	(224, 'නීරෝගා පේයාව 2g', 1, 5, 73, 36, 40, 0),
	(225, 'Vendol අසමෝදගම් 215ml', 1, 5, 74, 120, 130, 0),
	(226, 'Vendol අසමෝදගම් 375ml', 1, 5, 74, 175, 190, 0),
	(227, 'හරිස්චන්ද්‍ර විනාකිරි 350ml', 1, 5, 41, 180, 210, 0),
	(228, 'හරිස්චන්ද්‍ර විනාකිරි 750ml', 1, 5, 41, 265, 310, 0),
	(229, 'හරිස්චන්ද්‍ර ප්ලේන් noodles 400g', 1, 5, 75, 240, 270, 0),
	(230, 'හරිස්චන්ද්‍ර උදු පිටි 200g', 1, 5, 76, 385, 425, 0),
	(231, 'හරිස්චන්ද්‍ර ආප්ප පිටි 400g', 1, 5, 76, 220, 250, 0),
	(232, 'හරිස්චන්ද්‍ර තොසෙ පිටි 400g', 1, 5, 76, 390, 440, 0),
	(233, 'ලුණු කුඩු 400g', 1, 5, 77, 95, 115, 0),
	(234, 'ලුණු කැට 1kg', 1, 5, 77, 155, 170, 0),
	(235, 'ලුණු කුඩු 1kg', 1, 5, 77, 200, 230, 0),
	(236, 'Ocean star jack mackerel', 1, 5, 78, 490, 540, 0),
	(237, 'MIlan පැස්ටා 400g', 1, 5, 79, 240, 290, 0),
	(238, 'MIlan පැස්ටා 1kg', 1, 5, 79, 460, 550, 0),
	(239, 'Ravan Hair colour', 1, 5, 80, 230, 260, 0),
	(240, 'Arya', 1, 5, 81, 55, 60, 0),
	(245, 'හාල් මැස්සො 1KG', 1000, 5, 84, 1200, 1300, 0),
	(246, 'හාල් මැස්සො 100G', 100, 5, 84, 200, 300, 0),
	(247, 'හාල් මැස්සො 1KG', 1000, 5, 85, 1200, 1300, 0),
	(248, 'හාල් මැස්සො 100G', 100, 5, 85, 200, 300, 60);

-- Dumping structure for table quantum_retail_pro.product_distribute
CREATE TABLE IF NOT EXISTS `product_distribute` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` varchar(45) NOT NULL,
  `employee_id` int NOT NULL,
  `receiver` varchar(50) NOT NULL,
  `location_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_distribute_employee1_idx` (`employee_id`),
  KEY `fk_product_distribute_location1_idx` (`location_id`),
  CONSTRAINT `fk_product_distribute_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_product_distribute_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_distribute: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.product_distribute_item
CREATE TABLE IF NOT EXISTS `product_distribute_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` double NOT NULL,
  `product_distribute_id` int NOT NULL,
  `product_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_distribute_item_product1_idx` (`product_id`),
  KEY `fk_product_distribute_item_product_distribute1_idx` (`product_distribute_id`),
  CONSTRAINT `fk_product_distribute_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_distribute_item_product_distribute1` FOREIGN KEY (`product_distribute_id`) REFERENCES `product_distribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_distribute_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.product_has_barcode
CREATE TABLE IF NOT EXISTS `product_has_barcode` (
  `id` int NOT NULL AUTO_INCREMENT,
  `barcode` varchar(45) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_has_barcode_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_has_barcode_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table quantum_retail_pro.product_has_barcode: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.product_has_product_type
CREATE TABLE IF NOT EXISTS `product_has_product_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `product_type_id` int NOT NULL,
  `reference_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_has_product_type_product1_idx` (`product_id`),
  KEY `fk_product_has_product_type_product_type1_idx` (`product_type_id`),
  KEY `fk_product_has_product_type_product2_idx` (`reference_id`),
  CONSTRAINT `fk_product_has_product_type_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_has_product_type_product2` FOREIGN KEY (`reference_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_product_has_product_type_product_type1` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_has_product_type: ~237 rows (approximately)
INSERT INTO `product_has_product_type` (`id`, `product_id`, `product_type_id`, `reference_id`) VALUES
	(4, 4, 1, 4),
	(5, 5, 1, 5),
	(6, 6, 1, 6),
	(7, 7, 1, 7),
	(8, 8, 1, 8),
	(9, 9, 1, 9),
	(10, 10, 1, 10),
	(11, 11, 1, 11),
	(12, 12, 1, 12),
	(13, 13, 1, 13),
	(14, 14, 1, 14),
	(15, 15, 1, 15),
	(16, 16, 1, 16),
	(17, 17, 1, 17),
	(18, 18, 1, 18),
	(19, 19, 1, 19),
	(20, 20, 1, 20),
	(21, 21, 1, 21),
	(22, 22, 1, 22),
	(23, 23, 1, 23),
	(24, 24, 1, 24),
	(25, 25, 1, 25),
	(26, 26, 1, 26),
	(27, 27, 1, 27),
	(28, 28, 1, 28),
	(29, 29, 1, 29),
	(30, 30, 1, 30),
	(31, 31, 1, 31),
	(32, 32, 1, 32),
	(33, 33, 1, 33),
	(34, 34, 1, 34),
	(35, 35, 1, 35),
	(36, 36, 1, 36),
	(37, 37, 1, 37),
	(38, 38, 1, 38),
	(39, 39, 1, 39),
	(40, 40, 1, 40),
	(41, 41, 1, 41),
	(42, 42, 1, 42),
	(43, 43, 1, 43),
	(44, 44, 1, 44),
	(45, 45, 1, 45),
	(46, 46, 1, 46),
	(47, 47, 1, 47),
	(48, 48, 1, 48),
	(49, 49, 1, 49),
	(50, 50, 1, 50),
	(51, 51, 1, 51),
	(52, 52, 1, 52),
	(53, 53, 1, 53),
	(54, 54, 1, 54),
	(55, 55, 1, 55),
	(56, 56, 1, 56),
	(57, 57, 1, 57),
	(58, 58, 1, 58),
	(59, 59, 1, 59),
	(60, 60, 1, 60),
	(61, 61, 1, 61),
	(62, 62, 1, 62),
	(63, 63, 1, 63),
	(64, 64, 1, 64),
	(65, 65, 1, 65),
	(66, 66, 1, 66),
	(67, 67, 1, 67),
	(68, 68, 1, 68),
	(69, 69, 1, 69),
	(70, 70, 1, 70),
	(71, 71, 1, 71),
	(72, 72, 1, 72),
	(73, 73, 1, 73),
	(74, 74, 1, 74),
	(75, 75, 1, 75),
	(76, 76, 1, 76),
	(77, 77, 1, 77),
	(78, 78, 1, 78),
	(79, 79, 1, 79),
	(80, 80, 1, 80),
	(81, 81, 1, 81),
	(82, 82, 1, 82),
	(83, 83, 1, 83),
	(84, 84, 1, 84),
	(85, 85, 1, 85),
	(86, 86, 1, 86),
	(87, 87, 1, 87),
	(88, 88, 1, 88),
	(89, 89, 1, 89),
	(90, 90, 1, 90),
	(91, 91, 1, 91),
	(92, 92, 1, 92),
	(93, 93, 1, 93),
	(94, 94, 1, 94),
	(95, 95, 1, 95),
	(96, 96, 1, 96),
	(97, 97, 1, 97),
	(98, 98, 1, 98),
	(99, 99, 1, 99),
	(100, 100, 1, 100),
	(101, 101, 1, 101),
	(102, 102, 1, 102),
	(103, 103, 1, 103),
	(104, 104, 1, 104),
	(105, 105, 1, 105),
	(106, 106, 1, 106),
	(107, 107, 1, 107),
	(108, 108, 1, 108),
	(109, 109, 1, 109),
	(110, 110, 1, 110),
	(111, 111, 1, 111),
	(112, 112, 1, 112),
	(113, 113, 1, 113),
	(114, 114, 1, 114),
	(115, 115, 1, 115),
	(116, 116, 1, 116),
	(117, 117, 1, 117),
	(118, 118, 1, 118),
	(119, 119, 1, 119),
	(120, 120, 1, 120),
	(121, 121, 1, 121),
	(122, 122, 1, 122),
	(123, 123, 1, 123),
	(124, 124, 1, 124),
	(125, 125, 1, 125),
	(126, 126, 1, 126),
	(127, 127, 1, 127),
	(128, 128, 1, 128),
	(129, 129, 1, 129),
	(130, 130, 1, 130),
	(131, 131, 1, 131),
	(132, 132, 1, 132),
	(133, 133, 1, 133),
	(134, 134, 1, 134),
	(135, 135, 1, 135),
	(136, 136, 1, 136),
	(137, 137, 1, 137),
	(138, 138, 1, 138),
	(139, 139, 1, 139),
	(140, 140, 1, 140),
	(141, 141, 1, 141),
	(142, 142, 1, 142),
	(143, 143, 1, 143),
	(144, 144, 1, 144),
	(145, 145, 1, 145),
	(146, 146, 1, 146),
	(147, 147, 1, 147),
	(148, 148, 1, 148),
	(149, 149, 1, 149),
	(150, 150, 1, 150),
	(151, 151, 1, 151),
	(152, 152, 1, 152),
	(153, 153, 1, 153),
	(154, 154, 1, 154),
	(155, 155, 1, 155),
	(156, 156, 1, 156),
	(157, 157, 1, 157),
	(158, 158, 1, 158),
	(159, 159, 1, 159),
	(160, 160, 1, 160),
	(161, 161, 1, 161),
	(162, 162, 1, 162),
	(163, 163, 1, 163),
	(164, 164, 1, 164),
	(165, 165, 1, 165),
	(166, 166, 1, 166),
	(167, 167, 1, 167),
	(168, 168, 1, 168),
	(169, 169, 1, 169),
	(170, 170, 1, 170),
	(171, 171, 1, 171),
	(172, 172, 1, 172),
	(173, 173, 1, 173),
	(174, 174, 1, 174),
	(175, 175, 1, 175),
	(176, 176, 1, 176),
	(177, 177, 1, 177),
	(178, 178, 1, 178),
	(179, 179, 1, 179),
	(180, 180, 1, 180),
	(181, 181, 1, 181),
	(182, 182, 1, 182),
	(183, 183, 1, 183),
	(184, 184, 1, 184),
	(185, 185, 1, 185),
	(186, 186, 1, 186),
	(187, 187, 1, 187),
	(188, 188, 1, 188),
	(189, 189, 1, 189),
	(190, 190, 1, 190),
	(191, 191, 1, 191),
	(192, 192, 1, 192),
	(193, 193, 1, 193),
	(194, 194, 1, 194),
	(195, 195, 1, 195),
	(196, 196, 1, 196),
	(197, 197, 1, 197),
	(198, 198, 1, 198),
	(199, 199, 1, 199),
	(200, 200, 1, 200),
	(201, 201, 1, 201),
	(202, 202, 1, 202),
	(203, 203, 1, 203),
	(204, 204, 1, 204),
	(205, 205, 1, 205),
	(206, 206, 1, 206),
	(207, 207, 1, 207),
	(208, 208, 1, 208),
	(209, 209, 1, 209),
	(210, 210, 1, 210),
	(211, 211, 1, 211),
	(212, 212, 1, 212),
	(213, 213, 1, 213),
	(214, 214, 1, 214),
	(215, 215, 1, 215),
	(216, 216, 1, 216),
	(217, 217, 1, 217),
	(218, 218, 1, 218),
	(219, 219, 1, 219),
	(220, 220, 1, 220),
	(221, 221, 1, 221),
	(222, 222, 1, 222),
	(223, 223, 1, 223),
	(224, 224, 1, 224),
	(225, 225, 1, 225),
	(226, 226, 1, 226),
	(227, 227, 1, 227),
	(228, 228, 1, 228),
	(229, 229, 1, 229),
	(230, 230, 1, 230),
	(231, 231, 1, 231),
	(232, 232, 1, 232),
	(233, 233, 1, 233),
	(234, 234, 1, 234),
	(235, 235, 1, 235),
	(236, 236, 1, 236),
	(237, 237, 1, 237),
	(238, 238, 1, 238),
	(239, 239, 1, 239),
	(240, 240, 1, 240),
	(243, 245, 1, 245),
	(244, 247, 1, 247),
	(245, 248, 2, 247);

-- Dumping structure for table quantum_retail_pro.product_status
CREATE TABLE IF NOT EXISTS `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_status: ~2 rows (approximately)
INSERT INTO `product_status` (`id`, `status`) VALUES
	(2, 'inactive'),
	(5, 'active');

-- Dumping structure for table quantum_retail_pro.product_type
CREATE TABLE IF NOT EXISTS `product_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL COMMENT 'parent, child',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_type: ~2 rows (approximately)
INSERT INTO `product_type` (`id`, `type`) VALUES
	(1, 'parent'),
	(2, 'child');

-- Dumping structure for table quantum_retail_pro.refund
CREATE TABLE IF NOT EXISTS `refund` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `comission` double NOT NULL,
  `refund_amount` double NOT NULL,
  `refund_status_id` int NOT NULL,
  `session_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_refund_refund_status1_idx` (`refund_status_id`),
  KEY `fk_refund_session1_idx` (`session_id`),
  CONSTRAINT `fk_refund_refund_status1` FOREIGN KEY (`refund_status_id`) REFERENCES `refund_status` (`id`),
  CONSTRAINT `fk_refund_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.refund: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.refund_item
CREATE TABLE IF NOT EXISTS `refund_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` double NOT NULL,
  `refund_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_refund_item_product1_idx` (`product_id`),
  KEY `fk_refund_item_refund1_idx` (`refund_id`),
  CONSTRAINT `fk_refund_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_refund_item_refund1` FOREIGN KEY (`refund_id`) REFERENCES `refund` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.refund_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.refund_status
CREATE TABLE IF NOT EXISTS `refund_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'default, damage',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.refund_status: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.session
CREATE TABLE IF NOT EXISTS `session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day_in_time` datetime NOT NULL,
  `day_out_time` datetime DEFAULT NULL,
  `petty_cash` double NOT NULL,
  `collection` double DEFAULT NULL,
  `employee_id` int NOT NULL,
  `status` varchar(3) DEFAULT NULL COMMENT 'ON & OFF',
  PRIMARY KEY (`id`),
  KEY `fk_session_employee_idx` (`employee_id`),
  CONSTRAINT `fk_session_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.session: ~2 rows (approximately)
INSERT INTO `session` (`id`, `day_in_time`, `day_out_time`, `petty_cash`, `collection`, `employee_id`, `status`) VALUES
	(1, '2026-02-05 17:53:29', NULL, 6500, NULL, 2, 'ON'),
	(2, '2026-02-06 10:19:49', NULL, 6500, NULL, 2, 'ON'),
	(3, '2026-02-07 18:45:52', NULL, 6500, NULL, 2, 'ON'),
	(4, '2026-02-08 08:44:29', NULL, 6500, NULL, 2, 'ON'),
	(5, '2026-02-18 19:32:58', NULL, 6500, NULL, 2, 'ON'),
	(6, '2026-03-01 21:53:02', '2026-03-01 22:02:37', 6500, 7000, 2, 'OFF');

-- Dumping structure for table quantum_retail_pro.stock
CREATE TABLE IF NOT EXISTS `stock` (
  `batch_id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `cost_price` double NOT NULL,
  `sale_price` double NOT NULL,
  `discount` double NOT NULL,
  `received_date` date NOT NULL,
  `expire_date` date NOT NULL,
  `product_id` int NOT NULL,
  `stock_status_id` int NOT NULL,
  `grn_id` int DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `barcode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`batch_id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  KEY `fk_stock_stock_status1_idx` (`stock_status_id`),
  KEY `fk_stock_grn1_idx` (`grn_id`),
  KEY `fk_stock_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_stock_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_stock_status1` FOREIGN KEY (`stock_status_id`) REFERENCES `stock_status` (`id`),
  CONSTRAINT `fk_stock_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock: ~3 rows (approximately)
INSERT INTO `stock` (`batch_id`, `qty`, `cost_price`, `sale_price`, `discount`, `received_date`, `expire_date`, `product_id`, `stock_status_id`, `grn_id`, `supplier_id`, `barcode`) VALUES
	(2, 50, 1050, 1200, 0, '2026-02-08', '2026-03-10', 247, 1, NULL, NULL, '321654'),
	(3, 50, 100, 150, 50, '2026-02-08', '2026-03-20', 18, 1, NULL, NULL, '321654987'),
	(4, 50, 200, 210, 0, '2026-03-01', '2026-03-15', 168, 1, NULL, NULL, '');

-- Dumping structure for table quantum_retail_pro.stock_adjustment
CREATE TABLE IF NOT EXISTS `stock_adjustment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `employee_id` int NOT NULL,
  `location` varchar(45) NOT NULL COMMENT 'main store (stock table)\\\\nmain cashier (stock table)',
  `reason` text NOT NULL,
  `stock_batch_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_adjustment_employee1_idx` (`employee_id`),
  KEY `fk_stock_adjustment_stock1_idx` (`stock_batch_id`),
  CONSTRAINT `fk_stock_adjustment_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_stock_adjustment_stock1` FOREIGN KEY (`stock_batch_id`) REFERENCES `stock` (`batch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock_adjustment: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.stock_adjustment_item
CREATE TABLE IF NOT EXISTS `stock_adjustment_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock_adjustment_id` int NOT NULL,
  `qty` double NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_adjustment_item_stock_adjustment1_idx` (`stock_adjustment_id`),
  KEY `fk_stock_adjustment_item_product1_idx` (`product_id`),
  CONSTRAINT `fk_stock_adjustment_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_adjustment_item_stock_adjustment1` FOREIGN KEY (`stock_adjustment_id`) REFERENCES `stock_adjustment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock_adjustment_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.stock_status
CREATE TABLE IF NOT EXISTS `stock_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive, temporary',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock_status: ~3 rows (approximately)
INSERT INTO `stock_status` (`id`, `status`) VALUES
	(1, 'active'),
	(2, 'inactive'),
	(3, 'temporary');

-- Dumping structure for table quantum_retail_pro.supplier
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `supplier_status_id` int NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_supplier_status1_idx` (`supplier_status_id`),
  KEY `fk_supplier_company1_idx` (`company_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_supplier_supplier_status1` FOREIGN KEY (`supplier_status_id`) REFERENCES `supply_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier: ~0 rows (approximately)
INSERT INTO `supplier` (`id`, `name`, `telephone`, `supplier_status_id`, `company_id`) VALUES
	(1, 'ABC', '0719892932', 1, 1);

-- Dumping structure for table quantum_retail_pro.supplier_damage_return
CREATE TABLE IF NOT EXISTS `supplier_damage_return` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `supplier_id` int NOT NULL,
  `reason` text NOT NULL,
  `supply_damage_return_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_damage_return_supplier1_idx` (`supplier_id`),
  KEY `fk_supplier_damage_return_supply_damage_return_status1_idx` (`supply_damage_return_status_id`),
  CONSTRAINT `fk_supplier_damage_return_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`),
  CONSTRAINT `fk_supplier_damage_return_supply_damage_return_status1` FOREIGN KEY (`supply_damage_return_status_id`) REFERENCES `supply_damage_return_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier_damage_return: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.supplier_damage_return_item
CREATE TABLE IF NOT EXISTS `supplier_damage_return_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `return_price` double NOT NULL,
  `product_id` int NOT NULL,
  `supplier_damage_return_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_damage_return_item_product1_idx` (`product_id`),
  KEY `fk_supplier_damage_return_item_supplier_damage_return1_idx` (`supplier_damage_return_id`),
  CONSTRAINT `fk_supplier_damage_return_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_supplier_damage_return_item_supplier_damage_return1` FOREIGN KEY (`supplier_damage_return_id`) REFERENCES `supplier_damage_return` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier_damage_return_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.supplier_order
CREATE TABLE IF NOT EXISTS `supplier_order` (
  `id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `required_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_order_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_supplier_order_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier_order: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.supplier_order_item
CREATE TABLE IF NOT EXISTS `supplier_order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `supplier_order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_order_item_supplier_order1_idx` (`supplier_order_id`),
  KEY `fk_supplier_order_item_product1_idx` (`product_id`),
  CONSTRAINT `fk_supplier_order_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_supplier_order_item_supplier_order1` FOREIGN KEY (`supplier_order_id`) REFERENCES `supplier_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier_order_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.supply_damage_return_status
CREATE TABLE IF NOT EXISTS `supply_damage_return_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'pending, returned',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supply_damage_return_status: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.supply_status
CREATE TABLE IF NOT EXISTS `supply_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supply_status: ~2 rows (approximately)
INSERT INTO `supply_status` (`id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Inactive');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

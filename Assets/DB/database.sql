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
CREATE DATABASE IF NOT EXISTS `quantum_retail_pro` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quantum_retail_pro`;

-- Dumping structure for table quantum_retail_pro.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) NOT NULL,
  `product_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_brand_product_status1_idx` (`product_status_id`),
  CONSTRAINT `fk_brand_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.brand: ~0 rows (approximately)
INSERT INTO `brand` (`id`, `brand`, `product_status_id`) VALUES
	(11, 'brand-1', 3);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.category: ~0 rows (approximately)
INSERT INTO `category` (`id`, `category`, `product_status_id`) VALUES
	(8, 'category-1', 3);

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.category_has_brand: ~0 rows (approximately)
INSERT INTO `category_has_brand` (`id`, `category_id`, `brand_id`) VALUES
	(12, 8, 11);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.close_sale: ~0 rows (approximately)

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.company: ~0 rows (approximately)

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee: ~3 rows (approximately)
INSERT INTO `employee` (`id`, `name`, `employee_role_id`, `employee_status_id`, `username`, `password`, `pin`) VALUES
	(1, 'Quantum B. Cashier', 5, 3, 'Cashier', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', NULL),
	(2, 'Quantum B. Admin', 4, 3, 'Admin', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', '2237'),
	(3, 'Quantum B. Developer', 6, 3, 'Developer', '$argon2i$v=19$m=65536,t=10,p=4$b8ZZHAaWLcehwMxjJ97O8w$XF9DpMU5vqEK05515IjEry3ggSwWonpprKDLhAL1XxE', '2237'),
	(9, 'SystemOwner', 4, 3, 'owner', '$argon2i$v=19$m=65536,t=10,p=4$EE3FLnvxycsmcpuBygXMaQ$+dRtc67B0mGpeYzkkVLj2KVF0EhS2UInhceKUmZO4d8', '2237');

-- Dumping structure for table quantum_retail_pro.employee_panel
CREATE TABLE IF NOT EXISTS `employee_panel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_panel: ~2 rows (approximately)
INSERT INTO `employee_panel` (`id`, `type`) VALUES
	(4, 'admin'),
	(5, 'cashier'),
	(6, 'developer');

-- Dumping structure for table quantum_retail_pro.employee_role
CREATE TABLE IF NOT EXISTS `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  `employee_panel_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_role_employee_panel1_idx` (`employee_panel_id`),
  CONSTRAINT `fk_employee_role_employee_panel1` FOREIGN KEY (`employee_panel_id`) REFERENCES `employee_panel` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_role: ~2 rows (approximately)
INSERT INTO `employee_role` (`id`, `role`, `employee_panel_id`) VALUES
	(4, 'admin', 4),
	(5, 'cashier', 5),
	(6, 'developer', 6);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.employee_status: ~2 rows (approximately)
INSERT INTO `employee_status` (`id`, `status`) VALUES
	(3, 'Active'),
	(4, 'Inactive');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.grn: ~0 rows (approximately)

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.grn_item: ~0 rows (approximately)

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.invoice_item
CREATE TABLE IF NOT EXISTS `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `sale_price` double NOT NULL,
  `cost_price` double NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice_item: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.invoice_item_type
CREATE TABLE IF NOT EXISTS `invoice_item_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'selling, returning   ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.invoice_item_type: ~0 rows (approximately)

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
  `type` varchar(45) NOT NULL COMMENT 'defauld, damage, expired\\ndefault return goes to store table\\ndamage & expired goes to damage table',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.location_supply_type: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product` varchar(45) NOT NULL,
  `measure` float NOT NULL,
  `bar_code` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product: ~3 rows (approximately)
INSERT INTO `product` (`id`, `product`, `measure`, `bar_code`, `product_status_id`, `category_has_brand_id`, `cost_price`, `sale_price`, `discount`) VALUES
	(16, 'Updated Product 2', 100, 'ABC123', 3, 12, 200, 300, 0),
	(20, 'Araliya Rice 50KG', 50000, '321654987', 3, 12, 4500, 4700, 0),
	(21, 'Araliya Rice 1KG', 1000, '', 3, 12, 1000, 1200, 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_has_product_type: ~3 rows (approximately)
INSERT INTO `product_has_product_type` (`id`, `product_id`, `product_type_id`, `reference_id`) VALUES
	(16, 16, 1, 16),
	(20, 20, 1, 20),
	(21, 21, 2, 20);

-- Dumping structure for table quantum_retail_pro.product_status
CREATE TABLE IF NOT EXISTS `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.product_status: ~2 rows (approximately)
INSERT INTO `product_status` (`id`, `status`) VALUES
	(3, 'active'),
	(4, 'inactive');

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
	(1, '2026-01-08 18:58:41', '2026-01-08 18:59:00', 12500, 35600, 1, 'OFF'),
	(2, '2026-01-09 20:55:40', NULL, 6500, NULL, 1, 'ON'),
	(3, '2026-01-11 12:01:51', NULL, 6500, NULL, 1, 'ON'),
	(4, '2026-01-14 20:34:20', NULL, 6500, NULL, 1, 'ON'),
	(5, '2026-01-15 09:15:32', NULL, 6500, NULL, 1, 'ON'),
	(6, '2026-01-17 09:16:34', NULL, 6500, NULL, 1, 'ON');

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
  `grn_id` int NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`batch_id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  KEY `fk_stock_stock_status1_idx` (`stock_status_id`),
  KEY `fk_stock_grn1_idx` (`grn_id`),
  KEY `fk_stock_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_stock_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_stock_status1` FOREIGN KEY (`stock_status_id`) REFERENCES `stock_status` (`id`),
  CONSTRAINT `fk_stock_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock: ~0 rows (approximately)

-- Dumping structure for table quantum_retail_pro.stock_adjustment
CREATE TABLE IF NOT EXISTS `stock_adjustment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `employee_id` int NOT NULL,
  `location` varchar(45) NOT NULL COMMENT 'main store (stock table)\\nmain cashier (stock table)',
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
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.stock_status: ~0 rows (approximately)

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table quantum_retail_pro.supplier: ~0 rows (approximately)

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

-- Dumping data for table quantum_retail_pro.supply_status: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

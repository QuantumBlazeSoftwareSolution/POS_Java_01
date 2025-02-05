CREATE DATABASE  IF NOT EXISTS `rertail_pos_v_01_qb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rertail_pos_v_01_qb`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: rertail_pos_v_01_qb
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cash_withdrawal`
--

DROP TABLE IF EXISTS `cash_withdrawal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cash_withdrawal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `amount` double NOT NULL,
  `reason` text NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cash_withdrawal_session1_idx` (`session_id`),
  CONSTRAINT `fk_cash_withdrawal_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cash_withdrawal`
--

LOCK TABLES `cash_withdrawal` WRITE;
/*!40000 ALTER TABLE `cash_withdrawal` DISABLE KEYS */;
/*!40000 ALTER TABLE `cash_withdrawal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  `product_status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_product_status1_idx` (`product_status_id`),
  CONSTRAINT `fk_category_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `close_sale`
--

DROP TABLE IF EXISTS `close_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `close_sale` (
  `idclose_sale` int NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `c5000` int NOT NULL,
  `c1000` int NOT NULL,
  `c500` int NOT NULL,
  `c100` int NOT NULL,
  `c50` int NOT NULL,
  `c10` int NOT NULL,
  `c5` int NOT NULL,
  `date_time` datetime NOT NULL,
  PRIMARY KEY (`idclose_sale`),
  KEY `fk_close_sale_session1_idx` (`session_id`),
  CONSTRAINT `fk_close_sale_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `close_sale`
--

LOCK TABLES `close_sale` WRITE;
/*!40000 ALTER TABLE `close_sale` DISABLE KEYS */;
/*!40000 ALTER TABLE `close_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(150) NOT NULL,
  `telephone_1` varchar(10) NOT NULL,
  `telephone_2` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `costing`
--

DROP TABLE IF EXISTS `costing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `costing` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_product` int NOT NULL,
  `child_product` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_costing_product1_idx` (`parent_product`),
  KEY `fk_costing_product2_idx` (`child_product`),
  CONSTRAINT `fk_costing_product1` FOREIGN KEY (`parent_product`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_costing_product2` FOREIGN KEY (`child_product`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costing`
--

LOCK TABLES `costing` WRITE;
/*!40000 ALTER TABLE `costing` DISABLE KEYS */;
/*!40000 ALTER TABLE `costing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctrl`
--

DROP TABLE IF EXISTS `ctrl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctrl` (
  `primary_key` varchar(10) NOT NULL,
  `inventory_system` varchar(3) NOT NULL,
  `employee_management` varchar(3) NOT NULL,
  `discount` varchar(3) NOT NULL,
  `refund` varchar(3) NOT NULL,
  `credit_payment` varchar(3) NOT NULL,
  `withdrawal` varchar(3) NOT NULL,
  PRIMARY KEY (`primary_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctrl`
--

LOCK TABLES `ctrl` WRITE;
/*!40000 ALTER TABLE `ctrl` DISABLE KEYS */;
/*!40000 ALTER TABLE `ctrl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mobile` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `credit_amount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_has_invoice`
--

DROP TABLE IF EXISTS `customer_has_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_has_invoice` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_has_invoice`
--

LOCK TABLES `customer_has_invoice` WRITE;
/*!40000 ALTER TABLE `customer_has_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_has_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `damage`
--

DROP TABLE IF EXISTS `damage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `reason` text NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_damage_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_damage_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `damage`
--

LOCK TABLES `damage` WRITE;
/*!40000 ALTER TABLE `damage` DISABLE KEYS */;
/*!40000 ALTER TABLE `damage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `damage_item`
--

DROP TABLE IF EXISTS `damage_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` double NOT NULL,
  `damage_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_damage_item_product1_idx` (`product_id`),
  KEY `fk_damage_item_damage1_idx` (`damage_id`),
  CONSTRAINT `fk_damage_item_damage1` FOREIGN KEY (`damage_id`) REFERENCES `damage` (`id`),
  CONSTRAINT `fk_damage_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `damage_item`
--

LOCK TABLES `damage_item` WRITE;
/*!40000 ALTER TABLE `damage_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `damage_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount_type`
--

DROP TABLE IF EXISTS `discount_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'percentage, cash amount',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount_type`
--

LOCK TABLES `discount_type` WRITE;
/*!40000 ALTER TABLE `discount_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distribute_type`
--

DROP TABLE IF EXISTS `distribute_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `distribute_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'primary, secondary',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distribute_type`
--

LOCK TABLES `distribute_type` WRITE;
/*!40000 ALTER TABLE `distribute_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `distribute_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `employee_role_id` int NOT NULL,
  `employee_status_id` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(20) NOT NULL,
  `employee_panel_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_employee_role1_idx` (`employee_role_id`),
  KEY `fk_employee_employee_status1_idx` (`employee_status_id`),
  KEY `fk_employee_employee_panel1_idx` (`employee_panel_id`),
  CONSTRAINT `fk_employee_employee_panel1` FOREIGN KEY (`employee_panel_id`) REFERENCES `employee_panel` (`id`),
  CONSTRAINT `fk_employee_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_role` (`id`),
  CONSTRAINT `fk_employee_employee_status1` FOREIGN KEY (`employee_status_id`) REFERENCES `employee_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_panel`
--

DROP TABLE IF EXISTS `employee_panel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_panel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `panel` varchar(45) NOT NULL COMMENT 'cashier, admin',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_panel`
--

LOCK TABLES `employee_panel` WRITE;
/*!40000 ALTER TABLE `employee_panel` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_panel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role`
--

DROP TABLE IF EXISTS `employee_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role`
--

LOCK TABLES `employee_role` WRITE;
/*!40000 ALTER TABLE `employee_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_role_has_interface`
--

DROP TABLE IF EXISTS `employee_role_has_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_role_has_interface` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_role_id` int NOT NULL,
  `interface_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employee_role_has_interface_interface1_idx` (`interface_id`),
  KEY `fk_employee_role_has_interface_employee_role1_idx` (`employee_role_id`),
  CONSTRAINT `fk_employee_role_has_interface_employee_role1` FOREIGN KEY (`employee_role_id`) REFERENCES `employee_role` (`id`),
  CONSTRAINT `fk_employee_role_has_interface_interface1` FOREIGN KEY (`interface_id`) REFERENCES `interface` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_role_has_interface`
--

LOCK TABLES `employee_role_has_interface` WRITE;
/*!40000 ALTER TABLE `employee_role_has_interface` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_role_has_interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_status`
--

DROP TABLE IF EXISTS `employee_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_status`
--

LOCK TABLES `employee_status` WRITE;
/*!40000 ALTER TABLE `employee_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn`
--

DROP TABLE IF EXISTS `grn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grn_id` varchar(20) NOT NULL,
  `date_time` datetime NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_grn_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn`
--

LOCK TABLES `grn` WRITE;
/*!40000 ALTER TABLE `grn` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_item`
--

DROP TABLE IF EXISTS `grn_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `grn_id` int NOT NULL,
  `cost_price` double NOT NULL,
  `qty` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_grn_item_product1_idx` (`product_id`),
  KEY `fk_grn_item_grn1_idx` (`grn_id`),
  CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_id`) REFERENCES `grn` (`id`),
  CONSTRAINT `fk_grn_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_item`
--

LOCK TABLES `grn_item` WRITE;
/*!40000 ALTER TABLE `grn_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interface`
--

DROP TABLE IF EXISTS `interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interface` (
  `id` int NOT NULL AUTO_INCREMENT,
  `interface` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interface`
--

LOCK TABLES `interface` WRITE;
/*!40000 ALTER TABLE `interface` DISABLE KEYS */;
/*!40000 ALTER TABLE `interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `bill_amount` double NOT NULL,
  `paid_amount` double NOT NULL,
  `credit_amount` double NOT NULL,
  `session_id` int NOT NULL,
  `payment_method_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_session1_idx` (`session_id`),
  KEY `fk_invoice_payment_method1_idx` (`payment_method_id`),
  CONSTRAINT `fk_invoice_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`),
  CONSTRAINT `fk_invoice_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item`
--

DROP TABLE IF EXISTS `invoice_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `qty` double NOT NULL,
  `sale_price` double NOT NULL,
  `cost_price` double NOT NULL,
  `invoice_id` int NOT NULL,
  `invoice_item_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_item_product1_idx` (`product_id`),
  KEY `fk_invoice_item_invoice1_idx` (`invoice_id`),
  KEY `fk_invoice_item_invoice_item_type1_idx` (`invoice_item_type_id`),
  CONSTRAINT `fk_invoice_item_invoice1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `fk_invoice_item_invoice_item_type1` FOREIGN KEY (`invoice_item_type_id`) REFERENCES `invoice_item_type` (`id`),
  CONSTRAINT `fk_invoice_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item`
--

LOCK TABLES `invoice_item` WRITE;
/*!40000 ALTER TABLE `invoice_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_item_type`
--

DROP TABLE IF EXISTS `invoice_item_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_item_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'selling, returning',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_item_type`
--

LOCK TABLES `invoice_item_type` WRITE;
/*!40000 ALTER TABLE `invoice_item_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_item_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(150) NOT NULL,
  `telephone_1` varchar(10) NOT NULL,
  `telephone_2` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_return`
--

DROP TABLE IF EXISTS `location_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_return` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `location_return_type_id` int NOT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_return_location_return_type1_idx` (`location_return_type_id`),
  KEY `fk_location_return_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_location_return_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_location_return_location_return_type1` FOREIGN KEY (`location_return_type_id`) REFERENCES `location_return_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_return`
--

LOCK TABLES `location_return` WRITE;
/*!40000 ALTER TABLE `location_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_return_has_location`
--

DROP TABLE IF EXISTS `location_return_has_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_return_has_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `location_return_id` int NOT NULL,
  `location_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_return_has_location_location1_idx` (`location_id`),
  KEY `fk_location_return_has_location_location_return1_idx` (`location_return_id`),
  CONSTRAINT `fk_location_return_has_location_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_location_return_has_location_location_return1` FOREIGN KEY (`location_return_id`) REFERENCES `location_return` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_return_has_location`
--

LOCK TABLES `location_return_has_location` WRITE;
/*!40000 ALTER TABLE `location_return_has_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_return_has_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_return_item`
--

DROP TABLE IF EXISTS `location_return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_return_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `location_return_id` int NOT NULL,
  `qty` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_location_return_item_product1_idx` (`product_id`),
  KEY `fk_location_return_item_location_return1_idx` (`location_return_id`),
  CONSTRAINT `fk_location_return_item_location_return1` FOREIGN KEY (`location_return_id`) REFERENCES `location_return` (`id`),
  CONSTRAINT `fk_location_return_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_return_item`
--

LOCK TABLES `location_return_item` WRITE;
/*!40000 ALTER TABLE `location_return_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location_return_type`
--

DROP TABLE IF EXISTS `location_return_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location_return_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL COMMENT 'defauld, damage, expired\ndefault return goes to store table\ndamage & expired goes to damage table',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location_return_type`
--

LOCK TABLES `location_return_type` WRITE;
/*!40000 ALTER TABLE `location_return_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `location_return_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(10) NOT NULL COMMENT 'Cash, credit, card',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_status_id` int NOT NULL,
  `product` varchar(45) NOT NULL,
  `sale_price` double NOT NULL,
  `cost_price` double NOT NULL,
  `discount` double NOT NULL,
  `measure` float NOT NULL,
  `category_id` int NOT NULL,
  `bar_code` varchar(45) NOT NULL,
  `product_type_id` int NOT NULL,
  `product_unit_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_product_status1_idx` (`product_status_id`),
  KEY `fk_product_category1_idx` (`category_id`),
  KEY `fk_product_product_type1_idx` (`product_type_id`),
  KEY `fk_product_product_unit1_idx` (`product_unit_id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_product_product_status1` FOREIGN KEY (`product_status_id`) REFERENCES `product_status` (`id`),
  CONSTRAINT `fk_product_product_type1` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`id`),
  CONSTRAINT `fk_product_product_unit1` FOREIGN KEY (`product_unit_id`) REFERENCES `product_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_distribute`
--

DROP TABLE IF EXISTS `product_distribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_distribute` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` varchar(45) NOT NULL,
  `employee_id` int NOT NULL,
  `receiver` varchar(50) NOT NULL,
  `distribute_type_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_distribute_employee1_idx` (`employee_id`),
  KEY `fk_product_distribute_distribute_type1_idx` (`distribute_type_id`),
  CONSTRAINT `fk_product_distribute_distribute_type1` FOREIGN KEY (`distribute_type_id`) REFERENCES `distribute_type` (`id`),
  CONSTRAINT `fk_product_distribute_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_distribute`
--

LOCK TABLES `product_distribute` WRITE;
/*!40000 ALTER TABLE `product_distribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_distribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_distribute_has_location`
--

DROP TABLE IF EXISTS `product_distribute_has_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_distribute_has_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_distribute_id` int NOT NULL,
  `location_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_issue_has_location_location1_idx` (`location_id`),
  KEY `fk_product_issue_has_location_product_issue1_idx` (`product_distribute_id`),
  CONSTRAINT `fk_product_issue_has_location_location1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_product_issue_has_location_product_issue1` FOREIGN KEY (`product_distribute_id`) REFERENCES `product_distribute` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_distribute_has_location`
--

LOCK TABLES `product_distribute_has_location` WRITE;
/*!40000 ALTER TABLE `product_distribute_has_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_distribute_has_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_distribute_item`
--

DROP TABLE IF EXISTS `product_distribute_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_distribute_item` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_distribute_item`
--

LOCK TABLES `product_distribute_item` WRITE;
/*!40000 ALTER TABLE `product_distribute_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_distribute_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_status`
--

DROP TABLE IF EXISTS `product_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_status`
--

LOCK TABLES `product_status` WRITE;
/*!40000 ALTER TABLE `product_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(10) NOT NULL COMMENT 'parent, child',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_type`
--

LOCK TABLES `product_type` WRITE;
/*!40000 ALTER TABLE `product_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_unit`
--

DROP TABLE IF EXISTS `product_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_unit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `unit` varchar(45) DEFAULT NULL COMMENT 'g, ml, units, piece',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_unit`
--

LOCK TABLES `product_unit` WRITE;
/*!40000 ALTER TABLE `product_unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund`
--

DROP TABLE IF EXISTS `refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `refund_status_id` int NOT NULL,
  `refund_amount` double NOT NULL,
  `session_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_refund_refund_status1_idx` (`refund_status_id`),
  KEY `fk_refund_session1_idx` (`session_id`),
  CONSTRAINT `fk_refund_refund_status1` FOREIGN KEY (`refund_status_id`) REFERENCES `refund_status` (`id`),
  CONSTRAINT `fk_refund_session1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund`
--

LOCK TABLES `refund` WRITE;
/*!40000 ALTER TABLE `refund` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_item`
--

DROP TABLE IF EXISTS `refund_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund_item` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_item`
--

LOCK TABLES `refund_item` WRITE;
/*!40000 ALTER TABLE `refund_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_status`
--

DROP TABLE IF EXISTS `refund_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'default, damage',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_status`
--

LOCK TABLES `refund_status` WRITE;
/*!40000 ALTER TABLE `refund_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day_in_time` datetime NOT NULL,
  `day_out_time` datetime DEFAULT NULL,
  `petty_cash` double NOT NULL,
  `collection` double DEFAULT NULL,
  `employee_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_session_employee_idx` (`employee_id`),
  CONSTRAINT `fk_session_employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_product1_idx` (`product_id`),
  CONSTRAINT `fk_stock_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_adjustment`
--

DROP TABLE IF EXISTS `stock_adjustment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_adjustment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_time` datetime NOT NULL,
  `employee_id` int NOT NULL,
  `location` varchar(45) NOT NULL COMMENT 'main store (stock table)\nmain cashier (stock table)',
  PRIMARY KEY (`id`),
  KEY `fk_stock_adjustment_employee1_idx` (`employee_id`),
  CONSTRAINT `fk_stock_adjustment_employee1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_adjustment`
--

LOCK TABLES `stock_adjustment` WRITE;
/*!40000 ALTER TABLE `stock_adjustment` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_adjustment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_adjustment_item`
--

DROP TABLE IF EXISTS `stock_adjustment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stock_adjustment_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `stock_adjustment_id` int NOT NULL,
  `qty` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_adjustment_item_product1_idx` (`product_id`),
  KEY `fk_stock_adjustment_item_stock_adjustment1_idx` (`stock_adjustment_id`),
  CONSTRAINT `fk_stock_adjustment_item_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_stock_adjustment_item_stock_adjustment1` FOREIGN KEY (`stock_adjustment_id`) REFERENCES `stock_adjustment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_adjustment_item`
--

LOCK TABLES `stock_adjustment_item` WRITE;
/*!40000 ALTER TABLE `stock_adjustment_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_adjustment_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `id` int NOT NULL AUTO_INCREMENT,
  `qty` double NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_store_product1_idx` (`product_id`),
  CONSTRAINT `fk_store_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `supplier_status_id` int NOT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_supplier_status1_idx` (`supplier_status_id`),
  KEY `fk_supplier_company1_idx` (`company_id`),
  CONSTRAINT `fk_supplier_company1` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_supplier_supplier_status1` FOREIGN KEY (`supplier_status_id`) REFERENCES `supplier_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_damage_return`
--

DROP TABLE IF EXISTS `supplier_damage_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_damage_return` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `supplier_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_damage_return_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_supplier_damage_return_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_damage_return`
--

LOCK TABLES `supplier_damage_return` WRITE;
/*!40000 ALTER TABLE `supplier_damage_return` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_damage_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_damage_return_item`
--

DROP TABLE IF EXISTS `supplier_damage_return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_damage_return_item` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_damage_return_item`
--

LOCK TABLES `supplier_damage_return_item` WRITE;
/*!40000 ALTER TABLE `supplier_damage_return_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_damage_return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_order`
--

DROP TABLE IF EXISTS `supplier_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_order` (
  `id` int NOT NULL,
  `supplier_id` int NOT NULL,
  `required_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_supplier_order_supplier1_idx` (`supplier_id`),
  CONSTRAINT `fk_supplier_order_supplier1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_order`
--

LOCK TABLES `supplier_order` WRITE;
/*!40000 ALTER TABLE `supplier_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_order_item`
--

DROP TABLE IF EXISTS `supplier_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_order_item` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_order_item`
--

LOCK TABLES `supplier_order_item` WRITE;
/*!40000 ALTER TABLE `supplier_order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_status`
--

DROP TABLE IF EXISTS `supplier_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL COMMENT 'active, inactive',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_status`
--

LOCK TABLES `supplier_status` WRITE;
/*!40000 ALTER TABLE `supplier_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplier_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system`
--

DROP TABLE IF EXISTS `system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system` (
  `primary_key` varchar(10) NOT NULL,
  `system_name` varchar(45) NOT NULL,
  `initial_date` date NOT NULL,
  `deactive_date` datetime NOT NULL,
  `telephone_1` varchar(10) NOT NULL,
  `telephone_2` varchar(10) NOT NULL,
  `address` varchar(100) NOT NULL,
  `discount_amount` double NOT NULL,
  `discount_percentage` double NOT NULL,
  `discount_range` double NOT NULL,
  `discount_type_id` int NOT NULL,
  PRIMARY KEY (`primary_key`),
  KEY `fk_system_discount_type1_idx` (`discount_type_id`),
  CONSTRAINT `fk_system_discount_type1` FOREIGN KEY (`discount_type_id`) REFERENCES `discount_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system`
--

LOCK TABLES `system` WRITE;
/*!40000 ALTER TABLE `system` DISABLE KEYS */;
/*!40000 ALTER TABLE `system` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-05 16:35:11

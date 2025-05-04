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

-- Dumping data for table quantum_retail_pro.employee: ~3 rows (approximately)
INSERT INTO `employee` (`id`, `name`, `employee_role_id`, `employee_status_id`, `username`, `password`) VALUES
	(1, 'Quantum B. Cashier', 1, 1, 'Cashier', '$argon2i$v=19$m=65536,t=10,p=4$QpGX9iOw9coU7csvYd6+aQ$chWoqks/izwaihIDB6WG3U4lFU0lpNsbfzzFbnpqlSU'),
	(2, 'Quantum B. Admin', 2, 1, 'Admin', '$argon2i$v=19$m=65536,t=10,p=4$QpGX9iOw9coU7csvYd6+aQ$chWoqks/izwaihIDB6WG3U4lFU0lpNsbfzzFbnpqlSU'),
	(3, 'Quantum B. Developer', 3, 1, 'Developer', '$argon2i$v=19$m=65536,t=10,p=4$QpGX9iOw9coU7csvYd6+aQ$chWoqks/izwaihIDB6WG3U4lFU0lpNsbfzzFbnpqlSU');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

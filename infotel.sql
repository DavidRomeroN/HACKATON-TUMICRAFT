-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 03, 2025 at 09:54 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `infotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `productos`
--

CREATE TABLE `productos` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `genero_destino` varchar(255) DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `clima_apropiado` varchar(255) DEFAULT NULL,
  `clima` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `url_imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `productos`
--

INSERT INTO `productos` (`id`, `nombre`, `categoria`, `genero_destino`, `material`, `clima_apropiado`, `clima`, `genero`, `url_imagen`) VALUES
(40, 'Poncho Tortuga', 'Poncho', 'Unisex', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748925936/vjtrz2eq58pkvwbvgnvh.png'),
(41, 'Poncho Sada', 'Poncho', 'Femenino', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748926837/ponsada_bz8d2c.png'),
(42, 'Poncho Sada', 'Poncho', 'femenino', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748926837/ponsada_bz8d2c.png'),
(43, 'Ponchos Andinos', 'Poncho', 'femenino', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927034/ponandinos_qoztbt.png'),
(44, 'Chaleco Cielo', 'Chaleco', 'femenino', 'Alpacril', 'calor', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927116/calecocielo_lkgi1h.png'),
(45, 'Sweater Capucha - Varón', 'Sweater', 'masculino', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927256/capuchavaron_occpuf.png'),
(46, 'Chompa térmica', 'Abrigo', 'unisex', 'Lana', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927342/puntovaron_tsy8xm.png'),
(47, 'Poncho - Varón', 'Poncho', 'masculino', 'Alpacril', 'frio', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927422/ponchovaron_wrw8is.png'),
(48, 'Sweater Unisex con y sin personalizado', 'Sweater', 'Unisex', 'Algodón/Sintético', 'calor', NULL, NULL, 'https://res.cloudinary.com/dxvdbp7zk/image/upload/v1748927553/sweater_pshihv.png');

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `latitud` double DEFAULT NULL,
  `longitud` double DEFAULT NULL,
  `estilo_preferido` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `latitud`, `longitud`, `estilo_preferido`, `genero`) VALUES
(1, 'Juana', 'juana@gmail.com', -15.5, -70.1, 'elegante', 'femenino'),
(2, 'David', 'david@gmail.com', -15.48304973387583, -70.12780762576061, 'elegante', 'masculino'),
(3, 'Gloria', 'Gloria@gmail.com', 30.49229785617472, 8.152952070957816, 'elegante', 'femenino');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `productos`
--
ALTER TABLE `productos`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

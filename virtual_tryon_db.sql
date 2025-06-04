-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 04, 2025 at 04:23 AM
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
-- Database: `virtual_tryon_db`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `cleanup_old_sessions` ()   BEGIN
    DELETE FROM tryon_sessions
    WHERE created_at < (NOW() - INTERVAL 30 DAY);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `clothing`
--

CREATE TABLE `clothing` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `category` enum('SHIRT','T_SHIRT','BLOUSE','SWEATER','CARDIGAN','HOODIE','JACKET','BLAZER','COAT','PANTS','JEANS','SHORTS','SKIRT','LEGGINGS','DRESS','JUMPSUIT','PONCHO','CAPE','VEST','HAT','SCARF','BELT','SHOES','BOOTS','SNEAKERS','SANDALS') COLLATE utf8mb4_unicode_ci NOT NULL,
  `image_url` text COLLATE utf8mb4_unicode_ci,
  `model_url` text COLLATE utf8mb4_unicode_ci,
  `texture_url` text COLLATE utf8mb4_unicode_ci,
  `color` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` enum('XS','S','M','L','XL','XXL') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_public` tinyint(1) DEFAULT '1',
  `is_active` tinyint(1) DEFAULT '1',
  `price` double DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `clothing`
--

INSERT INTO `clothing` (`id`, `user_id`, `name`, `description`, `category`, `image_url`, `model_url`, `texture_url`, `color`, `size`, `is_public`, `is_active`, `price`, `created_at`) VALUES
(1, 1, 'Camiseta Básica Blanca', 'Camiseta de algodón 100% cómoda y versátil', 'T_SHIRT', NULL, NULL, NULL, 'Blanco', 'M', 1, 1, 25.99, '2025-06-03 07:28:08'),
(2, 1, 'Jeans Clásicos Azules', 'Jeans de corte recto en denim premium', 'JEANS', NULL, NULL, NULL, 'Azul', 'M', 1, 1, 79.99, '2025-06-03 07:28:08'),
(3, 1, 'Vestido Elegante Negro', 'Vestido de noche sofisticado y elegante', 'DRESS', NULL, NULL, NULL, 'Negro', 'M', 1, 1, 149.99, '2025-06-03 07:28:08'),
(4, 1, 'Sudadera Gris con Capucha', 'Sudadera cómoda perfecta para días casuales', 'HOODIE', NULL, NULL, NULL, 'Gris', 'L', 1, 1, 45.99, '2025-06-03 07:28:08'),
(5, 1, 'Falda Plisada Rosa', 'Falda midi con pliegues en tono pastel', 'SKIRT', NULL, NULL, NULL, 'Rosa', 'S', 1, 1, 35.99, '2025-06-03 07:28:08'),
(6, 1, 'Blazer Negro Formal', 'Blazer estructurado para ocasiones formales', 'BLAZER', NULL, NULL, NULL, 'Negro', 'M', 1, 1, 129.99, '2025-06-03 07:28:08'),
(7, 1, 'Poncho Tejido Beige', 'Poncho artesanal de lana suave', 'PONCHO', 'https://res.cloudinary.com/ddkdsx3fg/image/upload/v1747691164/ixuay7gleibdbx5uz1sv.jpg', NULL, NULL, 'Beige', 'M', 1, 1, 65.99, '2025-06-03 07:28:08'),
(8, 1, 'Cardigan de Punto Azul', 'Cardigan clásico de punto fino', 'CARDIGAN', 'https://res.cloudinary.com/ddkdsx3fg/image/upload/v1748982975/sweater_qddghe.glb', NULL, NULL, 'Azul Marino', 'L', 1, 1, 55.99, '2025-06-03 07:28:08');

-- --------------------------------------------------------

--
-- Table structure for table `clothing_collections`
--

CREATE TABLE `clothing_collections` (
  `id` bigint NOT NULL,
  `banner_image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `display_order` int DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_featured` bit(1) DEFAULT NULL,
  `launch_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `season` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `short_description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `slug` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thumbnail_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `year` varchar(4) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clothing_dimensions`
--

CREATE TABLE `clothing_dimensions` (
  `id` bigint NOT NULL,
  `arm_hole_cm` decimal(5,2) DEFAULT NULL,
  `chest_cm` decimal(5,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `hip_cm` decimal(5,2) DEFAULT NULL,
  `inseam_cm` decimal(5,2) DEFAULT NULL,
  `length_cm` decimal(5,2) DEFAULT NULL,
  `measurement_unit` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `neck_cm` decimal(5,2) DEFAULT NULL,
  `shoulder_cm` decimal(5,2) DEFAULT NULL,
  `size` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sleeve_cm` decimal(5,2) DEFAULT NULL,
  `thigh_cm` decimal(5,2) DEFAULT NULL,
  `waist_cm` decimal(5,2) DEFAULT NULL,
  `clothing_item_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clothing_images`
--

CREATE TABLE `clothing_images` (
  `id` bigint NOT NULL,
  `alt_text` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `display_order` int DEFAULT NULL,
  `file_size` bigint DEFAULT NULL,
  `height` int DEFAULT NULL,
  `image_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `thumbnail_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `width` int DEFAULT NULL,
  `clothing_item_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clothing_items`
--

CREATE TABLE `clothing_items` (
  `id` bigint NOT NULL,
  `age_group` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anchor_points` text COLLATE utf8mb4_unicode_ci,
  `ar_scale` double DEFAULT NULL,
  `available_for_ar` bit(1) DEFAULT NULL,
  `average_rating` decimal(3,2) DEFAULT NULL,
  `brand` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `care_instructions` text COLLATE utf8mb4_unicode_ci,
  `category` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `clothing_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `currency` varchar(3) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `discontinued_at` datetime(6) DEFAULT NULL,
  `discount_percentage` int DEFAULT NULL,
  `fit_type` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_bestseller` bit(1) DEFAULT NULL,
  `is_featured` bit(1) DEFAULT NULL,
  `is_new_arrival` bit(1) DEFAULT NULL,
  `is_on_sale` bit(1) DEFAULT NULL,
  `launched_at` datetime(6) DEFAULT NULL,
  `material` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `min_stock_level` int DEFAULT NULL,
  `model_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `normal_map_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `occasion` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `original_price` decimal(10,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `purchase_count` bigint DEFAULT NULL,
  `rating_count` int DEFAULT NULL,
  `season` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `short_description` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `size` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `style` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subcategory` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `texture_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thumbnail_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `try_on_count` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `view_count` bigint DEFAULT NULL,
  `collection_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clothing_reviews`
--

CREATE TABLE `clothing_reviews` (
  `id` bigint NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `created_at` datetime(6) DEFAULT NULL,
  `fit_rating` int DEFAULT NULL,
  `helpful_votes` int DEFAULT NULL,
  `is_approved` bit(1) DEFAULT NULL,
  `quality_rating` int DEFAULT NULL,
  `rating` int NOT NULL,
  `size_purchased` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_votes` int DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value_rating` int DEFAULT NULL,
  `verified_purchase` bit(1) DEFAULT NULL,
  `clothing_item_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `clothing_stats`
-- (See below for the actual view)
--
CREATE TABLE `clothing_stats` (
`category` enum('SHIRT','T_SHIRT','BLOUSE','SWEATER','CARDIGAN','HOODIE','JACKET','BLAZER','COAT','PANTS','JEANS','SHORTS','SKIRT','LEGGINGS','DRESS','JUMPSUIT','PONCHO','CAPE','VEST','HAT','SCARF','BELT','SHOES','BOOTS','SNEAKERS','SANDALS')
,`total_items` bigint
,`public_items` decimal(23,0)
,`avg_price` double
,`min_price` double
,`max_price` double
);

-- --------------------------------------------------------

--
-- Table structure for table `clothing_tags`
--

CREATE TABLE `clothing_tags` (
  `clothing_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `clothing_variants`
--

CREATE TABLE `clothing_variants` (
  `id` bigint NOT NULL,
  `barcode` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color_hex` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `price_adjustment` decimal(10,2) DEFAULT NULL,
  `reserved_quantity` int DEFAULT NULL,
  `size` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sku` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `variant_image_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `variant_model_url` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `weight_grams` int DEFAULT NULL,
  `clothing_item_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `id` bigint NOT NULL,
  `color_code` varchar(7) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tag_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usage_count` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tryon_sessions`
--

CREATE TABLE `tryon_sessions` (
  `id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `clothing_id` bigint DEFAULT NULL,
  `session_data` text COLLATE utf8mb4_unicode_ci,
  `screenshot_url` text COLLATE utf8mb4_unicode_ci,
  `duration` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') COLLATE utf8mb4_unicode_ci NOT NULL,
  `profile_picture` text COLLATE utf8mb4_unicode_ci,
  `active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `password`, `gender`, `profile_picture`, `active`, `created_at`, `updated_at`) VALUES
(1, 'admin@virtualtryon.com', 'Administrador', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPjiUNFn2', 'OTHER', NULL, 1, '2025-06-03 07:28:08', '2025-06-03 07:28:08'),
(2, 'nick@gmail.com', 'Nick', '$2a$10$R.hMS2Qgy/CIBbQ7RiiToOgQ7q58IHtzNZne8ZrLGANSFD2eFRx1u', 'MALE', NULL, 1, '2025-06-03 13:17:12', '2025-06-03 13:17:12'),
(3, 'nicksaim69@gmail.com', 'Nick Saim', '$2a$10$n1gf6rM6Q4pP632b45xQduon1HPMreujLPsZJae20yi5wZN4Tmiqm', 'MALE', NULL, 1, '2025-06-04 01:57:41', '2025-06-04 01:57:41');

-- --------------------------------------------------------

--
-- Table structure for table `user_preferences`
--

CREATE TABLE `user_preferences` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `avatar_type` enum('BASIC','REALISTIC','CARTOON') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar_skin_tone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '#FDBCB4',
  `body_type` enum('SLIM','AVERAGE','ATHLETIC','CURVY','PLUS_SIZE') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `height` int DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `preferred_size` enum('XS','S','M','L','XL','XXL') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `preferred_colors` text COLLATE utf8mb4_unicode_ci,
  `favorite_categories` text COLLATE utf8mb4_unicode_ci,
  `enable_camera_by_default` tinyint(1) DEFAULT '0',
  `enable_pose_detection` tinyint(1) DEFAULT '1',
  `language` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT 'es',
  `last_selected_clothing_id` bigint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_preferences`
--

INSERT INTO `user_preferences` (`id`, `user_id`, `avatar_type`, `avatar_skin_tone`, `body_type`, `height`, `weight`, `preferred_size`, `preferred_colors`, `favorite_categories`, `enable_camera_by_default`, `enable_pose_detection`, `language`, `last_selected_clothing_id`, `created_at`, `updated_at`) VALUES
(1, 2, 'BASIC', '#FDBCB4', 'AVERAGE', NULL, NULL, 'M', NULL, NULL, 0, 1, 'es', 3, '2025-06-03 13:17:12', '2025-06-04 03:50:23'),
(2, 3, 'BASIC', '#FDBCB4', 'AVERAGE', NULL, NULL, 'M', NULL, NULL, 0, 1, 'es', 7, '2025-06-04 01:57:41', '2025-06-04 02:25:21');

-- --------------------------------------------------------

--
-- Stand-in structure for view `user_stats`
-- (See below for the actual view)
--
CREATE TABLE `user_stats` (
`id` bigint
,`name` varchar(255)
,`email` varchar(255)
,`created_at` timestamp
,`uploaded_clothing_count` bigint
,`session_count` bigint
,`avg_session_duration` decimal(14,4)
);

-- --------------------------------------------------------

--
-- Structure for view `clothing_stats`
--
DROP TABLE IF EXISTS `clothing_stats`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `clothing_stats`  AS SELECT `clothing`.`category` AS `category`, count(0) AS `total_items`, sum((case when (`clothing`.`is_public` = true) then 1 else 0 end)) AS `public_items`, avg(`clothing`.`price`) AS `avg_price`, min(`clothing`.`price`) AS `min_price`, max(`clothing`.`price`) AS `max_price` FROM `clothing` WHERE (`clothing`.`is_active` = true) GROUP BY `clothing`.`category``category`  ;

-- --------------------------------------------------------

--
-- Structure for view `user_stats`
--
DROP TABLE IF EXISTS `user_stats`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_stats`  AS SELECT `u`.`id` AS `id`, `u`.`name` AS `name`, `u`.`email` AS `email`, `u`.`created_at` AS `created_at`, count(`c`.`id`) AS `uploaded_clothing_count`, count(`ts`.`id`) AS `session_count`, avg(`ts`.`duration`) AS `avg_session_duration` FROM ((`users` `u` left join `clothing` `c` on(((`u`.`id` = `c`.`user_id`) and (`c`.`is_active` = true)))) left join `tryon_sessions` `ts` on((`u`.`id` = `ts`.`user_id`))) WHERE (`u`.`active` = true) GROUP BY `u`.`id``id`  ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clothing`
--
ALTER TABLE `clothing`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_clothing_category` (`category`),
  ADD KEY `idx_clothing_is_public` (`is_public`),
  ADD KEY `idx_clothing_is_active` (`is_active`),
  ADD KEY `idx_clothing_user_id` (`user_id`),
  ADD KEY `idx_clothing_created_at` (`created_at`);

--
-- Indexes for table `clothing_collections`
--
ALTER TABLE `clothing_collections`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clothing_dimensions`
--
ALTER TABLE `clothing_dimensions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj7m0rf3f1ubt6663ork15vtdl` (`clothing_item_id`);

--
-- Indexes for table `clothing_images`
--
ALTER TABLE `clothing_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7xvas2enva134d2cfq97dq5x2` (`clothing_item_id`);

--
-- Indexes for table `clothing_items`
--
ALTER TABLE `clothing_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK81dui96nvytfebslxpydkfpvl` (`collection_id`);

--
-- Indexes for table `clothing_reviews`
--
ALTER TABLE `clothing_reviews`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfckauw26ww9r8frgb2g56gga3` (`clothing_item_id`);

--
-- Indexes for table `clothing_tags`
--
ALTER TABLE `clothing_tags`
  ADD PRIMARY KEY (`clothing_id`,`tag_id`),
  ADD KEY `FKhhu2yjflqfjjfktln5d5ny5vo` (`tag_id`);

--
-- Indexes for table `clothing_variants`
--
ALTER TABLE `clothing_variants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK39qqfomgqhuuwk9s9l6554bld` (`clothing_item_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_t48xdq560gs3gap9g7jg36kgc` (`name`);

--
-- Indexes for table `tryon_sessions`
--
ALTER TABLE `tryon_sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_tryon_sessions_user_id` (`user_id`),
  ADD KEY `idx_tryon_sessions_clothing_id` (`clothing_id`),
  ADD KEY `idx_tryon_sessions_created_at` (`created_at`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `idx_users_email` (`email`),
  ADD KEY `idx_users_active` (`active`);

--
-- Indexes for table `user_preferences`
--
ALTER TABLE `user_preferences`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `idx_user_preferences_user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clothing`
--
ALTER TABLE `clothing`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `clothing_collections`
--
ALTER TABLE `clothing_collections`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clothing_dimensions`
--
ALTER TABLE `clothing_dimensions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clothing_images`
--
ALTER TABLE `clothing_images`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clothing_items`
--
ALTER TABLE `clothing_items`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clothing_reviews`
--
ALTER TABLE `clothing_reviews`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `clothing_variants`
--
ALTER TABLE `clothing_variants`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tryon_sessions`
--
ALTER TABLE `tryon_sessions`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_preferences`
--
ALTER TABLE `user_preferences`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `clothing`
--
ALTER TABLE `clothing`
  ADD CONSTRAINT `clothing_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `clothing_dimensions`
--
ALTER TABLE `clothing_dimensions`
  ADD CONSTRAINT `FKj7m0rf3f1ubt6663ork15vtdl` FOREIGN KEY (`clothing_item_id`) REFERENCES `clothing_items` (`id`);

--
-- Constraints for table `clothing_images`
--
ALTER TABLE `clothing_images`
  ADD CONSTRAINT `FK7xvas2enva134d2cfq97dq5x2` FOREIGN KEY (`clothing_item_id`) REFERENCES `clothing_items` (`id`);

--
-- Constraints for table `clothing_items`
--
ALTER TABLE `clothing_items`
  ADD CONSTRAINT `FK81dui96nvytfebslxpydkfpvl` FOREIGN KEY (`collection_id`) REFERENCES `clothing_collections` (`id`);

--
-- Constraints for table `clothing_reviews`
--
ALTER TABLE `clothing_reviews`
  ADD CONSTRAINT `FKfckauw26ww9r8frgb2g56gga3` FOREIGN KEY (`clothing_item_id`) REFERENCES `clothing_items` (`id`);

--
-- Constraints for table `clothing_tags`
--
ALTER TABLE `clothing_tags`
  ADD CONSTRAINT `FKfktysb7ele80ewsoekbh4x4e9` FOREIGN KEY (`clothing_id`) REFERENCES `clothing_items` (`id`),
  ADD CONSTRAINT `FKhhu2yjflqfjjfktln5d5ny5vo` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

--
-- Constraints for table `clothing_variants`
--
ALTER TABLE `clothing_variants`
  ADD CONSTRAINT `FK39qqfomgqhuuwk9s9l6554bld` FOREIGN KEY (`clothing_item_id`) REFERENCES `clothing_items` (`id`);

--
-- Constraints for table `tryon_sessions`
--
ALTER TABLE `tryon_sessions`
  ADD CONSTRAINT `tryon_sessions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tryon_sessions_ibfk_2` FOREIGN KEY (`clothing_id`) REFERENCES `clothing` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_preferences`
--
ALTER TABLE `user_preferences`
  ADD CONSTRAINT `user_preferences_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `evt_cleanup_old_sessions` ON SCHEDULE EVERY 1 DAY STARTS '2025-06-03 03:27:54' ON COMPLETION NOT PRESERVE ENABLE DO CALL cleanup_old_sessions()$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

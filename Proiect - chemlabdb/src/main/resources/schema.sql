-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jan 15, 2021 at 05:45 PM
-- Server version: 5.7.30
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `chemLabDb`
--

-- --------------------------------------------------------

--
-- Table structure for table `consumed`
--

CREATE TABLE `consumed` (
  `id` int(11) NOT NULL,
  `substanceId` int(11) NOT NULL,
  `consumedAmount` float NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `consumed`
--

INSERT INTO `consumed` (`id`, `substanceId`, `consumedAmount`, `userId`) VALUES
(1, 1, 0.0001, 1),
(2, 1, 0.0001, 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `cas` varchar(20) DEFAULT NULL,
  `purity` float NOT NULL,
  `supplier` varchar(20) NOT NULL,
  `packing` varchar(5) NOT NULL,
  `number` int(2) NOT NULL,
  `userId` int(2) NOT NULL,
  `price` float NOT NULL,
  `link` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`id`, `cas`, `purity`, `supplier`, `packing`, `number`, `userId`, `price`, `link`) VALUES
(1, '7647145', 99, 'Aldrich', '500g', 1, 1, 29.2, 'https://www.sigmaaldrich.com/catalog/product/sigald/s9888'),
(2, '7664939', 99.999, 'Aldrich', '500ml', 2, 3, 721, 'https://www.sigmaaldrich.com/catalog/product/sigald/339741'),
(3, '64197', 99, 'Aldrich', '500ml', 2, 1, 37.7, 'https://www.sigmaaldrich.com/catalog/product/sigald/A6283'),
(4, '67641', 99.9, 'Aldrich', '1l', 10, 1, 3740.9, 'https://www.sigmaaldrich.com/catalog/product/sigald/650501');

-- --------------------------------------------------------

--
-- Table structure for table `substances`
--

CREATE TABLE `substances` (
  `id` int(11) NOT NULL,
  `cas` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `molecularFormula` varchar(50) NOT NULL,
  `purity` float NOT NULL,
  `supplier` varchar(50) NOT NULL,
  `packing` varchar(10) NOT NULL,
  `availableQuantity` float NOT NULL,
  `ownerId` int(11) NOT NULL,
  `restricted` tinyint(1) NOT NULL,
  `price` float NOT NULL,
  `link` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `substances`
--

INSERT INTO `substances` (`id`, `cas`, `name`, `molecularFormula`, `purity`, `supplier`, `packing`, `availableQuantity`, `ownerId`, `restricted`, `price`, `link`) VALUES
(1, '3443456', '1-pyrenebutiric acid', 'C20H16O2', 97, 'Aldrich', '1g', 0.9998, 1, 0, 69.3, 'https://www.sigmaaldrich.com/catalog/product/aldrich/257354'),
(2, '127093', 'Sodium acetate', 'C2H3O2Na1', 99, 'Aldrich', '10', 10, 4, 1, 162, 'https://www.sigmaaldrich.com/catalog/product/sial/s2889'),
(3, '127093', 'Sodium acetate', 'C2H3O2Na1', 99, 'Aldrich', '10g', 10, 3, 0, 162, 'https://www.sigmaaldrich.com/catalog/product/sial/s2889'),
(4, '127093', 'Sodium acetate', 'C2H3O2Na1', 99, 'Aldrich', '10g', 10, 3, 0, 162, 'https://www.sigmaaldrich.com/catalog/product/sial/s2889');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `restrictedAccess` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `role`, `admin`, `restrictedAccess`) VALUES
(1, 'Adi Apostol', 'PhD student', 1, 1),
(2, 'Teo Mocanu', 'PhD student', 0, 0),
(3, 'Bianca Militaru', 'master student', 0, 0),
(4, 'Augustin Madalan', 'associate professor', 0, 1),
(5, 'Cristi Spinu', 'PhD student', 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `consumed`
--
ALTER TABLE `consumed`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `substances`
--
ALTER TABLE `substances`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `consumed`
--
ALTER TABLE `consumed`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `substances`
--
ALTER TABLE `substances`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

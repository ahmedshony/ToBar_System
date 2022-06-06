-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 17, 2022 at 01:48 PM
-- Server version: 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tobar_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `client_code` int(120) NOT NULL,
  `client_name` varchar(250) NOT NULL,
  `client_kind` varchar(250) NOT NULL,
  `client_envoy_code` int(120) NOT NULL,
  `client_envoy_name` varchar(250) NOT NULL,
  `client_address` varchar(250) NOT NULL DEFAULT ' ',
  `client_phone` varchar(250) NOT NULL DEFAULT ' ',
  `client_current_balance` double NOT NULL DEFAULT '0',
  `client_balance_received` double NOT NULL DEFAULT '0',
  `client_balance_remaining` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `company_code` int(120) NOT NULL,
  `company_name` varchar(250) NOT NULL,
  `company_address` varchar(250) NOT NULL DEFAULT ' ',
  `company_phone` varchar(250) NOT NULL DEFAULT ' ',
  `company_current_balance` double NOT NULL DEFAULT '0',
  `company_balance_paid` double NOT NULL DEFAULT '0',
  `company_balance_remaining` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `earn_and_spend_money`
--

CREATE TABLE `earn_and_spend_money` (
  `bill_operation_code` int(120) NOT NULL,
  `bill_code` int(120) NOT NULL,
  `bill_kind` varchar(250) NOT NULL,
  `bill_name` varchar(250) NOT NULL,
  `bill_value` double NOT NULL,
  `bill_total` double NOT NULL,
  `bill_date` date NOT NULL,
  `bill_notes` varchar(250) NOT NULL DEFAULT ' '
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `envoys`
--

CREATE TABLE `envoys` (
  `envoy_code` int(120) NOT NULL,
  `envoy_name` varchar(250) NOT NULL,
  `envoy_phone` varchar(250) NOT NULL,
  `envoy_current_balance` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `expire_details`
--

CREATE TABLE `expire_details` (
  `code` int(120) NOT NULL,
  `item_code` int(120) NOT NULL,
  `item_name` varchar(250) NOT NULL,
  `item_kind` varchar(250) NOT NULL,
  `quantity` double NOT NULL,
  `expire_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `export_client_bills`
--

CREATE TABLE `export_client_bills` (
  `export_bill_item_code` int(120) NOT NULL,
  `export_code` int(120) NOT NULL,
  `export_bill_kind` varchar(250) NOT NULL,
  `export_pay_kind` varchar(250) NOT NULL,
  `export_date` date NOT NULL,
  `export_client_code` int(120) NOT NULL,
  `export_client_name` varchar(250) NOT NULL,
  `export_envoy_code` int(120) NOT NULL,
  `export_envoy_name` varchar(250) NOT NULL,
  `export_item_code` int(120) NOT NULL,
  `export_item_name` varchar(250) NOT NULL,
  `export_item_buy_price` double NOT NULL,
  `export_item_kind` varchar(250) NOT NULL,
  `export_item_count` double NOT NULL,
  `export_item_price` double NOT NULL,
  `export_item_total_price` double NOT NULL,
  `export_item_expire_date` date NOT NULL,
  `export_bill_total_price` double NOT NULL,
  `export_bill_total_discount` double NOT NULL,
  `export_bill_total_received` double NOT NULL,
  `export_bill_final_total` double NOT NULL,
  `export_bill_receipt_number` varchar(250) NOT NULL DEFAULT ' '
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `import_company_bills`
--

CREATE TABLE `import_company_bills` (
  `import_bill_item_code` int(120) NOT NULL,
  `import_code` int(120) NOT NULL,
  `import_bill_kind` varchar(250) NOT NULL,
  `import_pay_kind` varchar(250) NOT NULL,
  `import_date` date NOT NULL,
  `import_company_code` int(120) NOT NULL,
  `import_company_name` varchar(250) NOT NULL,
  `import_item_code` int(120) NOT NULL,
  `import_item_name` varchar(250) NOT NULL,
  `import_item_kind` varchar(250) NOT NULL,
  `import_item_count` double NOT NULL,
  `import_item_price` double NOT NULL,
  `import_item_total_price` double NOT NULL,
  `import_item_expire_date` date NOT NULL,
  `import_bill_total_price` double NOT NULL,
  `import_bill_total_discount` double NOT NULL,
  `import_bill_total_paid` double NOT NULL,
  `import_bill_final_total` double NOT NULL,
  `import_bill_receipt_number` varchar(250) NOT NULL DEFAULT ' '
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `item_code` int(120) NOT NULL,
  `item_name` varchar(250) NOT NULL,
  `item_current_quantity` double NOT NULL DEFAULT '0',
  `item_kind` varchar(250) NOT NULL,
  `item_farmer_cash_price` double NOT NULL,
  `item_trade_cash_price` double NOT NULL,
  `item_farmer_Installment_price` double NOT NULL,
  `item_trade_Installment_price` double NOT NULL,
  `item_buy_price` double NOT NULL,
  `item_quantity_limit` double NOT NULL DEFAULT '0',
  `item_notes` varchar(250) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `item_move`
--

CREATE TABLE `item_move` (
  `move_code` int(120) NOT NULL,
  `move_item_code` int(120) NOT NULL,
  `move_item_name` varchar(250) NOT NULL,
  `move_bill_code` int(120) NOT NULL,
  `move_kind` varchar(250) NOT NULL,
  `move_item_kind` varchar(250) NOT NULL,
  `move_quantity` double NOT NULL,
  `move_buy_price` double NOT NULL,
  `move_price` double NOT NULL,
  `move_envoy_name` varchar(250) NOT NULL,
  `move_date` date NOT NULL,
  `move_client_name` varchar(250) NOT NULL,
  `move_expire_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(120) NOT NULL,
  `priority` int(120) NOT NULL,
  `user_name` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `user_type` varchar(250) NOT NULL DEFAULT 'manager'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `priority`, `user_name`, `password`, `user_type`) VALUES
(1, 1, 'Ahmed El-Shall', '117', 'manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`client_code`),
  ADD UNIQUE KEY `client_name` (`client_name`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`company_code`),
  ADD UNIQUE KEY `company_name` (`company_name`);

--
-- Indexes for table `earn_and_spend_money`
--
ALTER TABLE `earn_and_spend_money`
  ADD PRIMARY KEY (`bill_operation_code`);

--
-- Indexes for table `envoys`
--
ALTER TABLE `envoys`
  ADD PRIMARY KEY (`envoy_code`),
  ADD UNIQUE KEY `envoy_name` (`envoy_name`);

--
-- Indexes for table `expire_details`
--
ALTER TABLE `expire_details`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `export_client_bills`
--
ALTER TABLE `export_client_bills`
  ADD PRIMARY KEY (`export_bill_item_code`);

--
-- Indexes for table `import_company_bills`
--
ALTER TABLE `import_company_bills`
  ADD PRIMARY KEY (`import_bill_item_code`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`item_code`,`item_name`),
  ADD UNIQUE KEY `item_name` (`item_name`);

--
-- Indexes for table `item_move`
--
ALTER TABLE `item_move`
  ADD PRIMARY KEY (`move_code`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userName` (`user_name`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `client_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `company_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `earn_and_spend_money`
--
ALTER TABLE `earn_and_spend_money`
  MODIFY `bill_operation_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `envoys`
--
ALTER TABLE `envoys`
  MODIFY `envoy_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `expire_details`
--
ALTER TABLE `expire_details`
  MODIFY `code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `export_client_bills`
--
ALTER TABLE `export_client_bills`
  MODIFY `export_bill_item_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `import_company_bills`
--
ALTER TABLE `import_company_bills`
  MODIFY `import_bill_item_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `item_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `item_move`
--
ALTER TABLE `item_move`
  MODIFY `move_code` int(120) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(120) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

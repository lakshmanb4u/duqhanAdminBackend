-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 24, 2017 at 07:56 AM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `duqhandb_new`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `load_date` datetime NOT NULL,
  `product_property_map_id` bigint(32) NOT NULL,
  `discount_offer_pct` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `parent_path` varchar(255) NOT NULL,
  `quantity` bigint(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `duqhan_admin`
--

CREATE TABLE `duqhan_admin` (
  `id` bigint(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `last_login` datetime NOT NULL,
  `aouth_token` varchar(255) DEFAULT NULL,
  `valid_till` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `offer_products`
--

CREATE TABLE `offer_products` (
  `id` bigint(32) NOT NULL,
  `product_id` bigint(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `id` bigint(32) NOT NULL,
  `order_id` varchar(255) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `payment_key` varchar(50) NOT NULL,
  `map_id` bigint(32) NOT NULL,
  `payment_amount` double NOT NULL,
  `order_date` datetime NOT NULL,
  `status` varchar(50) NOT NULL,
  `quentity` bigint(32) NOT NULL,
  `address_id` bigint(32) NOT NULL,
  `delivery_date` datetime DEFAULT NULL,
  `shipment_id` varchar(255) DEFAULT NULL,
  `return_status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `otp_table`
--

CREATE TABLE `otp_table` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `user_mail` varchar(255) NOT NULL,
  `otp` varchar(50) NOT NULL,
  `send_time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `payment_detail`
--

CREATE TABLE `payment_detail` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `payment_key` varchar(50) NOT NULL,
  `payer_id` varchar(50) DEFAULT NULL,
  `pay_amount` double NOT NULL,
  `payment_type` varchar(20) NOT NULL,
  `payment_date` date NOT NULL,
  `payment_status` varchar(10) NOT NULL,
  `paypal_token` varchar(255) DEFAULT NULL,
  `access_token` varchar(255) NOT NULL,
  `app_type` int(11) NOT NULL,
  `paytm_txn_id` varchar(255) NOT NULL,
  `gateway_type` int(11) NOT NULL,
  `remarks` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` bigint(32) NOT NULL,
  `name` text NOT NULL,
  `category_id` bigint(32) NOT NULL,
  `description` text,
  `imgurl` varchar(255) NOT NULL,
  `last_update` datetime NOT NULL,
  `vendor_id` bigint(32) NOT NULL,
  `shipping_time` varchar(20) DEFAULT NULL,
  `shipping_rate` double DEFAULT NULL,
  `parent_path` varchar(255) NOT NULL,
  `external_link` text,
  `specifications` text,
  `properties` varchar(255) NOT NULL,
  `product_height` double NOT NULL,
  `product_length` double NOT NULL,
  `product_weight` double NOT NULL,
  `product_width` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product_img`
--

CREATE TABLE `product_img` (
  `id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `img_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product_properties`
--

CREATE TABLE `product_properties` (
  `id` bigint(32) NOT NULL,
  `property_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product_properties_map`
--

CREATE TABLE `product_properties_map` (
  `id` bigint(32) NOT NULL,
  `product_id` bigint(32) NOT NULL,
  `propertyvalue_composition` varchar(255) NOT NULL,
  `discount` double NOT NULL,
  `price` double NOT NULL,
  `quantity` bigint(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `product_propertyvalues`
--

CREATE TABLE `product_propertyvalues` (
  `id` bigint(32) NOT NULL,
  `value_name` text NOT NULL,
  `product_id` bigint(32) NOT NULL,
  `property_id` bigint(32) NOT NULL,
  `ref_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `recent_view`
--

CREATE TABLE `recent_view` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `view_date` date NOT NULL,
  `visit_count` bigint(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `shipment_table`
--

CREATE TABLE `shipment_table` (
  `id` bigint(32) NOT NULL,
  `shipment_id` varchar(255) NOT NULL,
  `parcel_id` varchar(255) NOT NULL,
  `postage_label_id` varchar(255) NOT NULL,
  `rate_id` varchar(255) NOT NULL,
  `tracker_id` varchar(255) NOT NULL,
  `is_return` tinyint(1) NOT NULL,
  `customs_info_id` varchar(255) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `created_at` datetime NOT NULL,
  `status` varchar(255) NOT NULL,
  `pay_key` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `temtproductlinklist`
--

CREATE TABLE `temtproductlinklist` (
  `id` bigint(32) NOT NULL,
  `link` text NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `reg_date` datetime DEFAULT NULL,
  `lastlogin_date` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fbid` bigint(32) DEFAULT NULL,
  `profile_img` varchar(255) DEFAULT NULL,
  `fcm_token` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `free_offer_accepted` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_activity`
--

CREATE TABLE `user_activity` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `activity` varchar(255) DEFAULT NULL,
  `activity_time` datetime DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_address`
--

CREATE TABLE `user_address` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `status` bigint(20) NOT NULL,
  `street_one` varchar(255) NOT NULL,
  `street_two` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `residential` tinyint(1) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user_aouth`
--

CREATE TABLE `user_aouth` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `aouth_token` varchar(255) NOT NULL,
  `valid_till` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `id` bigint(32) NOT NULL,
  `vendor_name` varchar(255) NOT NULL,
  `street_one` varchar(255) NOT NULL,
  `street_two` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `zip` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `duqhan_admin`
--
ALTER TABLE `duqhan_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `offer_products`
--
ALTER TABLE `offer_products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `otp_table`
--
ALTER TABLE `otp_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment_detail`
--
ALTER TABLE `payment_detail`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `payment_key` (`payment_key`) USING BTREE;

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_img`
--
ALTER TABLE `product_img`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_properties`
--
ALTER TABLE `product_properties`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_properties_map`
--
ALTER TABLE `product_properties_map`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_propertyvalues`
--
ALTER TABLE `product_propertyvalues`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `recent_view`
--
ALTER TABLE `recent_view`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shipment_table`
--
ALTER TABLE `shipment_table`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temtproductlinklist`
--
ALTER TABLE `temtproductlinklist`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_activity`
--
ALTER TABLE `user_activity`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_address`
--
ALTER TABLE `user_address`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_aouth`
--
ALTER TABLE `user_aouth`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `duqhan_admin`
--
ALTER TABLE `duqhan_admin`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `offer_products`
--
ALTER TABLE `offer_products`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `otp_table`
--
ALTER TABLE `otp_table`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `payment_detail`
--
ALTER TABLE `payment_detail`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;
--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `product_img`
--
ALTER TABLE `product_img`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `product_properties`
--
ALTER TABLE `product_properties`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `product_properties_map`
--
ALTER TABLE `product_properties_map`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `product_propertyvalues`
--
ALTER TABLE `product_propertyvalues`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `recent_view`
--
ALTER TABLE `recent_view`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `shipment_table`
--
ALTER TABLE `shipment_table`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `temtproductlinklist`
--
ALTER TABLE `temtproductlinklist`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=205068;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=234;
--
-- AUTO_INCREMENT for table `user_activity`
--
ALTER TABLE `user_activity`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37810;
--
-- AUTO_INCREMENT for table `user_address`
--
ALTER TABLE `user_address`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
--
-- AUTO_INCREMENT for table `user_aouth`
--
ALTER TABLE `user_aouth`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=262;
--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `id` bigint(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
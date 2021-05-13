-- phpMyAdmin SQL Dump
-- version 5.0.0-alpha1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: May 12, 2021 at 08:31 PM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.1.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `employee_tracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `id` bigint(11) NOT NULL,
  `audit_trail_id` bigint(11) NOT NULL,
  `entity_id` bigint(11) NOT NULL,
  `narrative` varchar(255) NOT NULL,
  `date_added` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `annual_activity_calendar`
--

CREATE TABLE `annual_activity_calendar` (
  `id` bigint(11) NOT NULL,
  `annual_activity_category_id` bigint(11) NOT NULL,
  `activity` varchar(255) NOT NULL,
  `activity_day` date NOT NULL,
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `annual_activity_category`
--

CREATE TABLE `annual_activity_category` (
  `id` bigint(11) NOT NULL,
  `category` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `annual_activity_category`
--

INSERT INTO `annual_activity_category` (`id`, `category`) VALUES
(1, 'HOLIDAY'),
(2, 'WORK');

-- --------------------------------------------------------

--
-- Table structure for table `audit_trail`
--

CREATE TABLE `audit_trail` (
  `id` bigint(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `business_unit`
--

CREATE TABLE `business_unit` (
  `id` int(11) NOT NULL,
  `business_unit` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `business_unit`
--

INSERT INTO `business_unit` (`id`, `business_unit`, `date_created`) VALUES
(1, 'BULAWAYO CAMPUS', '2021-04-22 14:58:40'),
(2, 'HARARE OFFICE', '2021-04-22 14:58:40');

-- --------------------------------------------------------

--
-- Table structure for table `delegation_of_duty`
--

CREATE TABLE `delegation_of_duty` (
  `id` bigint(11) NOT NULL,
  `assigned_by` bigint(11) NOT NULL,
  `assign_to` bigint(11) NOT NULL,
  `duty` text NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `reason` text DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `delegation_of_duty`
--

INSERT INTO `delegation_of_duty` (`id`, `assigned_by`, `assign_to`, `duty`, `from_date`, `to_date`, `reason`, `date_created`) VALUES
(1, 3, 1, 'AEMAPS FRONT-END DEVELOPMENT', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed ASAP', '2021-05-10 11:57:29'),
(2, 3, 1, 'AEMAPS BACK-END DEVELOPMENT', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed ASAP', '2021-05-10 18:19:38'),
(3, 3, 1, 'AEMAPS GRAPHICS(UI) DESIGN ', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed ASAP', '2021-05-10 18:28:39'),
(4, 3, 1, 'AEMAPS DESIGN SESSION', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed in a no time.', '2021-05-10 18:35:14'),
(5, 3, 1, 'AEMAPS QUALITY ASSURANCE TESTS', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed in a no time.', '2021-05-10 18:39:28'),
(6, 3, 1, 'AEMAPS USER ACCEPTANCE TESTS', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed in a no time.', '2021-05-10 18:47:42'),
(7, 3, 1, 'AEMAPS LAUNCH', '2021-04-11', '2021-05-01', 'The project is urgent and has to be completed in a no time.', '2021-05-11 19:23:07');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `department` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`id`, `department`, `date_created`) VALUES
(1, 'COMPUTER SCIENCE', '2021-04-22 14:57:47'),
(2, 'ENGINEERING', '2021-04-22 14:57:47'),
(3, 'BUILT ENVIRONMENT', '2021-04-22 14:57:47');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` bigint(11) NOT NULL,
  `employment_status_id` bigint(20) NOT NULL,
  `job_title_id` bigint(11) NOT NULL,
  `employee_code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `gender` enum('M','F') NOT NULL,
  `mobile_number` varchar(13) DEFAULT NULL,
  `email_address` varchar(50) NOT NULL,
  `residential_status` enum('PERMANENT','MORTGAGE','RENTAL','') NOT NULL,
  `address_1` text NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id`, `employment_status_id`, `job_title_id`, `employee_code`, `name`, `surname`, `gender`, `mobile_number`, `email_address`, `residential_status`, `address_1`, `date_created`, `date_updated`) VALUES
(1, 1, 1, 'N0173717A', 'NATASHA', 'CHIKURUWO', 'F', '+263782844543', 'N0173717A@students.nust.ac.zw', 'MORTGAGE', '16306 GAMBA ROAD, ZENGEZA 3 INFILLS', '2021-05-10 09:17:49', '2021-05-10 12:06:28'),
(3, 1, 1, 'N0172515T', 'Ruvimbo', 'CHIKURUWO', 'F', '+263779821348', 'N0172515T@students.nust.ac.zw', 'RENTAL', '16307 GAMBA ROAD, ZENGEZA 3 INFILLS', '2021-05-10 10:10:46', '2021-05-11 21:27:51');

-- --------------------------------------------------------

--
-- Table structure for table `employee_status`
--

CREATE TABLE `employee_status` (
  `id` bigint(11) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_status`
--

INSERT INTO `employee_status` (`id`, `status`) VALUES
(1, 'PERMANENT'),
(2, 'CONTRACT');

-- --------------------------------------------------------

--
-- Table structure for table `employee_user`
--

CREATE TABLE `employee_user` (
  `id` bigint(11) NOT NULL,
  `employee_id` bigint(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `employee_user`
--

INSERT INTO `employee_user` (`id`, `employee_id`, `user_id`) VALUES
(1, 1, 3),
(3, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `job_title`
--

CREATE TABLE `job_title` (
  `id` bigint(11) NOT NULL,
  `business_unit_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `title_name` varchar(255) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `job_title`
--

INSERT INTO `job_title` (`id`, `business_unit_id`, `department_id`, `title_name`, `date_created`, `date_updated`) VALUES
(1, 1, 1, 'SOFTWARE DEVELOPER', '2021-05-10 06:04:27', '2021-05-10 06:04:27'),
(3, 1, 3, 'SOFTWARE QUALITY TESTER', '2021-05-10 06:07:18', '2021-05-10 06:07:18'),
(4, 1, 3, 'SCRUM MASTER', '2021-05-10 06:07:36', '2021-05-10 06:07:36'),
(5, 1, 3, 'GRAPHICS DESIGNER', '2021-05-10 06:08:08', '2021-05-10 06:08:08'),
(6, 1, 3, 'WEB DEVELOPER', '2021-05-10 06:08:22', '2021-05-10 06:08:22');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `user_id`, `date`) VALUES
(1, 1, '2021-05-10 05:53:59'),
(2, 3, '2021-05-10 08:15:38'),
(3, 3, '2021-05-10 18:18:35'),
(4, 3, '2021-05-11 07:23:54'),
(5, 3, '2021-05-11 12:34:24'),
(6, 3, '2021-05-11 18:05:48'),
(7, 3, '2021-05-12 06:00:13'),
(8, 3, '2021-05-12 17:55:39');

-- --------------------------------------------------------

--
-- Table structure for table `next_of_kin`
--

CREATE TABLE `next_of_kin` (
  `id` bigint(11) NOT NULL,
  `employee_id` bigint(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` text NOT NULL,
  `address` text NOT NULL,
  `mobile_number` varchar(30) NOT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `next_of_kin`
--

INSERT INTO `next_of_kin` (`id`, `employee_id`, `name`, `surname`, `address`, `mobile_number`, `email_address`, `date_created`, `date_updated`) VALUES
(1, 1, 'RICHARD', 'CHIKURUWO', '16306 GAMBA ROAD, ZENGEZA 3 INFILLS', '+263779503822', 'roxnmugo@gmail.com', '2021-05-10 10:40:00', '2021-05-10 10:40:00'),
(2, 3, 'NIGEL T', 'CHIKURUWO', '16307 GAMBA ROAD, ZENGEZA 3 INFILLS', '+263771503822', 'rILEY@gmail.com', '2021-05-10 11:00:37', '2021-05-10 11:00:37');

-- --------------------------------------------------------

--
-- Table structure for table `notices`
--

CREATE TABLE `notices` (
  `id` bigint(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `notice` text NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'CHIEF'),
(3, 'HOD'),
(4, 'MANAGER'),
(5, 'SUPERVISOR'),
(6, 'SUBORDINATE'),
(7, 'GUEST');

-- --------------------------------------------------------

--
-- Table structure for table `spouse_details`
--

CREATE TABLE `spouse_details` (
  `id` bigint(11) NOT NULL,
  `employee_id` bigint(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `national_id_number` varchar(14) NOT NULL,
  `employer` varchar(255) DEFAULT NULL,
  `employer_address` text DEFAULT NULL,
  `mobile_number` varchar(14) NOT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `spouse_details`
--

INSERT INTO `spouse_details` (`id`, `employee_id`, `name`, `surname`, `national_id_number`, `employer`, `employer_address`, `mobile_number`, `email_address`, `date_created`, `date_updated`) VALUES
(1, 1, 'MUNYARADZI', 'CHIKURUWO', '63-2135996S07', '', '', '+263779821348', 'mchikuruwo@hotmail.com', '2021-05-10 10:13:38', '2021-05-10 10:13:38');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` bigint(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `task_importance` bigint(11) NOT NULL,
  `task_status` enum('PENDING','ACTIVE','COMPLETE','') NOT NULL,
  `start_date` date NOT NULL,
  `due_date` date NOT NULL,
  `actual_start_date` date DEFAULT NULL,
  `actual_end_date` date DEFAULT NULL,
  `reason_of_missing_due_date` text DEFAULT NULL,
  `date_updated` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`id`, `name`, `description`, `task_importance`, `task_status`, `start_date`, `due_date`, `actual_start_date`, `actual_end_date`, `reason_of_missing_due_date`, `date_updated`) VALUES
(1, 'AEMAPS BACKEND DEVELOPMENT', 'Writing code for the backend logic of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 06:23:44'),
(2, 'AEMAPS FRONT-END DEVELOPMENT', 'Writing code for the backend logic of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 07:20:16'),
(3, 'UI/UX WIREFRAMES DESIGN', 'Designing the wireframes for the Web and Mobile platforms of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 07:50:14'),
(4, 'WEB INTERFACE DESIGN', 'Designing the user interface for the Web platform of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 08:16:23'),
(5, 'MOBILE INTERFACE DESIGN', 'Designing the user interface for the mobile platform of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 17:57:08'),
(6, 'MOBILE INTERFACE DESIGN', 'Designing the user interface for the mobile platform of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 18:03:25'),
(7, 'MOBILE INTERFACE DESIGN', 'Designing the user interface for the mobile platform of the AEMAPS platform.', 4, 'ACTIVE', '2021-04-11', '2021-05-01', '2021-04-20', '2021-05-01', 'N/A', '2021-05-12 18:09:05');

-- --------------------------------------------------------

--
-- Table structure for table `task_delegation_of_duty`
--

CREATE TABLE `task_delegation_of_duty` (
  `id` bigint(11) NOT NULL,
  `task_id` bigint(11) NOT NULL,
  `delegation_of_duty_id` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_delegation_of_duty`
--

INSERT INTO `task_delegation_of_duty` (`id`, `task_id`, `delegation_of_duty_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `task_employee`
--

CREATE TABLE `task_employee` (
  `id` bigint(11) NOT NULL,
  `task_id` bigint(11) NOT NULL,
  `employee_id` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_employee`
--

INSERT INTO `task_employee` (`id`, `task_id`, `employee_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `task_importance`
--

CREATE TABLE `task_importance` (
  `id` bigint(11) NOT NULL,
  `importance` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_importance`
--

INSERT INTO `task_importance` (`id`, `importance`) VALUES
(4, 'EXTREMELY_HIGH'),
(3, 'HIGH'),
(1, 'LOW'),
(2, 'MEDIUM');

-- --------------------------------------------------------

--
-- Table structure for table `task_requests`
--

CREATE TABLE `task_requests` (
  `id` bigint(11) NOT NULL,
  `employee_id` bigint(11) NOT NULL,
  `task` bigint(11) NOT NULL,
  `task_request_action` bigint(11) NOT NULL,
  `task_request_status` bigint(11) NOT NULL,
  `request_date` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `task_request_action`
--

CREATE TABLE `task_request_action` (
  `id` bigint(11) NOT NULL,
  `action` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_request_action`
--

INSERT INTO `task_request_action` (`id`, `action`) VALUES
(1, 'ADD'),
(2, 'COMPLETE');

-- --------------------------------------------------------

--
-- Table structure for table `task_request_status`
--

CREATE TABLE `task_request_status` (
  `id` bigint(11) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_request_status`
--

INSERT INTO `task_request_status` (`id`, `status`) VALUES
(2, 'APPROVED'),
(1, 'PENDING'),
(3, 'REJECTED');

-- --------------------------------------------------------

--
-- Table structure for table `task_status`
--

CREATE TABLE `task_status` (
  `id` bigint(11) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `task_status`
--

INSERT INTO `task_status` (`id`, `status`) VALUES
(2, 'ACTIVE'),
(3, 'COMPLETED'),
(1, 'PENDING');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `surname` varchar(150) NOT NULL,
  `email_address` varchar(150) NOT NULL,
  `employee_code` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL DEFAULT b'1',
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `date_updated` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `surname`, `email_address`, `employee_code`, `password`, `is_active`, `date_created`, `date_updated`) VALUES
(1, 'Munyaradzi', 'Chikuruwo', 'roxnmugo@gmail.com', 'N0172515T', '$2a$10$EvirdWHA.xYnjjw2/eiR5OmtTq39N.642QFw4O4mVT/3vgg6Y7u32', b'1', '2021-05-10 05:20:29', '2021-05-10 05:20:29'),
(2, 'Ruvimbo', 'Chikuruwo', 'N0172515T@students.nust.ac.zw', 'N0172516T', '$2a$10$NeIr.B1c9OGYN9uTEwCkv.P.q4VYy5Jew8zhGO1ukAwh00./9G4u.', b'1', '2021-05-10 05:29:46', '2021-05-10 05:29:46'),
(3, 'Natasha', 'Chikuruwo', 'N0173717A@students.nust.ac.zw', 'N0173717A', '$2a$10$3JqvXf36P6FEw0m9dOH35.iWQ8ufp72UA/K5FK3WBbAsqB9ogFt3a', b'1', '2021-05-10 05:38:41', '2021-05-10 05:38:41');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `user_id`, `role_id`) VALUES
(1, 1, 1),
(2, 1, 1),
(3, 2, 4),
(4, 3, 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `audit_trail_id` (`audit_trail_id`);

--
-- Indexes for table `annual_activity_calendar`
--
ALTER TABLE `annual_activity_calendar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `annual_activity_category_id` (`annual_activity_category_id`);

--
-- Indexes for table `annual_activity_category`
--
ALTER TABLE `annual_activity_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `audit_trail`
--
ALTER TABLE `audit_trail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `business_unit`
--
ALTER TABLE `business_unit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `business_unit` (`business_unit`);

--
-- Indexes for table `delegation_of_duty`
--
ALTER TABLE `delegation_of_duty`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee_id` (`assigned_by`),
  ADD KEY `assign_to` (`assign_to`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `department` (`department`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `employee_code` (`employee_code`),
  ADD UNIQUE KEY `email_address` (`email_address`),
  ADD KEY `employment_status_id` (`employment_status_id`),
  ADD KEY `job_title_id` (`job_title_id`);

--
-- Indexes for table `employee_status`
--
ALTER TABLE `employee_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employee_user`
--
ALTER TABLE `employee_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `employee_id` (`employee_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indexes for table `job_title`
--
ALTER TABLE `job_title`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `title_name` (`title_name`),
  ADD KEY `business_unit_id` (`business_unit_id`),
  ADD KEY `department_id` (`department_id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `next_of_kin`
--
ALTER TABLE `next_of_kin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mobile_number` (`mobile_number`,`email_address`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `notices`
--
ALTER TABLE `notices`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `spouse_details`
--
ALTER TABLE `spouse_details`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `national_id_number` (`national_id_number`,`mobile_number`,`email_address`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `task_importance` (`task_importance`);

--
-- Indexes for table `task_delegation_of_duty`
--
ALTER TABLE `task_delegation_of_duty`
  ADD PRIMARY KEY (`id`),
  ADD KEY `task_id` (`task_id`),
  ADD KEY `delegation_of_duty_id` (`delegation_of_duty_id`);

--
-- Indexes for table `task_employee`
--
ALTER TABLE `task_employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `task_id` (`task_id`),
  ADD KEY `employee_id` (`employee_id`);

--
-- Indexes for table `task_importance`
--
ALTER TABLE `task_importance`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `importance` (`importance`);

--
-- Indexes for table `task_requests`
--
ALTER TABLE `task_requests`
  ADD PRIMARY KEY (`id`),
  ADD KEY `employee_id` (`employee_id`),
  ADD KEY `activity` (`task`),
  ADD KEY `activity_request_action` (`task_request_action`),
  ADD KEY `activity_request_status` (`task_request_status`);

--
-- Indexes for table `task_request_action`
--
ALTER TABLE `task_request_action`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `action` (`action`);

--
-- Indexes for table `task_request_status`
--
ALTER TABLE `task_request_status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `status` (`status`);

--
-- Indexes for table `task_status`
--
ALTER TABLE `task_status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `status` (`status`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email_address` (`email_address`),
  ADD UNIQUE KEY `employee_code` (`employee_code`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `annual_activity_calendar`
--
ALTER TABLE `annual_activity_calendar`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `annual_activity_category`
--
ALTER TABLE `annual_activity_category`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `audit_trail`
--
ALTER TABLE `audit_trail`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `business_unit`
--
ALTER TABLE `business_unit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `delegation_of_duty`
--
ALTER TABLE `delegation_of_duty`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `employee_status`
--
ALTER TABLE `employee_status`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee_user`
--
ALTER TABLE `employee_user`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `job_title`
--
ALTER TABLE `job_title`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `next_of_kin`
--
ALTER TABLE `next_of_kin`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `notices`
--
ALTER TABLE `notices`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `spouse_details`
--
ALTER TABLE `spouse_details`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `task_delegation_of_duty`
--
ALTER TABLE `task_delegation_of_duty`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `task_employee`
--
ALTER TABLE `task_employee`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `task_importance`
--
ALTER TABLE `task_importance`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `task_requests`
--
ALTER TABLE `task_requests`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `task_request_action`
--
ALTER TABLE `task_request_action`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `task_request_status`
--
ALTER TABLE `task_request_status`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `task_status`
--
ALTER TABLE `task_status`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`audit_trail_id`) REFERENCES `audit_trail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `annual_activity_calendar`
--
ALTER TABLE `annual_activity_calendar`
  ADD CONSTRAINT `annual_activity_calendar_ibfk_1` FOREIGN KEY (`annual_activity_category_id`) REFERENCES `annual_activity_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `audit_trail`
--
ALTER TABLE `audit_trail`
  ADD CONSTRAINT `audit_trail_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `delegation_of_duty`
--
ALTER TABLE `delegation_of_duty`
  ADD CONSTRAINT `delegation_of_duty_ibfk_1` FOREIGN KEY (`assigned_by`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `delegation_of_duty_ibfk_2` FOREIGN KEY (`assign_to`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`employment_status_id`) REFERENCES `employee_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`job_title_id`) REFERENCES `job_title` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employee_user`
--
ALTER TABLE `employee_user`
  ADD CONSTRAINT `employee_user_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `employee_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `job_title`
--
ALTER TABLE `job_title`
  ADD CONSTRAINT `job_title_ibfk_1` FOREIGN KEY (`business_unit_id`) REFERENCES `business_unit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `job_title_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `next_of_kin`
--
ALTER TABLE `next_of_kin`
  ADD CONSTRAINT `next_of_kin_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `spouse_details`
--
ALTER TABLE `spouse_details`
  ADD CONSTRAINT `spouse_details_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_6` FOREIGN KEY (`task_importance`) REFERENCES `task_importance` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `task_delegation_of_duty`
--
ALTER TABLE `task_delegation_of_duty`
  ADD CONSTRAINT `task_delegation_of_duty_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_delegation_of_duty_ibfk_2` FOREIGN KEY (`delegation_of_duty_id`) REFERENCES `delegation_of_duty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `task_employee`
--
ALTER TABLE `task_employee`
  ADD CONSTRAINT `task_employee_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_employee_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `task_requests`
--
ALTER TABLE `task_requests`
  ADD CONSTRAINT `task_requests_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_requests_ibfk_2` FOREIGN KEY (`task`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_requests_ibfk_3` FOREIGN KEY (`task_request_action`) REFERENCES `task_request_action` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `task_requests_ibfk_4` FOREIGN KEY (`task_request_status`) REFERENCES `task_request_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


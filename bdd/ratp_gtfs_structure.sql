-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mer 13 Novembre 2013 à 19:30
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `ratp_gtfs`
--
CREATE DATABASE IF NOT EXISTS `ratp_gtfs` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ratp_gtfs`;

-- --------------------------------------------------------

--
-- Structure de la table `agency`
--

CREATE TABLE IF NOT EXISTS `agency` (
  `agency_id` int(11) NOT NULL,
  `agency_name` varchar(150) NOT NULL,
  `agency_url` varchar(150) NOT NULL,
  `agency_timezone` varchar(10) NOT NULL,
  `agency_lang` varchar(150) DEFAULT NULL,
  `agency_phone` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`agency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `calendar`
--

CREATE TABLE IF NOT EXISTS `calendar` (
  `service_id` int(11) NOT NULL,
  `monday` tinyint(1) NOT NULL,
  `tuesday` tinyint(1) NOT NULL,
  `wednesday` tinyint(1) NOT NULL,
  `thursday` tinyint(1) NOT NULL,
  `friday` tinyint(1) NOT NULL,
  `saturday` tinyint(1) NOT NULL,
  `sunday` tinyint(1) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `calendar_dates`
--

CREATE TABLE IF NOT EXISTS `calendar_dates` (
  `service_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `exception_type` tinyint(1) NOT NULL,
  PRIMARY KEY (`service_id`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
  `route_id` int(11) NOT NULL,
  `agency_id` int(11) NOT NULL,
  `route_short_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `route_long_name` varchar(150) CHARACTER SET utf8 NOT NULL,
  `route_desc` varchar(150) CHARACTER SET utf8 NOT NULL,
  `route_type` int(11) NOT NULL,
  `route_url` varchar(150) NOT NULL,
  `route_color` varchar(10) CHARACTER SET utf8 NOT NULL,
  `route_text_color` varchar(10) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `stops`
--

CREATE TABLE IF NOT EXISTS `stops` (
  `stop_id` int(11) NOT NULL,
  `stop_code` varchar(150) CHARACTER SET utf8 NOT NULL,
  `stop_name` varchar(150) CHARACTER SET utf8 NOT NULL,
  `stop_desc` varchar(150) CHARACTER SET utf8 NOT NULL,
  `stop_lat` decimal(20,20) NOT NULL,
  `stop_lon` decimal(20,20) NOT NULL,
  `location_type` tinyint(1) NOT NULL,
  `parent_station` int(11) NOT NULL,
  PRIMARY KEY (`stop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `stop_times`
--

CREATE TABLE IF NOT EXISTS `stop_times` (
  `trip_id` bigint(20) unsigned NOT NULL,
  `arrival_time` time NOT NULL,
  `departure_time` time NOT NULL,
  `stop_id` int(11) NOT NULL,
  `stop_sequence` int(11) NOT NULL,
  `stop_headsign` varchar(150) CHARACTER SET utf8 DEFAULT NULL,
  `shape_dist_traveled` varchar(150) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `trips`
--

CREATE TABLE IF NOT EXISTS `trips` (
  `route_id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `trip_id` bigint(20) unsigned NOT NULL,
  `trip_headsign` varchar(150) CHARACTER SET utf8 DEFAULT NULL,
  `trip_short_name` varchar(5) CHARACTER SET utf8 DEFAULT NULL,
  `direction_id` tinyint(1) NOT NULL,
  `shape_id` varchar(150) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-05-2023 a las 09:38:09
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectodua`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `savestates`
--

CREATE TABLE `savestates` (
  `id` int(11) NOT NULL,
  `dateCreated` datetime NOT NULL DEFAULT current_timestamp(),
  `fields` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `savestates`
--

INSERT INTO `savestates` (`id`, `dateCreated`, `fields`) VALUES
(5, '2023-05-10 18:20:05', 'Aduana\\Declaración\\Nombre\\Dirección\\CIF\\Formu\\Lista carga\\2\\2\\ref\\dest\\respon finan\\pais prime\\pais tran\\pac\\delcar\\exped expor\\exped expor cod\\p origen\\234\\Polonia\\Iden nacion med trans partida\\1\\condis\\identi nacio\\divisa\\tipoCambio\\6\\-5\\4\\4\\asfd\\asdf\\-3\\-5\\2\\8\\-11\\Cod pais origen\\0\\10\\0\\contin\\doc car\\uds suple\\0\\certis autos\\estadis\\4\\aplazamiento\\identi\\obligado princi\\aduanas de paso previstas\\garantia\\aduana destino\\lugar y fecha'),
(6, '2023-05-10 18:24:17', 'a\\a\\a\\a\\a\\a\\a\\0\\0\\a\\a\\a\\a\\a\\a\\a\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\0\\0\\1\\1\\a\\a\\0\\0\\0\\0\\0\\a\\0\\10\\0\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\a\\a\\a'),
(7, '2023-05-10 18:24:36', 'Aduana\\Declaración\\Nombre\\Dirección\\CIF\\Formu\\Lista carga\\2\\2\\ref\\dest\\respon finan\\pais prime\\pais tran\\pac\\delcar\\exped expor\\exped expor cod\\p origen\\234\\Polonia\\Iden nacion med trans partida\\1\\condis\\identi nacio\\divisa\\tipoCambio\\6\\-5\\4\\4\\asfd\\asdf\\-3\\-5\\2\\8\\-11\\Cod pais origen\\0\\24\\0\\contin\\doc car\\uds suple\\0\\certis autos\\estadis\\4\\aplazamiento\\identi\\obligado princi\\aduanas de paso previstas\\garantia\\aduana destino\\lugar y fecha'),
(8, '2023-05-11 20:45:51', 'a\\a\\a\\a\\a\\a\\a\\0\\0\\a\\a\\a\\a\\a\\a\\a\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\0\\0\\1\\1\\a\\a\\0\\0\\0\\0\\0\\a\\0\\10\\0\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\a\\a\\a'),
(9, '2023-05-11 20:47:39', 'a\\a\\a\\a\\a\\a\\a\\0\\0\\a\\a\\a\\a\\a\\a\\a\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\0\\0\\1\\1\\a\\a\\0\\0\\0\\0\\0\\a\\0\\10\\0\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\a\\a\\a'),
(10, '2023-05-11 20:53:17', 'a\\a\\a\\a\\a\\a\\a\\0\\0\\a\\a\\a\\a\\a\\a\\a\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\0\\0\\1\\1\\a\\a\\0\\0\\0\\0\\0\\a\\0\\10\\0\\a\\a\\a\\0\\a\\a\\0\\a\\a\\a\\a\\a\\a\\a');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_credentials`
--

CREATE TABLE `user_credentials` (
  `UserName` varchar(40) NOT NULL,
  `UserPassword` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user_credentials`
--

INSERT INTO `user_credentials` (`UserName`, `UserPassword`) VALUES
('admin', '12345678'),
('dfdfdsfsdfsfdsdfsdfsasdfasdfasdfasdf', '12345678'),
('Yasuo', 'Yasuo1234');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `savestates`
--
ALTER TABLE `savestates`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_credentials`
--
ALTER TABLE `user_credentials`
  ADD PRIMARY KEY (`UserName`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `savestates`
--
ALTER TABLE `savestates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

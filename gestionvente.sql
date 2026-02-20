-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : ven. 20 fév. 2026 à 16:29
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `gestionvente`
--

-- --------------------------------------------------------

--
-- Structure de la table `approvisionnement`
--

CREATE TABLE `approvisionnement` (
  `id_article` bigint(20) NOT NULL,
  `id_fournisseur` bigint(20) NOT NULL,
  `quantite` int(11) NOT NULL,
  `date_approvisionnement` datetime NOT NULL DEFAULT current_timestamp(),
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `articles`
--

CREATE TABLE `articles` (
  `id_article` bigint(20) NOT NULL,
  `nom_article` varchar(100) NOT NULL,
  `prix` decimal(10,2) NOT NULL,
  `stock` int(11) NOT NULL CHECK (`stock` >= 0),
  `url_photo` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `articles`
--

INSERT INTO `articles` (`id_article`, `nom_article`, `prix`, `stock`, `url_photo`, `created_at`, `updated_at`, `description`) VALUES
(1, 'Chausssre', 2000.00, 2, NULL, '2026-01-26 19:09:23', '2026-02-19 22:57:25', NULL),
(2, 'Ananas', 2000.00, 1988, NULL, '2026-01-26 19:09:23', '2026-02-19 22:57:25', ''),
(3, 'banane', 2000.00, 0, NULL, '2026-01-26 19:09:23', '2026-02-19 22:57:25', NULL),
(4, 'Pomme', 2000.00, 3, NULL, '2026-01-26 19:13:35', '2026-02-19 22:57:25', 'Pomme frais'),
(6, 'Adidas', 300.00, 30, 'data/images/articles/article_20260220_151533_7265.png', '2026-01-26 19:13:35', '2026-02-20 15:15:36', ''),
(7, 'Chemise', 150000.00, 86, NULL, '2026-02-17 14:36:15', '2026-02-19 22:57:25', 'FILA'),
(8, 'Gucci', 1000.00, 10, NULL, '2026-02-17 16:07:31', '2026-02-19 22:57:25', 'll'),
(9, 'Gari', 50.00, 200, NULL, '2026-02-19 14:08:51', '2026-02-19 22:58:09', ''),
(10, 'Sucre', 50.00, 200, NULL, '2026-02-19 14:08:58', '2026-02-19 22:57:25', ''),
(12, 'Savon', 1234.00, 20, '', '2026-02-19 14:25:50', '2026-02-19 22:57:54', 'Savon pafumé'),
(13, 'Savon sucré', 200.00, 20, 'data/images/articles/article_20260219_223104_1456.png', '2026-02-19 22:31:48', '2026-02-19 23:04:37', ''),
(14, 'Orange', 300.00, 20, 'data/images/articles/article_20260219_231447_7115.jpg', '2026-02-19 23:15:01', '2026-02-19 23:15:01', 'Fruit'),
(17, 'Mangue', 200.00, 50, 'data/images/articles/article_20260220_125653_770.png', '2026-02-20 12:57:03', '2026-02-20 12:57:03', '');

-- --------------------------------------------------------

--
-- Structure de la table `article_categorie`
--

CREATE TABLE `article_categorie` (
  `id_article` bigint(20) NOT NULL,
  `id_categorie` int(11) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `article_categorie`
--

INSERT INTO `article_categorie` (`id_article`, `id_categorie`, `created_at`, `updated_at`) VALUES
(1, 1, '2026-02-19 01:45:09', '2026-02-19 01:45:09'),
(4, 1, '2026-02-19 01:45:29', '2026-02-19 01:45:29'),
(7, 2, '2026-02-19 01:46:49', '2026-02-19 01:46:49'),
(8, 2, '2026-02-19 01:47:10', '2026-02-19 01:47:10'),
(14, 1, '2026-02-19 23:15:01', '2026-02-19 23:15:01'),
(17, 1, '2026-02-20 12:57:03', '2026-02-20 12:57:03');

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE `categories` (
  `id_categorie` int(11) NOT NULL,
  `nom_categorie` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`id_categorie`, `nom_categorie`, `description`, `created_at`, `updated_at`) VALUES
(1, 'Fruit', 'Les fruits mangeable ', '2026-02-19 01:44:37', '2026-02-19 01:44:37'),
(2, 'A porter ', 'les vêtements,...', '2026-02-19 01:46:20', '2026-02-19 01:46:20');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

CREATE TABLE `clients` (
  `id_client` bigint(20) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `adresse` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`id_client`, `nom`, `prenom`, `email`, `telephone`, `adresse`, `created_at`, `updated_at`) VALUES
(14, 'Martin', 'Nafissa', 'Nafissa@email.com', '0987654321', 'Nikki', '2026-02-10 11:42:55', '2026-02-10 11:42:55'),
(15, 'YACOUBOU', 'Faoussane', 'faoussane@gmail.com', '1233444', 'Rue 10', '2026-02-10 11:42:55', '2026-02-10 11:42:55'),
(16, 'LAMINE', 'Yamal', 'yamal@gmail.com', '1233444', 'Rue 10', '2026-02-10 11:42:55', '2026-02-10 11:42:55'),
(17, 'Dupont', 'Jean', 'jean.dupont@email.com', '0123456789', '123 Rue de Paris', '2026-02-10 12:14:32', '2026-02-10 12:14:32'),
(18, 'Dupont', 'Jean', 'dupont@email.com', '0123456789', '123 Rue de Paris', '2026-02-10 12:31:12', '2026-02-10 12:31:12'),
(19, 'TABE', 'Jaydath', 'jay@gmail.com', '', 'Zopah', '2026-02-16 17:51:52', '2026-02-16 17:51:52'),
(20, 'Robert', 'Lewandowski', 'lewis@gmail.com', '012345566', 'Rue 12', '2026-02-17 12:25:32', '2026-02-17 12:25:32');

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

CREATE TABLE `commandes` (
  `id_commande` bigint(20) NOT NULL,
  `date_commande` datetime NOT NULL DEFAULT current_timestamp(),
  `statut` varchar(30) DEFAULT NULL,
  `id_client` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`id_commande`, `date_commande`, `statut`, `id_client`) VALUES
(1, '2026-02-10 12:44:13', 'PAYEE', 15),
(2, '2026-02-10 12:53:54', 'PAYEE', 15),
(3, '2026-02-10 13:01:45', 'PAYEE', 15),
(4, '2026-02-10 13:03:48', 'PAYEE', 15),
(5, '2026-02-10 13:51:47', 'PAYEE', 15),
(6, '2026-02-17 14:16:19', 'PAYEE', 20),
(7, '2026-02-17 14:24:48', 'PAYEE', 20),
(8, '2026-02-17 14:38:21', 'PAYEE', 20),
(9, '2026-02-17 16:06:27', 'PAYEE', 20),
(10, '2026-02-19 00:25:43', 'PAYEE', 20),
(11, '2026-02-19 02:00:31', 'PAYEE', 20),
(12, '2026-02-19 13:33:39', 'PAYEE', 20),
(13, '2026-02-19 16:35:23', 'EN_COURS', 20);

-- --------------------------------------------------------

--
-- Structure de la table `fournisseurs`
--

CREATE TABLE `fournisseurs` (
  `id_fournisseur` bigint(20) NOT NULL,
  `nom_fournisseur` varchar(100) DEFAULT NULL,
  `prenom_fournisseur` varchar(100) DEFAULT NULL,
  `contact_fournisseur` varchar(20) DEFAULT NULL,
  `adresse_fournisseur` varchar(100) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

CREATE TABLE `ligne_commande` (
  `id_commande` bigint(20) NOT NULL,
  `id_article` bigint(20) NOT NULL,
  `quantite` int(11) NOT NULL CHECK (`quantite` > 0),
  `prix_unitaire` decimal(10,2) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ligne_commande`
--

INSERT INTO `ligne_commande` (`id_commande`, `id_article`, `quantite`, `prix_unitaire`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 2000.00, '2026-02-10 12:44:13', '2026-02-10 12:44:13'),
(1, 2, 2, 2000.00, '2026-02-10 12:44:13', '2026-02-10 12:44:13'),
(2, 1, 1, 2000.00, '2026-02-10 12:53:54', '2026-02-10 12:53:54'),
(2, 2, 2, 2000.00, '2026-02-10 12:53:54', '2026-02-10 12:53:54'),
(3, 1, 1, 2000.00, '2026-02-10 13:01:45', '2026-02-10 13:01:45'),
(3, 2, 2, 2000.00, '2026-02-10 13:01:45', '2026-02-10 13:01:45'),
(4, 1, 1, 2000.00, '2026-02-10 13:03:48', '2026-02-10 13:03:48'),
(4, 2, 2, 2000.00, '2026-02-10 13:03:48', '2026-02-10 13:03:48'),
(5, 1, 1, 2000.00, '2026-02-10 13:51:47', '2026-02-10 13:51:47'),
(5, 2, 2, 2000.00, '2026-02-10 13:51:47', '2026-02-10 13:51:47'),
(6, 1, 1, 2000.00, '2026-02-17 14:16:19', '2026-02-17 14:16:19'),
(6, 3, 1, 2000.00, '2026-02-17 14:16:40', '2026-02-17 14:16:40'),
(7, 2, 10, 2000.00, '2026-02-17 14:24:48', '2026-02-17 14:24:48'),
(8, 2, 2, 2000.00, '2026-02-17 14:38:21', '2026-02-17 15:10:06'),
(8, 3, 1, 2000.00, '2026-02-17 15:10:20', '2026-02-17 15:10:20'),
(8, 4, 3, 2000.00, '2026-02-17 16:04:09', '2026-02-17 16:04:09'),
(8, 7, 3, 150000.00, '2026-02-17 16:04:27', '2026-02-17 16:04:27'),
(9, 3, 8, 2000.00, '2026-02-17 16:06:27', '2026-02-17 16:06:27'),
(10, 4, 3, 2000.00, '2026-02-19 01:48:42', '2026-02-19 01:48:42'),
(11, 1, 2, 2000.00, '2026-02-19 02:00:31', '2026-02-19 02:02:05'),
(11, 7, 11, 150000.00, '2026-02-19 02:00:39', '2026-02-19 02:01:20'),
(12, 4, 1, 2000.00, '2026-02-19 13:33:39', '2026-02-19 13:33:39'),
(13, 1, 2, 2000.00, '2026-02-19 17:03:29', '2026-02-19 17:07:19'),
(13, 4, 3, 2000.00, '2026-02-19 16:35:23', '2026-02-19 17:03:23'),
(13, 7, 5, 150000.00, '2026-02-19 17:11:50', '2026-02-19 17:31:13'),
(13, 8, 1, 1000.00, '2026-02-19 17:40:35', '2026-02-19 17:40:35');

-- --------------------------------------------------------

--
-- Structure de la table `paiements`
--

CREATE TABLE `paiements` (
  `id_paiement` bigint(20) NOT NULL,
  `date_paiement` datetime NOT NULL DEFAULT current_timestamp(),
  `montant` decimal(10,2) NOT NULL,
  `mode_paiement` varchar(50) DEFAULT NULL,
  `id_commande` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `paiements`
--

INSERT INTO `paiements` (`id_paiement`, `date_paiement`, `montant`, `mode_paiement`, `id_commande`, `created_at`, `updated_at`) VALUES
(1, '2026-02-10 12:53:54', 6000.00, 'CARTE', 2, '2026-02-10 12:53:54', '2026-02-10 12:53:54'),
(2, '2026-02-10 13:01:45', 6000.00, 'CARTE', 3, '2026-02-10 13:01:45', '2026-02-10 13:01:45'),
(3, '2026-02-10 13:03:48', 6000.00, 'CARTE', 4, '2026-02-10 13:03:48', '2026-02-10 13:03:48'),
(4, '2026-02-10 13:51:47', 6000.00, 'CARTE', 5, '2026-02-10 13:51:47', '2026-02-10 13:51:47'),
(5, '2026-02-17 14:24:07', 4000.00, 'Mobile Money', 6, '2026-02-17 14:24:07', '2026-02-17 14:24:07'),
(6, '2026-02-17 14:25:02', 20000.00, 'À la livraison', 7, '2026-02-17 14:25:02', '2026-02-17 14:25:02'),
(7, '2026-02-17 16:04:58', 462000.00, 'Mobile Money', 8, '2026-02-17 16:04:58', '2026-02-17 16:04:58'),
(8, '2026-02-17 16:06:32', 16000.00, 'Carte bancaire', 9, '2026-02-17 16:06:32', '2026-02-17 16:06:32'),
(9, '2026-02-19 01:49:22', 6000.00, 'Virement', 10, '2026-02-19 01:49:22', '2026-02-19 01:49:22'),
(10, '2026-02-19 02:02:14', 1654000.00, 'Virement', 11, '2026-02-19 02:02:14', '2026-02-19 02:02:14'),
(11, '2026-02-19 13:34:13', 2000.00, 'Carte bancaire', 12, '2026-02-19 13:34:13', '2026-02-19 13:34:13');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id_utilisateur` bigint(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role` enum('CLIENT','ADMIN') DEFAULT 'CLIENT',
  `id_client` bigint(20) DEFAULT NULL,
  `est_actif` tinyint(1) DEFAULT 1,
  `derniere_connexion` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id_utilisateur`, `email`, `mot_de_passe`, `role`, `id_client`, `est_actif`, `derniere_connexion`, `created_at`) VALUES
(14, 'Nafissa@email.com', 'hashed_password123', 'CLIENT', 14, 1, '2026-02-10 11:42:55', '2026-02-10 11:42:55'),
(15, 'faoussane@gmail.com', 'hashed_password123', 'ADMIN', 15, 1, '2026-02-20 11:44:19', '2026-02-10 11:42:55'),
(16, 'yamal@gmail.com', 'hashed_password123', 'CLIENT', 16, 1, '2026-02-10 11:42:55', '2026-02-10 11:42:55'),
(17, 'jean.dupont@email.com', 'hashed_password123', 'CLIENT', 17, 1, '2026-02-10 12:14:32', '2026-02-10 12:14:32'),
(18, 'dupont@email.com', 'hashed_password123', 'CLIENT', 18, 1, '2026-02-16 20:12:14', '2026-02-10 12:31:12'),
(19, 'jay@gmail.com', 'hashed_1234567', 'CLIENT', 19, 1, '2026-02-16 17:51:52', '2026-02-16 17:51:52'),
(20, 'lewis@gmail.com', 'hashed_123456', 'CLIENT', 20, 1, '2026-02-20 02:55:05', '2026-02-17 12:25:32');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `approvisionnement`
--
ALTER TABLE `approvisionnement`
  ADD PRIMARY KEY (`id_article`,`id_fournisseur`),
  ADD KEY `id_fournisseur` (`id_fournisseur`);

--
-- Index pour la table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id_article`),
  ADD UNIQUE KEY `nom_article` (`nom_article`);

--
-- Index pour la table `article_categorie`
--
ALTER TABLE `article_categorie`
  ADD PRIMARY KEY (`id_article`,`id_categorie`),
  ADD KEY `id_categorie` (`id_categorie`);

--
-- Index pour la table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id_categorie`);

--
-- Index pour la table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id_client`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_client` (`id_client`);

--
-- Index pour la table `fournisseurs`
--
ALTER TABLE `fournisseurs`
  ADD PRIMARY KEY (`id_fournisseur`);

--
-- Index pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`id_commande`,`id_article`),
  ADD KEY `id_article` (`id_article`);

--
-- Index pour la table `paiements`
--
ALTER TABLE `paiements`
  ADD PRIMARY KEY (`id_paiement`),
  ADD UNIQUE KEY `id_commande` (`id_commande`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `id_client` (`id_client`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `articles`
--
ALTER TABLE `articles`
  MODIFY `id_article` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `categories`
--
ALTER TABLE `categories`
  MODIFY `id_categorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `clients`
--
ALTER TABLE `clients`
  MODIFY `id_client` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `commandes`
--
ALTER TABLE `commandes`
  MODIFY `id_commande` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `fournisseurs`
--
ALTER TABLE `fournisseurs`
  MODIFY `id_fournisseur` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `paiements`
--
ALTER TABLE `paiements`
  MODIFY `id_paiement` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `approvisionnement`
--
ALTER TABLE `approvisionnement`
  ADD CONSTRAINT `approvisionnement_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `articles` (`id_article`),
  ADD CONSTRAINT `approvisionnement_ibfk_2` FOREIGN KEY (`id_fournisseur`) REFERENCES `fournisseurs` (`id_fournisseur`);

--
-- Contraintes pour la table `article_categorie`
--
ALTER TABLE `article_categorie`
  ADD CONSTRAINT `article_categorie_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `articles` (`id_article`),
  ADD CONSTRAINT `article_categorie_ibfk_2` FOREIGN KEY (`id_categorie`) REFERENCES `categories` (`id_categorie`);

--
-- Contraintes pour la table `commandes`
--
ALTER TABLE `commandes`
  ADD CONSTRAINT `commandes_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`);

--
-- Contraintes pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD CONSTRAINT `ligne_commande_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commandes` (`id_commande`),
  ADD CONSTRAINT `ligne_commande_ibfk_2` FOREIGN KEY (`id_article`) REFERENCES `articles` (`id_article`);

--
-- Contraintes pour la table `paiements`
--
ALTER TABLE `paiements`
  ADD CONSTRAINT `paiements_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commandes` (`id_commande`);

--
-- Contraintes pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD CONSTRAINT `utilisateurs_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_client`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

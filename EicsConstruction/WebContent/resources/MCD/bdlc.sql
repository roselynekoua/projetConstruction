-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Jeu 03 Mars 2016 à 00:46
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `bdlc`
--
CREATE DATABASE IF NOT EXISTS `bdlc` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `bdlc`;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `NUM_CLT` char(30) NOT NULL,
  `NUM_TYPECLT` char(30) NOT NULL,
  `CODE_SEXE` char(30) NOT NULL,
  `CODE_PROF` char(30) NOT NULL,
  `CODE_NAT` char(30) NOT NULL,
  `CODE_UTILISATEUR` char(20) NOT NULL,
  `CODE_PAYS` char(30) NOT NULL,
  `NOM_CLT` varchar(20) DEFAULT NULL,
  `PRENOM_CLT` varchar(50) DEFAULT NULL,
  `MATRICULE_CLT` char(30) DEFAULT NULL,
  `DATENAISS_CLT` date DEFAULT NULL,
  `BOITE_POST_CLT` char(30) DEFAULT NULL,
  `NUMTEL_CLT` char(30) DEFAULT NULL,
  `FAX_CLT` char(30) DEFAULT NULL,
  `EMAIL_CLT` char(30) DEFAULT NULL,
  `COMMUNE_CLT` char(30) DEFAULT NULL,
  `QUARTIER_CLT` char(30) DEFAULT NULL,
  `NDOMICILE_CLT` char(30) DEFAULT NULL,
  `VILLE_CLT` char(30) DEFAULT NULL,
  `DATECREA_CLT` date DEFAULT NULL,
  `PHOTO_CLT` longblob,
  `CIVILITE_CLT` char(10) DEFAULT NULL,
  `MOT_DE_PASS_CLT` char(30) DEFAULT NULL,
  `LOGIN_CLT` char(30) DEFAULT NULL,
  PRIMARY KEY (`NUM_CLT`),
  KEY `FK_ACHETTER2` (`CODE_UTILISATEUR`),
  KEY `FK_AVOIR1` (`CODE_SEXE`),
  KEY `FK_ETRE1` (`CODE_NAT`),
  KEY `FK_ETRE2` (`NUM_TYPECLT`),
  KEY `FK_ETRE3` (`CODE_PAYS`),
  KEY `FK_EXERCER` (`CODE_PROF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`NUM_CLT`, `NUM_TYPECLT`, `CODE_SEXE`, `CODE_PROF`, `CODE_NAT`, `CODE_UTILISATEUR`, `CODE_PAYS`, `NOM_CLT`, `PRENOM_CLT`, `MATRICULE_CLT`, `DATENAISS_CLT`, `BOITE_POST_CLT`, `NUMTEL_CLT`, `FAX_CLT`, `EMAIL_CLT`, `COMMUNE_CLT`, `QUARTIER_CLT`, `NDOMICILE_CLT`, `VILLE_CLT`, `DATECREA_CLT`, `PHOTO_CLT`, `CIVILITE_CLT`, `MOT_DE_PASS_CLT`, `LOGIN_CLT`) VALUES
('CLT00001', 'Typclt004', 'codesex001', 'Codprof01', 'Codnat01', 'USER02', 'codpays002', 'ahoua', 'jean', '22', '2016-03-08', '', '', '', '', '', '', '220', 'edd', '2016-03-07', NULL, 'Mme', NULL, NULL),
('CLT00002', 'Typclt006', 'codesex002', 'Codprof02', 'codenat001', 'USER02', 'codpays001', 'adjoba', 'joie', '55', '2016-03-16', '', '', '', '', '', '', '78', 'dee', '2016-03-08', NULL, 'Mlle', NULL, NULL),
('CLT00003', 'Typclt010', 'codesex001', 'Codprof02', 'codenat001', 'USER02', 'codpays001', 'ndri', 'apolinaire', '55', '2016-03-22', '', '', '', '', '', '', '22', 'abj', '2016-03-15', NULL, 'Mlle', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `contrat`
--

CREATE TABLE IF NOT EXISTS `contrat` (
  `CODE_CONTRAT` char(30) NOT NULL,
  `CODE_MAIS` char(20) NOT NULL,
  `NUM_CLT` char(30) NOT NULL,
  `CODE_DONSUP` char(10) NOT NULL,
  `ETAT_CONTRAT` char(30) DEFAULT NULL,
  `DATE_DEBUT_CONTRAT` date DEFAULT NULL,
  `DATE_FIN_CONTRAT` date DEFAULT NULL,
  `APPORT_INITIAL` decimal(15,3) DEFAULT NULL,
  `FRAIS_DOSSIER` decimal(15,3) DEFAULT NULL,
  `REMISE_CONTRAT` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`CODE_CONTRAT`),
  KEY `FK_ACHETTER` (`CODE_MAIS`),
  KEY `FK_AJOUTER` (`CODE_DONSUP`),
  KEY `FK_APPARTENIR` (`NUM_CLT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `contrat`
--

INSERT INTO `contrat` (`CODE_CONTRAT`, `CODE_MAIS`, `NUM_CLT`, `CODE_DONSUP`, `ETAT_CONTRAT`, `DATE_DEBUT_CONTRAT`, `DATE_FIN_CONTRAT`, `APPORT_INITIAL`, `FRAIS_DOSSIER`, `REMISE_CONTRAT`) VALUES
('codcont01', 'maison01', 'CLT00001', 'Coddons04', 'Non bouclé', '2016-03-02', '2016-03-31', '1000.000', '4800000.000', NULL),
('codcont02', 'maison01', 'CLT00002', 'Coddons05', '', '2015-01-02', '2017-03-02', '1000.000', NULL, NULL),
('codcont03', 'maison01', 'CLT00003', 'Coddons06', 'Bouclé', '2016-03-08', '2016-03-14', '200000.000', '4800000.000', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `donnees_sup`
--

CREATE TABLE IF NOT EXISTS `donnees_sup` (
  `CODE_DONSUP` char(10) NOT NULL,
  `QTE_M_DONSUP` decimal(5,0) DEFAULT NULL,
  `COUT_U_DONSUP` decimal(15,0) DEFAULT NULL,
  `COUT_T_DONSUP` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`CODE_DONSUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `donnees_sup`
--

INSERT INTO `donnees_sup` (`CODE_DONSUP`, `QTE_M_DONSUP`, `COUT_U_DONSUP`, `COUT_T_DONSUP`) VALUES
('Coddons01', '1', '35000', '35000'),
('Coddons02', '2', '35000', '70000'),
('Coddons03', NULL, '580000', NULL),
('Coddons04', '10', '52', '520'),
('Coddons05', '120', NULL, '1200'),
('Coddons06', '0', '0', '0');

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE IF NOT EXISTS `facture` (
  `CODE_FACT` char(30) NOT NULL,
  `NUM_CLT` char(30) NOT NULL,
  `CODE_UTILISATEUR` char(20) NOT NULL,
  `CODE_VERS` char(30) NOT NULL,
  `DATE_FACT` date DEFAULT NULL,
  `MONTANT_FACT` decimal(15,0) DEFAULT NULL,
  `MONTANT_TTC` decimal(15,0) DEFAULT NULL,
  `REMISE` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`CODE_FACT`),
  KEY `FK_CORRESPONDRE2` (`NUM_CLT`),
  KEY `FK_CORRESPONDRE3` (`CODE_UTILISATEUR`),
  KEY `FK_LIER` (`CODE_VERS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `maison`
--

CREATE TABLE IF NOT EXISTS `maison` (
  `CODE_MAIS` char(20) NOT NULL,
  `CODE_TYPEMAIS` char(30) NOT NULL,
  `CODE_TARIF` char(20) NOT NULL,
  `CODE_UTILISATEUR` char(20) NOT NULL,
  `DSG_MAIS` varchar(30) DEFAULT NULL,
  `SUPERFICIE` decimal(8,0) DEFAULT NULL,
  `LOCALISATION` char(50) DEFAULT NULL,
  `PHOTO_MAIS` longblob,
  PRIMARY KEY (`CODE_MAIS`),
  KEY `FK_CORRESPONDRE` (`CODE_TYPEMAIS`),
  KEY `FK_COUTER` (`CODE_TARIF`),
  KEY `FK_VENDRE` (`CODE_UTILISATEUR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `maison`
--

INSERT INTO `maison` (`CODE_MAIS`, `CODE_TYPEMAIS`, `CODE_TARIF`, `CODE_UTILISATEUR`, `DSG_MAIS`, `SUPERFICIE`, `LOCALISATION`, `PHOTO_MAIS`) VALUES
('maison01', 'Codtypmais09', 'Codtar03', 'USER02', 'villa 4pieces', '300', 'angre', NULL),
('maison02', 'Codtypmais10', 'Codtar02', 'USER02', 'Villa  duplex', '350', 'abobo', NULL),
('maison03', 'Codtypmais11', 'Codtar01', 'USER01', 'Villa Basse', '300', 'angre', NULL),
('maison04', 'Codtypmais12', 'Codtar03', 'USER02', 'Villa Duplex', '400', 'cocody', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `nationalite`
--

CREATE TABLE IF NOT EXISTS `nationalite` (
  `CODE_NAT` char(30) NOT NULL,
  `LIBELLE_NAT` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_NAT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `nationalite`
--

INSERT INTO `nationalite` (`CODE_NAT`, `LIBELLE_NAT`) VALUES
('codenat001', 'Congolaise'),
('Codnat01', 'Ivoirienne'),
('Codnat02', 'française');

-- --------------------------------------------------------

--
-- Structure de la table `pays`
--

CREATE TABLE IF NOT EXISTS `pays` (
  `CODE_PAYS` char(30) NOT NULL,
  `LIBELLE_PAYS` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_PAYS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `pays`
--

INSERT INTO `pays` (`CODE_PAYS`, `LIBELLE_PAYS`) VALUES
('codpays001', 'Ghana'),
('codpays002', 'Côte d''Ivoire'),
('codpays003', 'Gabon'),
('codpays004', 'France');

-- --------------------------------------------------------

--
-- Structure de la table `profession`
--

CREATE TABLE IF NOT EXISTS `profession` (
  `CODE_PROF` char(30) NOT NULL,
  `LIBELLE_PROF` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_PROF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `profession`
--

INSERT INTO `profession` (`CODE_PROF`, `LIBELLE_PROF`) VALUES
('Codprof01', 'Medecin'),
('Codprof02', 'Informaticien');

-- --------------------------------------------------------

--
-- Structure de la table `profil`
--

CREATE TABLE IF NOT EXISTS `profil` (
  `CODE_PROFIL` char(30) NOT NULL,
  `LIBELLE_PROFIL` char(30) DEFAULT NULL,
  `DATE_PROFIL_UTILISATEUR` date DEFAULT NULL,
  PRIMARY KEY (`CODE_PROFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `profil`
--

INSERT INTO `profil` (`CODE_PROFIL`, `LIBELLE_PROFIL`, `DATE_PROFIL_UTILISATEUR`) VALUES
('PROFIL001', 'ROLE_ADMIN', '2016-02-28'),
('PROFIL002', 'ROLE_GESTION', '2016-02-28'),
('PROFIL003', 'ROLE_CAISSIER', '2016-02-28');

-- --------------------------------------------------------

--
-- Structure de la table `sexe`
--

CREATE TABLE IF NOT EXISTS `sexe` (
  `CODE_SEXE` char(30) NOT NULL,
  `LIBELLE_SEXE` char(20) DEFAULT NULL,
  PRIMARY KEY (`CODE_SEXE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sexe`
--

INSERT INTO `sexe` (`CODE_SEXE`, `LIBELLE_SEXE`) VALUES
('codesex001', 'Masculin'),
('codesex002', 'Féminin');

-- --------------------------------------------------------

--
-- Structure de la table `tarif`
--

CREATE TABLE IF NOT EXISTS `tarif` (
  `CODE_TARIF` char(20) NOT NULL,
  `COUT_TTC_TARIF` decimal(15,0) DEFAULT NULL,
  `CLOTURE_FCFA_TARIF` decimal(15,0) DEFAULT NULL,
  `COUT_TOTAL_TARIF` decimal(15,0) DEFAULT NULL,
  PRIMARY KEY (`CODE_TARIF`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `tarif`
--

INSERT INTO `tarif` (`CODE_TARIF`, `COUT_TTC_TARIF`, `CLOTURE_FCFA_TARIF`, `COUT_TOTAL_TARIF`) VALUES
('Codtar01', '37642000', '4000000', '41642000'),
('Codtar02', '58124000', '4800000', '62924000'),
('Codtar03', '109000000', '4800000', '11460000');

-- --------------------------------------------------------

--
-- Structure de la table `type_client`
--

CREATE TABLE IF NOT EXISTS `type_client` (
  `NUM_TYPECLT` char(30) NOT NULL,
  `STATUT_TYPECLT` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`NUM_TYPECLT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_client`
--

INSERT INTO `type_client` (`NUM_TYPECLT`, `STATUT_TYPECLT`) VALUES
('blag aaa', 'Local'),
('Typclt001', 'Local'),
('Typclt002', 'Local'),
('Typclt003', 'Local'),
('Typclt004', 'Local'),
('Typclt005', 'Diaspora'),
('Typclt006', 'Diaspora'),
('Typclt007', 'Diaspora'),
('Typclt008', 'Diaspora'),
('Typclt009', 'Diaspora'),
('Typclt010', 'Local');

-- --------------------------------------------------------

--
-- Structure de la table `type_mais`
--

CREATE TABLE IF NOT EXISTS `type_mais` (
  `CODE_TYPEMAIS` char(30) NOT NULL,
  `LIBELLE_TYPEMAIS` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_TYPEMAIS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_mais`
--

INSERT INTO `type_mais` (`CODE_TYPEMAIS`, `LIBELLE_TYPEMAIS`) VALUES
('Codtypmais01', '4 pieces'),
('Codtypmais02', '5 pieces'),
('Codtypmais03', '6 pieces'),
('Codtypmais05', '5 pieces'),
('Codtypmais06', '5 pieces'),
('Codtypmais07', '5 pieces'),
('Codtypmais08', '5 pieces'),
('Codtypmais09', '6 pieces'),
('Codtypmais10', '4 pieces'),
('Codtypmais11', '4 pieces'),
('Codtypmais12', '5 pieces'),
('Codtypmais13', '4 pieces');

-- --------------------------------------------------------

--
-- Structure de la table `type_versement`
--

CREATE TABLE IF NOT EXISTS `type_versement` (
  `CODE_TYPEVERS` char(30) NOT NULL,
  `LIBELLE_TYPEVERS` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_TYPEVERS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `type_versement`
--

INSERT INTO `type_versement` (`CODE_TYPEVERS`, `LIBELLE_TYPEVERS`) VALUES
('codtver001', 'Chèque'),
('codtver002', 'Chèque'),
('codtver003', 'Chèque'),
('codtver004', 'Chèque'),
('codtver005', 'Chèque'),
('codtver006', 'Chèque'),
('codtver007', 'Chèque'),
('codtver008', 'Chèque');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE IF NOT EXISTS `utilisateur` (
  `CODE_UTILISATEUR` char(20) NOT NULL,
  `CODE_NAT` char(30) NOT NULL,
  `CODE_SEXE` char(30) NOT NULL,
  `CODE_PROFIL` char(30) NOT NULL,
  `CODE_PAYS` char(30) NOT NULL,
  `MATRICULE` char(20) DEFAULT NULL,
  `NOM_UTILISATEUR` char(25) DEFAULT NULL,
  `PRENOM_UTILISATEUR` char(60) DEFAULT NULL,
  `DATE_CREATION_UTILISATEUR` date DEFAULT NULL,
  `LOGIN_UTILISATEUR` char(30) DEFAULT NULL,
  `MOT_PASSE` char(30) DEFAULT NULL,
  `MAIL_UTILISATEUR` char(50) DEFAULT NULL,
  `ACTIVITE` tinyint(1) DEFAULT NULL,
  `BOITE_POST_UT` char(30) DEFAULT NULL,
  `NUMTEL_UT` char(30) DEFAULT NULL,
  `FAX_UT` char(30) DEFAULT NULL,
  `PHOTO_UT` longblob,
  PRIMARY KEY (`CODE_UTILISATEUR`),
  KEY `FK_AVOIR2` (`CODE_SEXE`),
  KEY `FK_AVOIR_PROFIL` (`CODE_PROFIL`),
  KEY `FK_ETRE_DE` (`CODE_NAT`),
  KEY `FK_ETRE_DE2` (`CODE_PAYS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `utilisateur`
--

INSERT INTO `utilisateur` (`CODE_UTILISATEUR`, `CODE_NAT`, `CODE_SEXE`, `CODE_PROFIL`, `CODE_PAYS`, `MATRICULE`, `NOM_UTILISATEUR`, `PRENOM_UTILISATEUR`, `DATE_CREATION_UTILISATEUR`, `LOGIN_UTILISATEUR`, `MOT_PASSE`, `MAIL_UTILISATEUR`, `ACTIVITE`, `BOITE_POST_UT`, `NUMTEL_UT`, `FAX_UT`, `PHOTO_UT`) VALUES
('USER01', 'Codnat01', 'codesex001', 'PROFIL001', 'codpays001', 'mat1452', 'koua', 'martial', '2016-02-28', 'admin', 'admin', 'admin@yahoo.fr', 1, 'bp0122', '06187993', NULL, NULL),
('USER02', 'Codnat02', 'codesex001', 'PROFIL002', 'codpays001', 'mart5888', 'kouko', 'andre', '2016-03-01', 'gestion', 'gestion', 'tttt@yahoo.fr', 1, NULL, '06895566', NULL, NULL),
('USER03', 'Codnat01', 'codesex002', 'PROFIL003', 'codpays001', 'mar0025y', 'usercaisse', 'succes', '2016-02-28', 'caisse', 'caisse', 'admincaisse@yahoo.fr', 1, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `versement`
--

CREATE TABLE IF NOT EXISTS `versement` (
  `CODE_VERS` char(30) NOT NULL,
  `NUM_CLT` char(30) NOT NULL,
  `CODE_TYPEVERS` char(30) NOT NULL,
  `CODE_UTILISATEUR` char(20) NOT NULL,
  `ETAT_VERS` char(30) DEFAULT NULL,
  `DATE_VERS` date DEFAULT NULL,
  `NCHEQUE` char(30) DEFAULT NULL,
  PRIMARY KEY (`CODE_VERS`),
  KEY `FK_CONCERNE` (`CODE_TYPEVERS`),
  KEY `FK_EFFECTUER` (`NUM_CLT`),
  KEY `FK_VERSER` (`CODE_UTILISATEUR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `versement`
--

INSERT INTO `versement` (`CODE_VERS`, `NUM_CLT`, `CODE_TYPEVERS`, `CODE_UTILISATEUR`, `ETAT_VERS`, `DATE_VERS`, `NCHEQUE`) VALUES
('codvers001', 'CLT00002', 'codtver007', 'USER03', 'Partial', '2016-03-15', ''),
('codvers002', 'CLT00002', 'codtver008', 'USER03', 'Partial', '2016-03-08', '44');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `FK_ACHETTER2` FOREIGN KEY (`CODE_UTILISATEUR`) REFERENCES `utilisateur` (`CODE_UTILISATEUR`),
  ADD CONSTRAINT `FK_AVOIR1` FOREIGN KEY (`CODE_SEXE`) REFERENCES `sexe` (`CODE_SEXE`),
  ADD CONSTRAINT `FK_ETRE1` FOREIGN KEY (`CODE_NAT`) REFERENCES `nationalite` (`CODE_NAT`),
  ADD CONSTRAINT `FK_ETRE2` FOREIGN KEY (`NUM_TYPECLT`) REFERENCES `type_client` (`NUM_TYPECLT`),
  ADD CONSTRAINT `FK_ETRE3` FOREIGN KEY (`CODE_PAYS`) REFERENCES `pays` (`CODE_PAYS`),
  ADD CONSTRAINT `FK_EXERCER` FOREIGN KEY (`CODE_PROF`) REFERENCES `profession` (`CODE_PROF`);

--
-- Contraintes pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `FK_ACHETTER` FOREIGN KEY (`CODE_MAIS`) REFERENCES `maison` (`CODE_MAIS`),
  ADD CONSTRAINT `FK_AJOUTER` FOREIGN KEY (`CODE_DONSUP`) REFERENCES `donnees_sup` (`CODE_DONSUP`),
  ADD CONSTRAINT `FK_APPARTENIR` FOREIGN KEY (`NUM_CLT`) REFERENCES `client` (`NUM_CLT`);

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `FK_CORRESPONDRE2` FOREIGN KEY (`NUM_CLT`) REFERENCES `client` (`NUM_CLT`),
  ADD CONSTRAINT `FK_CORRESPONDRE3` FOREIGN KEY (`CODE_UTILISATEUR`) REFERENCES `utilisateur` (`CODE_UTILISATEUR`),
  ADD CONSTRAINT `FK_LIER` FOREIGN KEY (`CODE_VERS`) REFERENCES `versement` (`CODE_VERS`);

--
-- Contraintes pour la table `maison`
--
ALTER TABLE `maison`
  ADD CONSTRAINT `FK_CORRESPONDRE` FOREIGN KEY (`CODE_TYPEMAIS`) REFERENCES `type_mais` (`CODE_TYPEMAIS`),
  ADD CONSTRAINT `FK_COUTER` FOREIGN KEY (`CODE_TARIF`) REFERENCES `tarif` (`CODE_TARIF`),
  ADD CONSTRAINT `FK_VENDRE` FOREIGN KEY (`CODE_UTILISATEUR`) REFERENCES `utilisateur` (`CODE_UTILISATEUR`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK_AVOIR2` FOREIGN KEY (`CODE_SEXE`) REFERENCES `sexe` (`CODE_SEXE`),
  ADD CONSTRAINT `FK_AVOIR_PROFIL` FOREIGN KEY (`CODE_PROFIL`) REFERENCES `profil` (`CODE_PROFIL`),
  ADD CONSTRAINT `FK_ETRE_DE` FOREIGN KEY (`CODE_NAT`) REFERENCES `nationalite` (`CODE_NAT`),
  ADD CONSTRAINT `FK_ETRE_DE2` FOREIGN KEY (`CODE_PAYS`) REFERENCES `pays` (`CODE_PAYS`);

--
-- Contraintes pour la table `versement`
--
ALTER TABLE `versement`
  ADD CONSTRAINT `FK_CONCERNE` FOREIGN KEY (`CODE_TYPEVERS`) REFERENCES `type_versement` (`CODE_TYPEVERS`),
  ADD CONSTRAINT `FK_EFFECTUER` FOREIGN KEY (`NUM_CLT`) REFERENCES `client` (`NUM_CLT`),
  ADD CONSTRAINT `FK_VERSER` FOREIGN KEY (`CODE_UTILISATEUR`) REFERENCES `utilisateur` (`CODE_UTILISATEUR`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

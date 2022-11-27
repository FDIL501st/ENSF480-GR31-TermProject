DROP DATABASE IF EXISTS movie_database;
CREATE DATABASE movie_database; 
USE movie_database;

DROP TABLE IF EXISTS theatre1;

CREATE TABLE theatre1 (
  `movie_name` VARCHAR(50) CHARACTER SET 'ascii' NOT NULL COMMENT 'name of movie',
  `show_time` DATETIME NOT NULL COMMENT 'date and time of movie showing',
  `release_date` DATETIME NOT NULL COMMENT 'the release date of the movie',
  `seat1` TINYINT NOT NULL DEFAULT 1,
  `seat2` TINYINT NOT NULL DEFAULT 1,
  `seat3` TINYINT NOT NULL DEFAULT 1,
  `seat4` TINYINT NOT NULL DEFAULT 1,
  `seat5` TINYINT NOT NULL DEFAULT 1,
  `seat6` TINYINT NOT NULL DEFAULT 1,
  `seat7` TINYINT NOT NULL DEFAULT 1,
  `seat8` TINYINT NOT NULL DEFAULT 1,
  `seat9` TINYINT NOT NULL DEFAULT 1,
  `seat10` TINYINT NOT NULL DEFAULT 1,
  `seat11` TINYINT NOT NULL DEFAULT 1,
  `seat12` TINYINT NOT NULL DEFAULT 1,
  `seat13` TINYINT NOT NULL DEFAULT 1,
  `seat14` TINYINT NOT NULL DEFAULT 1,
  `seat15` TINYINT NOT NULL DEFAULT 1,
  `seat16` TINYINT NOT NULL DEFAULT 1,
  `seat17` TINYINT NOT NULL DEFAULT 1,
  `seat18` TINYINT NOT NULL DEFAULT 1,
  `seat19` TINYINT NOT NULL DEFAULT 1,
  `seat20` TINYINT NOT NULL DEFAULT 1,
  `seat21` TINYINT NOT NULL DEFAULT 1,
  `seat22` TINYINT NOT NULL DEFAULT 1,
  `seat23` TINYINT NOT NULL DEFAULT 1,
  `seat24` TINYINT NOT NULL DEFAULT 1,
  `seat25` TINYINT NOT NULL DEFAULT 1,
  `seat26` TINYINT NOT NULL DEFAULT 1,
  `seat27` TINYINT NOT NULL DEFAULT 1,
  `seat28` TINYINT NOT NULL DEFAULT 1,
  `seat29` TINYINT NOT NULL DEFAULT 1,
  `seat30` TINYINT NOT NULL DEFAULT 1
  );


DROP TABLE IF EXISTS registered_users;

CREATE TABLE registered_users (
  email VARCHAR(100) CHARACTER SET 'ascii' NOT NULL,
  password VARCHAR(20) CHARACTER SET 'ascii' NOT NULL,
  first_name VARCHAR(30) CHARACTER SET 'ascii' NOT NULL,
  last_name VARCHAR(30) CHARACTER SET 'ascii' NOT NULL,
  address VARCHAR(50) CHARACTER SET 'ascii' NOT NULL,
  card_number VARCHAR(20) CHARACTER SET 'ascii' NOT NULL
);

SELECT * FROM theatre1;


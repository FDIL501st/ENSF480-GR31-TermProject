DROP DATABASE IF EXISTS movie_database;
CREATE DATABASE movie_database; 
USE movie_database;

DROP TABLE IF EXISTS theatre1;
CREATE TABLE theatre1 (
  `movie_name` VARCHAR(50) CHARACTER SET 'ascii' NOT NULL COMMENT 'name of movie',
  `show_time` TIMESTAMP NOT NULL COMMENT 'date and time of movie showing',
  primary key (movie_name, show_time),
  `release_date` DATE NOT NULL COMMENT 'the release date of the movie',
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
  `seat30` TINYINT NOT NULL DEFAULT 1,
  `seat31` TINYINT NOT NULL DEFAULT 1,
  `seat32` TINYINT NOT NULL DEFAULT 1,
  `seat33` TINYINT NOT NULL DEFAULT 1,
  `seat34` TINYINT NOT NULL DEFAULT 1,
  `seat35` TINYINT NOT NULL DEFAULT 1,
  `seat36` TINYINT NOT NULL DEFAULT 1,
  `seat37` TINYINT NOT NULL DEFAULT 1,
  `seat38` TINYINT NOT NULL DEFAULT 1,
  `seat39` TINYINT NOT NULL DEFAULT 1,
  `seat40` TINYINT NOT NULL DEFAULT 1,
  `seat41` TINYINT NOT NULL DEFAULT 1,
  `seat42` TINYINT NOT NULL DEFAULT 1,
  `seat43` TINYINT NOT NULL DEFAULT 1,
  `seat44` TINYINT NOT NULL DEFAULT 1,
  `seat45` TINYINT NOT NULL DEFAULT 1,
  `seat46` TINYINT NOT NULL DEFAULT 1,
  `seat47` TINYINT NOT NULL DEFAULT 1,
  `seat48` TINYINT NOT NULL DEFAULT 1,
  `seat49` TINYINT NOT NULL DEFAULT 1,
  `seat50` TINYINT NOT NULL DEFAULT 1,
  `seat51` TINYINT NOT NULL DEFAULT 1,
  `seat52` TINYINT NOT NULL DEFAULT 1,
  `seat53` TINYINT NOT NULL DEFAULT 1,
  `seat54` TINYINT NOT NULL DEFAULT 1,
  `seat55` TINYINT NOT NULL DEFAULT 1,
  `seat56` TINYINT NOT NULL DEFAULT 1,
  `seat57` TINYINT NOT NULL DEFAULT 1,
  `seat58` TINYINT NOT NULL DEFAULT 1,
  `seat59` TINYINT NOT NULL DEFAULT 1,
  `seat60` TINYINT NOT NULL DEFAULT 1,
  `seat61` TINYINT NOT NULL DEFAULT 1,
  `seat62` TINYINT NOT NULL DEFAULT 1,
  `seat63` TINYINT NOT NULL DEFAULT 1,
  `seat64` TINYINT NOT NULL DEFAULT 1,
  `seat65` TINYINT NOT NULL DEFAULT 1,
  `seat66` TINYINT NOT NULL DEFAULT 1,
  `seat67` TINYINT NOT NULL DEFAULT 1,
  `seat68` TINYINT NOT NULL DEFAULT 1,
  `seat69` TINYINT NOT NULL DEFAULT 1,
  `seat70` TINYINT NOT NULL DEFAULT 1,
  `seat71` TINYINT NOT NULL DEFAULT 1,
  `seat72` TINYINT NOT NULL DEFAULT 1,
  `seat73` TINYINT NOT NULL DEFAULT 1,
  `seat74` TINYINT NOT NULL DEFAULT 1,
  `seat75` TINYINT NOT NULL DEFAULT 1,
  `seat76` TINYINT NOT NULL DEFAULT 1,
  `seat77` TINYINT NOT NULL DEFAULT 1,
  `seat78` TINYINT NOT NULL DEFAULT 1,
  `seat79` TINYINT NOT NULL DEFAULT 1,
  `seat80` TINYINT NOT NULL DEFAULT 1,
  `seat81` TINYINT NOT NULL DEFAULT 1,
  `seat82` TINYINT NOT NULL DEFAULT 1,
  `seat83` TINYINT NOT NULL DEFAULT 1,
  `seat84` TINYINT NOT NULL DEFAULT 1,
  `seat85` TINYINT NOT NULL DEFAULT 1,
  `seat86` TINYINT NOT NULL DEFAULT 1,
  `seat87` TINYINT NOT NULL DEFAULT 1,
  `seat88` TINYINT NOT NULL DEFAULT 1,
  `seat89` TINYINT NOT NULL DEFAULT 1,
  `seat90` TINYINT NOT NULL DEFAULT 1,
  `seat91` TINYINT NOT NULL DEFAULT 1,
  `seat92` TINYINT NOT NULL DEFAULT 1,
  `seat93` TINYINT NOT NULL DEFAULT 1,
  `seat94` TINYINT NOT NULL DEFAULT 1,
  `seat95` TINYINT NOT NULL DEFAULT 1,
  `seat96` TINYINT NOT NULL DEFAULT 1,
  `seat97` TINYINT NOT NULL DEFAULT 1,
  `seat98` TINYINT NOT NULL DEFAULT 1,
  `seat99` TINYINT NOT NULL DEFAULT 1,
  `seat100` TINYINT NOT NULL DEFAULT 1
);

DROP TABLE IF EXISTS registered_users;
CREATE TABLE registered_users (
  email VARCHAR(100) CHARACTER SET 'ascii' NOT NULL,
  primary key (email),
  `password` VARCHAR(20) CHARACTER SET 'ascii' NOT NULL,
  first_name VARCHAR(30) CHARACTER SET 'ascii' NOT NULL,
  last_name VARCHAR(30) CHARACTER SET 'ascii' NOT NULL,
  `address` VARCHAR(50) CHARACTER SET 'ascii' NOT NULL,
  card_number VARCHAR(20) CHARACTER SET 'ascii' NOT NULL,
  registration_date DATE NOT NULL COMMENT 'date user registered'
);

DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets (
  movie_name VARCHAR(50) CHARACTER SET 'ascii' NOT NULL COMMENT 'name of movie',
  show_time TIMESTAMP NOT NULL COMMENT 'date and time of movie showing',
  seat_num INT NOT NULL COMMENT 'the seat the ticket reserves.'
);

DROP TABLE IF EXISTS codes;
CREATE TABLE codes (
  `ID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `value` DOUBLE NOT NULL DEFAULT 0 COMMENT 'value of code. The monetary/$ value that code represents.',
  `expiry` DATE NOT NULL COMMENT 'Expiry Date of the code',
  PRIMARY KEY (`ID`)
);




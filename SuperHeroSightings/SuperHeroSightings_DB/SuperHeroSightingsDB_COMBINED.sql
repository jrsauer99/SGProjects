DROP DATABASE IF EXISTS SuperHeroSightings;
CREATE DATABASE IF NOT EXISTS SuperHeroSightings;

-- SuperHero ***************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS SuperHero
(SuperHeroID INT NOT NULL auto_increment,
SuperHeroName VARCHAR (30) NOT NULL,
Description VARCHAR (100) NOT NULL,
-- SuperPowerID INT NOT NULL,
PRIMARY KEY (SuperHeroID)
);

-- 
-- USE SuperHeroSightings;
INSERT INTO SuperHero
(SuperHeroID, SuperHeroName,Description)
VALUES 
('1','Deuce','One eyed wonder'),
('2','Bella','Crazy Lady'),
('3','Sniffles','Gray fur baby'),
('4','Ellie Mae','The wise woman')
;
-- 
-- SuperPower *****************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS SuperPower
(SuperPowerID INT NOT NULL auto_increment,
SuperPower VARCHAR (30) NOT NULL,
PRIMARY KEY (SuperPowerID)
);

USE SuperHeroSightings;
INSERT INTO SuperPower
(SuperPowerID, SuperPower)
VALUES 
('1','fly'),
('2','snorts fire'),
('3','telekinetic'),
('4','psychic')
;
-- 
-- Coordinate *******************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS Coordinate
(CoordinateID INT NOT NULL auto_increment,
Latitude DOUBLE NOT NULL,
Longitude DOUBLE NOT NULL,
PRIMARY KEY (CoordinateID)
);

USE SuperHeroSightings;
INSERT INTO Coordinate
(CoordinateID, Latitude,Longitude)
VALUES 
('1','345','234'),
('2','234','351'),
('3','12234','21343'),
('4','3453','1234'),
('5','333','24124')
;

-- Location *******************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS Location
(LocationID INT NOT NULL auto_increment,
LocName VARCHAR (30) NOT NULL,
Description VARCHAR (100) NOT NULL,
Street VARCHAR (30) NOT NULL,
City VARCHAR (30) NOT NULL,
State VARCHAR (30) NOT NULL,
ZipCode VARCHAR (30) NOT NULL,
Country VARCHAR (30) NOT NULL,
CoordinateID INT NULL,
PRIMARY KEY (LocationID)
);

USE SuperHeroSightings;
INSERT INTO Location
(LocationID, LocName,Description,Street,City,State,ZipCode,Country,CoordinateID)
VALUES 
('1','The Motherlode','A massive compound for planning couragous feats', '3453 Dragons Drive', 'Slade','Ky','40021','USA','1'),
('2','The Madness Cave','A dangerous cave full of shady characters', '5432 Bat Cave Ave', 'Beatyville','Ky','40032','USA','2'),
('3','The Software Guild','A dangerous warehouse full of shady characters', '2000 E Floyd St', 'Louisville','Ky','40202','USA','4'),
('4','Quills','A coffee shop', '500 Baxter Ave', 'Louisville','Ky','40023','USA','5'),
('5','Climb Nulu','A climbing gym', '1000 Wentzel Drive', 'Louisville','Ky','40204','USA','3')
;

-- Organization ******************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS Organization
(OrganizationID INT NOT NULL auto_increment ,
OrgName VARCHAR (30) NOT NULL,
Description VARCHAR (100) NOT NULL,
PhoneNumber VARCHAR (30) NOT NULL,
Email VARCHAR (30) NOT NULL,
LocationID INT NULL,
PRIMARY KEY (OrganizationID)
);
-- 
USE SuperHeroSightings;
INSERT INTO Organization
(OrganizationID, OrgName,Description,PhoneNumber ,Email,LocationID)
VALUES 
('1','Super Heros Anonymous','A support group for the loney super hero', '502-222-2345', 'sa@gmail.com','1'),
('2','Ginga Ningas','A group for red headed super heros', '502-242-2446', 'gn@gmail.com','2'),
('3','The Super Hero Guild','A bootcamp for promising super heros', '502-776-2345', 'shg@gmail.com','3')
;

-- 
-- -- Sighting ******************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS Sighting
(SightingID INT NOT NULL auto_increment,
LocationID INT NULL,
SightingDate DATE NOT NULL,
PRIMARY KEY (SightingID)
);
-- 
USE SuperHeroSightings;
INSERT INTO Sighting
(SightingID,  LocationID,SightingDate)
VALUES 
('1','3','2017/06/30'),
('2','1','2017/07/04'),
('3','5','2017/05/26'),
('4','3','2017/06/07')
;
-- 
-- -- SuperHeroSuperPowers (BRIDGE) ************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS SuperHeroSuperPower
(SuperHeroID INT NOT NULL,
SuperPowerID INT NOT NULL,
PRIMARY KEY (SuperHeroID,SuperPowerID)
);
-- 
USE SuperHeroSightings;
INSERT INTO SuperHeroSuperPower
(SuperHeroID,SuperPowerID)
VALUES 
('1','1'),
('1','2'),
('2','2'),
('3','3'),
('4','4')
;


-- -- SuperHeroSighting (BRIDGE) ************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS SuperHeroSighting
(SightingID INT NOT NULL,
SuperHeroID INT NOT NULL,
PRIMARY KEY (SightingID,SuperHeroID)
);
-- 
USE SuperHeroSightings;
INSERT INTO SuperHeroSighting
(SightingID,SuperHeroID)
VALUES 
('1','2'),
('1','1'),
('2','2'),
('3','1'),
('4','3')
;
-- 
-- -- Memberships (BRIDGE) ********************
USE SuperHeroSightings;
CREATE TABLE IF NOT EXISTS Memberships
(OrganizationID INT NOT NULL,
SuperHeroID INT NOT NULL,
PRIMARY KEY (OrganizationID,SuperHeroID)
);
-- 
USE SuperHeroSightings;
INSERT INTO Memberships
(OrganizationID,SuperHeroID)
VALUES 
('1','1'),
('2','1'),
('3','2'),
('1','2'),
('1','3'),
('3','4'),
('2','4')
;
-- CONSTRAINTS----------------------------------------------------------------------------


-- Location ***************
USE SuperHeroSightings;
ALTER TABLE Location
ADD CONSTRAINT 
fk_Location_Coordinate
FOREIGN KEY
(CoordinateID)
REFERENCES
Coordinate(CoordinateID);

-- Organization **************
USE SuperHeroSightings;
ALTER TABLE Organization
ADD CONSTRAINT 
fk_Organization_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);

-- -- Sighting ****************
USE SuperHeroSightings;
ALTER TABLE Sighting
ADD CONSTRAINT 
fk_Sighting_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);

-- -- SuperHeroSuperPower (BRIDGE) *************
USE SuperHeroSightings;
ALTER TABLE SuperHeroSuperPower
ADD CONSTRAINT 
fk_SuperHeroSuperPower_SuperHero
FOREIGN KEY
(SuperHeroID)
REFERENCES
SuperHero(SuperHeroID);
-- 
USE SuperHeroSightings;
ALTER TABLE SuperHeroSuperPower
ADD CONSTRAINT 
fk_SuperHeroSuperPower_SuperPower
FOREIGN KEY
(SuperPowerID)
REFERENCES
SuperPower(SuperPowerID);


-- 
-- -- SuperHeroSighting (BRIDGE) *******
USE SuperHeroSightings;
ALTER TABLE SuperHeroSighting
ADD CONSTRAINT 
fk_SuperHeroSightings_Sighting
FOREIGN KEY
(SightingID)
REFERENCES
Sighting(SightingID);

USE SuperHeroSightings;
ALTER TABLE SuperHeroSighting
ADD CONSTRAINT 
fk_SuperHeroSightings_SuperHero
FOREIGN KEY
(SuperHeroID)
REFERENCES
SuperHero(SuperHeroID);


-- -- Memberships (BRIDGE) *******
USE SuperHeroSightings;
ALTER TABLE Memberships
ADD CONSTRAINT 
fk_Memberships_SuperHero
FOREIGN KEY
(SuperHeroID)
REFERENCES
SuperHero(SuperHeroID);
-- 
USE SuperHeroSightings;
ALTER TABLE Memberships
ADD CONSTRAINT 
fk_Memberships_Organization
FOREIGN KEY
(OrganizationID)
REFERENCES
Organization(OrganizationID);

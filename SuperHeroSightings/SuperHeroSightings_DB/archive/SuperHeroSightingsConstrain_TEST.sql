-- SuperHero ***************
USE SuperHeroSightings_TEST;
ALTER TABLE SuperHero
ADD CONSTRAINT 
fk_SuperHero_SuperPower
FOREIGN KEY
(SuperPowerID)
REFERENCES
SuperPower(SuperPowerID);

-- Location ***************
USE SuperHeroSightings_TEST;
ALTER TABLE Location
ADD CONSTRAINT 
fk_Location_Coordinate
FOREIGN KEY
(CoordinateID)
REFERENCES
Coordinate(CoordinateID);

-- Organization **************
USE SuperHeroSightings_TEST;
ALTER TABLE Organization
ADD CONSTRAINT 
fk_Organization_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);

-- -- Sighting ****************
USE SuperHeroSightings_TEST;
ALTER TABLE Sighting
ADD CONSTRAINT 
fk_Sighting_Location
FOREIGN KEY
(LocationID)
REFERENCES
Location(LocationID);
-- 
-- -- SuperHeroSightings_TEST ************
USE SuperHeroSightings_TEST;
ALTER TABLE SuperHeroSighting
ADD CONSTRAINT 
fk_SuperHeroSightings_Sighting
FOREIGN KEY
(SightingID)
REFERENCES
Sighting(SightingID);

USE SuperHeroSightings_TEST;
ALTER TABLE SuperHeroSighting
ADD CONSTRAINT 
fk_SuperHeroSightings_SuperHero
FOREIGN KEY
(SuperHeroID)
REFERENCES
SuperHero(SuperHeroID);


-- -- Memberships *************
USE SuperHeroSightings_TEST;
ALTER TABLE Memberships
ADD CONSTRAINT 
fk_Memberships_SuperHero
FOREIGN KEY
(SuperHeroID)
REFERENCES
SuperHero(SuperHeroID);
-- 
USE SuperHeroSightings_TEST;
ALTER TABLE Memberships
ADD CONSTRAINT 
fk_Memberships_Organization
FOREIGN KEY
(OrganizationID)
REFERENCES
Organization(OrganizationID);

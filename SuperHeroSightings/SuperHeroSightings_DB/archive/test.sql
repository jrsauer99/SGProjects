USE SuperHeroSightings_TEST;
SELECT*FROM Coordinate;

USE SuperHeroSightings_TEST;
SELECT*FROM Location;

select * from Location where LocationID = 16;

SELECT * FROM Sighting;


select loc.LocationID, loc.LocName, loc.Description, loc.Street, loc.City, loc.State, loc.ZipCode, loc.Country, loc.CoordinateID
             from Location loc
             inner join Sighting si ON loc.LocationID = si.LocationID
             where si.SightingID = '1';
             
             
insert into Memberships (OrganizationID,SuperHeroID)
             values ('1','4');            

delete from Memberships where OrganizationID = '1';

select SuperHeroID from Memberships where OrganizationID = '3';

USE SuperHeroSightings_TEST;
select * from Memberships;

delete from Memberships;

select sh.SuperHeroID, sh.SuperHeroName, sh.Description, sh.SuperPowerID
	from Memberships mem
    inner join SuperHero sh ON sh.SuperHeroID = mem.SuperHeroID
    where OrganizationID = '3';

select * from SuperHeroSighting;

insert into SuperHeroSighting (SightingID, SuperHeroID)
           values ('1','3');
           
select sh.SuperHeroID, sh.SuperHeroName, sh.Description, sh.SuperPowerID
             from SuperHeroSighting shs
              inner join SuperHero sh ON sh.SuperHeroID = shs.SuperHeroID
              where SightingID = 2;
              
delete from SuperHeroSighting where SightingID = 1;

delete from SuperHeroSighting;
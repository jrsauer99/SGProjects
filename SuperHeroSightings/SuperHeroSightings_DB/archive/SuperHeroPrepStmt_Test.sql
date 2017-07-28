-- TEST COORDINATE *********************************************
USE SuperHeroSightings;
Select * From Coordinate;

-- test insert
USE SuperHeroSightings;
insert into Coordinate (Latitude, Longitude)
             values (435, 680);
  
-- test delete  
USE SuperHeroSightings;
delete from Coordinate where CoordinateID = 6;    

-- test update
USE SuperHeroSightings;
    update Coordinate set Latitude = 200, Longitude = 200 
             where CoordinateID  =  5; 
             
-- test select by id
USE SuperHeroSightings;
select * from Coordinate where CoordinateID = 5;

-- test select all
USE SuperHeroSightings;
select * from Coordinate;

-- test select coord by location id
USE SuperHeroSightings;
 select co.CoordinateID, co.Latitude, co.Longitude 
 from Coordinate co 
    inner join Location loc on co.CoordinateID = loc.CoordinateID where 
    loc.LocationID = 4;

-- TEST Location ***************************************************
USE SuperHeroSightings;
Select * From Location;

-- test insert
USE SuperHeroSightings;
insert into Location (LocName, Description, Street, City, State, ZipCode, Country, CoordinateID)
             values ('Hammerheads', 'restaraunt', '1010 E Swan St', 'Louisville', 'KY', '40204', 'USA','1');
  
-- test delete  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
USE SuperHeroSightings;   
delete from Location where LocationID = 5;

-- test update
USE SuperHeroSightings;
update Location set LocName='My House', Description='place in gtown', Street='1010 E Oak', City='Louisville', State='Ky', ZipCode='40204', Country='USA', CoordinateID=1 
             where LocationID  =  1;
             
-- test select by id
USE SuperHeroSightings;
select * from Location where LocationID = 1;

-- test select all
USE SuperHeroSightings;
select * from Location;

-- test select locations by superhero id
USE SuperHeroSightings;
select loc.LocationID, loc.LocName, loc.Description, loc.Street, loc.City, loc.State, loc.ZipCode, loc.Country, loc.CoordinateID
             from Location loc
             inner join Sighting si on loc.LocationID = si.LocationID
             inner join SuperHeroSighting shs on si.SightingID = shs.SightingID
             inner join SuperHero sh on shs.SuperHeroID = sh.SuperHeroID
            where sh.SuperHeroID  =  3;
                       

USE SuperHeroSightings;
update Location loc set loc.CoordinateID = NULL 
	where loc.CoordinateID = 1;


-- TEST Organization ***************************************************
USE SuperHeroSightings;
Select * From Organization;

-- test insert
USE SuperHeroSightings;
insert into Organization (OrgName, Description, PhoneNumber, Email, LocationID)
             values ('SuperHero Alliance','the best one' , '256-679-9079', 'sauer@gmail.com', '1');
  
-- test delete  
USE SuperHeroSightings;  
 delete from Organization where OrganizationID = 4;


-- test update
USE SuperHeroSightings;
update Organization set OrgName='my org', Description='this is mine', PhoneNumber='502-228-0778', Email='sd@gmail.com', LocationID='1' 
             where OrganizationID  =  3;
             
-- test select by id
USE SuperHeroSightings;
select * from Organization where OrganizationID = 3;

-- test select all
USE SuperHeroSightings;
select * from Organization;

-- test get all orgs by Superhero ID
USE SuperHeroSightings;
select org.OrganizationID, org.OrgName, org.Description, org.PhoneNumber, org.Email, org.LocationID
             from Organization org
             inner join Memberships memb on org.OrganizationID = memb.OrganizationID
             inner join SuperHero sh on memb.SuperHeroID = sh.SuperHeroID
            where sh.SuperHeroID =  2;

-- TEST SIGHTING ***************************************************
USE SuperHeroSightings;
Select * From Sighting;

-- test insert
USE SuperHeroSightings;
insert into Sighting (LocationID, SightingDate)
            values ('1', '2017/07/10');
  
-- test delete !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
USE SuperHeroSightings;   
delete from Sighting where SightingID = '1';

-- test update
USE SuperHeroSightings;
update Sighting set LocationID='3', SightingDate='2017/06/05'
             where SightingID  =  '1';
             
-- test select by id
USE SuperHeroSightings;
select * from Sighting where SightingID = '1';

-- test select all
USE SuperHeroSightings;
select * from Sighting;

-- test select sightings by date
USE SuperHeroSightings;
select * from Sighting si
	where si.SightingDate  =  '2017/06/30';

-- TEST SUPERHERO ***************************************************
USE SuperHeroSightings;
Select * From SuperHero;

-- test insert
USE SuperHeroSightings;
insert into SuperHero (SuperHeroName, Description, SuperPowerID)
             values ('Bing Bong', 'wild man', '2');
  
-- test delete !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
USE SuperHeroSightings;   
delete from SuperHero where SuperHeroID = ?;

-- test update
USE SuperHeroSightings;
update SuperHero set SuperHeroName='Jen', Description='super villan', SuperPowerID='1'
             where SuperHeroID  =  '1';
             
-- test select by id
USE SuperHeroSightings;
select * from SuperHero where SuperHeroID = '1';

-- test select all
USE SuperHeroSightings;
select * from SuperHero;

-- test select superheros by loc id
USE SuperHeroSightings;
select sh.SuperHeroID, sh.SuperHeroName, sh.Description, sh.SuperPowerID, si.SightingDate
             from Location loc
             inner join Sighting si on loc.LocationID = si.LocationID
             inner join SuperHeroSighting shs on si.SightingID = shs.SightingID
             inner join SuperHero sh on shs.SuperHeroID = sh.SuperHeroID
            where si.LocationID  =  3;

-- test select superheros by org id
USE SuperHeroSightings;
select sh.SuperHeroID, sh.SuperHeroName, sh.Description, sh.SuperPowerID
             from SuperHero sh
             inner join Memberships memb on sh.SuperHeroID = memb.SuperHeroID
             inner join Organization org on memb.OrganizationID = org.OrganizationID
            where org.OrganizationID  =  2;


-- TEST SuperPower ***************************************************
USE SuperHeroSightings;
Select * From SuperPower;

-- test insert
USE SuperHeroSightings;
insert into SuperPower (SuperPower)
             values ('evil');
  
-- test delete  
USE SuperHeroSightings;   
delete from SuperPower where SuperPowerID = 1;

-- test update
USE SuperHeroSightings;
update SuperPower set SuperPower='sneaky'
             where SuperPowerID  =  '1';

             
-- test select by id
USE SuperHeroSightings;
select * from SuperPower where SuperPowerID = 1;

-- test select all
USE SuperHeroSightings;
select * from SuperPow
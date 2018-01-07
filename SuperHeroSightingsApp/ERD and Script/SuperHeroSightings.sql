DROP DATABASE IF EXISTS SuperHeroSightings;

CREATE DATABASE SuperHeroSightings;

USE SuperHeroSightings;

CREATE TABLE SuperPerson
(SuperPersonID int NOT NULL auto_increment,
SuperName varchar(70) NOT NULL,
Description varchar(400) NOT NULL,
PRIMARY KEY(SuperPersonID));

CREATE TABLE Location
(LocationID int NOT NULL auto_increment,
LocationName varchar(40) NOT NULL,
LocationDescription varchar(200) NOT NULL,
Latitude Decimal(9,6),
Longitude Decimal(9,6),
PRIMARY KEY(LocationID));

CREATE TABLE Organization
(OrganizationID int NOT NULL auto_increment,
OrganizationName varchar(70) NOT NULL,
LocationID int NOT NULL,
Description varchar(400) NOT NULL,
OrganizationPhone varchar(15) NOT NULL,
OrganizationEmail varchar(320) NOT NULL,
PRIMARY KEY(OrganizationID));

ALTER TABLE Organization
ADD CONSTRAINT fk_Organization_Location
FOREIGN KEY (LocationID) REFERENCES Location(LocationID);


CREATE TABLE SuperPersonOrganization
(SuperPersonID INT NOT NULL,
OrganizationID INT NOT NULL,        
PRIMARY KEY (SuperPersonID, OrganizationID));

ALTER TABLE SuperPersonOrganization
ADD CONSTRAINT fk_SuperPersonOrganization_SuperPerson 
FOREIGN KEY (SuperPersonID) REFERENCES SuperPerson(SuperPersonID);

ALTER TABLE SuperPersonOrganization
ADD CONSTRAINT fk_SuperPersonOrganization_Organization
FOREIGN KEY (OrganizationID) REFERENCES Organization(OrganizationID);


CREATE TABLE Power
(PowerID int NOT NULL auto_increment,
PowerName varchar(40),
PowerDescription varchar(400),
PRIMARY KEY(PowerID));


CREATE TABLE SuperPersonPower
(SuperPersonID INT NOT NULL,
PowerID INT NOT NULL,        
PRIMARY KEY (SuperPersonID, PowerID));

ALTER TABLE SuperPersonPower
ADD CONSTRAINT fk_SuperPersonPower_SuperPerson
FOREIGN KEY (SuperPersonID) REFERENCES SuperPerson(SuperPersonID);

ALTER TABLE SuperPersonPower
ADD CONSTRAINT fk_SuperPersonPower_Power
FOREIGN KEY (PowerID) REFERENCES Power(PowerID);



CREATE TABLE Sighting
(SightingID INT NOT NULL auto_increment,
LocationID INT NOT NULL, 
SightingDate date NOT NULL,       
PRIMARY KEY (SightingID));

ALTER TABLE Sighting
ADD CONSTRAINT fk_Sighting_Location
FOREIGN KEY (LocationID) REFERENCES Location(LocationID);


CREATE TABLE SuperPersonSighting
(SuperPersonID INT NOT NULL,
SightingID INT NOT NULL,        
PRIMARY KEY (SuperPersonID, SightingID));

ALTER TABLE SuperPersonSighting
ADD CONSTRAINT fk_SuperPersonSighting_SuperPerson
FOREIGN KEY (SuperPersonID) REFERENCES SuperPerson(SuperPersonID);

ALTER TABLE SuperPersonSighting
ADD CONSTRAINT fk_SuperPersonSighting_Sighting
FOREIGN KEY (SightingID) REFERENCES Sighting(SightingID);

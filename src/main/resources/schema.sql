CREATE TABLE IF NOT EXISTS Organization (
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL,
    fullname VARCHAR(200) NOT NULL,
    inn      VARCHAR(10) NOT NULL,
    kpp      VARCHAR(9) NOT NULL,
    address  VARCHAR(100) NOT NULL,
    phone    VARCHAR(20) NOT NULL,
    isActive BOOLEAN NOT NULL,
    version  INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Office (
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL,
    orgId    INTEGER NOT NULL,
    address  VARCHAR(100) NOT NULL,
    phone    VARCHAR(20) NOT NULL,
    isActive BOOLEAN NOT NULL,
    version  INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS User (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    firstname       VARCHAR(50) NOT NULL,
    lastname        VARCHAR(50) NOT NULL,
    middlename      VARCHAR(50),
    officeId        INTEGER NOT NULL,
    position        VARCHAR(50) NOT NULL,
    phone           VARCHAR(20) NOT NULL,
    docCode         INTEGER NOT NULL,
    docNumber       VARCHAR(20) NOT NULL,
    docDate         DATE NOT NULL,
    citizenshipCode INTEGER NOT NULL,
    isIdentified    BOOLEAN NOT NULL,
    version         INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS Doc (
    code INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Country (
    code INTEGER PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE INDEX IX_Office_Organization_Id ON Office (orgId);
ALTER TABLE Office ADD FOREIGN KEY (orgId) REFERENCES Organization (id);

CREATE INDEX IX_User_Office_Id ON User (officeId);
ALTER TABLE User ADD FOREIGN KEY (officeId) REFERENCES Office (id);

CREATE INDEX IX_User_Doc_Code ON User (docCode);
ALTER TABLE User ADD FOREIGN KEY (docCode) REFERENCES Doc (code);

CREATE INDEX IX_User_Country_Code ON User (citizenshipCode);
ALTER TABLE User ADD FOREIGN KEY (citizenshipCode) REFERENCES Country (code);


/* Schema from old project below */

CREATE TABLE IF NOT EXISTS Person (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    age        INTEGER  NOT NULL
);

CREATE TABLE IF NOT EXISTS House (
    id         INTEGER  PRIMARY KEY AUTO_INCREMENT,
    version    INTEGER NOT NULL,
    address    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Person_House (
    person_id   INTEGER  NOT NULL,
    house_id    INTEGER  NOT NULL,

    PRIMARY KEY (person_id, house_id)
);

CREATE INDEX IX_Person_House_Id ON Person_House (house_id);
ALTER TABLE Person_House ADD FOREIGN KEY (house_id) REFERENCES House(id);

CREATE INDEX IX_House_Person_Id ON Person_House (person_id);
ALTER TABLE Person_House ADD FOREIGN KEY (person_id) REFERENCES Person(id);
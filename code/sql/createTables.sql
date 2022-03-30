CREATE TABLE ORGANIZATION (
    id int identity,
    name varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE CLASSROOM (
    id int identity,
    name varchar(50),
    maxGroups int,
    maxMembersPerGroup int,
    linkRepo varchar(50),
    schoolYear YEAR,
    orgId int,
    PRIMARY KEY(id),
    FOREIGN KEY(orgId) REFERENCES ORGANIZATION(id)
);

CREATE TABLE TEACHER (
    number int unique,
    name varchar(50),
    PRIMARY KEY(number)
);

CREATE TABLE GROUP (
    id int identity,
    cId int,
    nMembers int,
    PRIMARY KEY(id),
    FOREIGN KEY(cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE NOTE (
    gId int,
    date timestamp,
    description varchar(200),
    PRIMARY KEY(gId,date),
    FOREIGN KEY(gId) REFERENCES GROUP(id),
);

CREATE TABLE STUDENT (
    number int unique,
    name varchar(50),
    cId int,
    gId int,
    PRIMARY KEY(number),
    FOREIGN KEY(cId) REFERENCES CLASSROOM(id),
    FOREIGN KEY(gId) REFERENCES GROUP(id)
);

CREATE TABLE ASSIGNMENT (
    id int identity,
    releaseDate timestamp,
    dueDate timestamp,
    cId int,
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE REPO (
    url varchar(50) unique,
    name varchar(50),
    gId int,
    assId int,
    PRIMARY KEY (url),
    FOREIGN KEY (gId) REFERENCES GROUP(id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENT(id)
);

CREATE TABLE DELIVERY (
    id int identity,
    assId int,
    date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENT(id)
);

CREATE TABLE TAG (
    name varchar(50),
    date timestamp,
    PRIMARY KEY (name)
);

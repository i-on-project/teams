CREATE TABLE ORGANIZATION (
    id serial,
    name varchar(50),
    PRIMARY KEY (id)
);

CREATE TABLE CLASSROOM (
    id serial,
    name varchar(50),
    maxGroups int,
    maxMembersPerGroup int,
    linkRepo varchar(50),
    schoolYear timestamp,
    orgId int,
    PRIMARY KEY(id),
    FOREIGN KEY(orgId) REFERENCES ORGANIZATION(id)
);

CREATE TABLE TEACHER (
    number int unique,
    name varchar(50),
    PRIMARY KEY(number)
);

CREATE TABLE TEAM (
    id serial,
    cId int,
    PRIMARY KEY(id),
    FOREIGN KEY(cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE NOTE (
    tId int,
    date timestamp,
    description varchar(200),
    PRIMARY KEY(tId,date),
    FOREIGN KEY(tId) REFERENCES TEAM(id)
);

CREATE TABLE STUDENT (
    number int,
    name varchar(50),
    cId int,
    tId int,
    PRIMARY KEY(number),
    FOREIGN KEY(cId) REFERENCES CLASSROOM(id),
    FOREIGN KEY(tId) REFERENCES TEAM(id)
);

CREATE TABLE ASSIGNMENT (
    id serial,
    releaseDate timestamp,
    dueDate timestamp,
    cId int,
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE REPO (
    url varchar(50),
    name varchar(50),
    tId int,
    assId int,
    PRIMARY KEY (url),
    FOREIGN KEY (tId) REFERENCES TEAM(id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENT(id)
);

CREATE TABLE DELIVERY (
    id serial,
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

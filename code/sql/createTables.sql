CREATE TABLE ORGANIZATIONS (
    id serial,
    name varchar(50),
    description varchar(200),
    PRIMARY KEY (id)
);

CREATE TABLE CLASSROOMS (
    id serial,
    name varchar(50),
    maxGroups int,
    maxMembersPerGroup int,
    linkRepo varchar(50),
    schoolYear timestamp,
    orgId int,
    --state ,
    PRIMARY KEY(id),
    FOREIGN KEY(orgId) REFERENCES ORGANIZATION(id)
);

CREATE TABLE TEACHERS (
    number int unique,
    name varchar(50),
    PRIMARY KEY(number)
);

CREATE TABLE TEAMS (
    id serial,
    cId int,            --class id
    PRIMARY KEY(id),
    FOREIGN KEY(cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE NOTES (
    tId int,
    date timestamp,
    description varchar(200),
    PRIMARY KEY(tId,date),
    FOREIGN KEY(tId) REFERENCES TEAM(id)
);

CREATE TABLE STUDENTS (
    sId int,
    cId int,
    PRIMARY KEY (sId,cId),
    FOREIGN KEY (sId) REFERENCES STUDENT(number)
);

CREATE TABLE STUDENT (
    number int,
    name varchar(50),
    tId int,
    PRIMARY KEY(number),
    FOREIGN KEY(tId) REFERENCES TEAM(id)
);

CREATE TABLE ASSIGNMENTS (
    id serial,
    releaseDate timestamp,
    dueDate timestamp,
    cId int,
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOM(id)
);

CREATE TABLE REPOS (
    url varchar(50),
    name varchar(50),
    tId int,
    assId int,
    PRIMARY KEY (url),
    FOREIGN KEY (tId) REFERENCES TEAM(id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENT(id)
);

CREATE TABLE DELIVERIES (
    id serial,
    assId int,
    date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENT(id)
);

CREATE TABLE TAGS (
    name varchar(50),
    date timestamp,
    PRIMARY KEY (name)
);

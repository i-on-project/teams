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
    state varchar(50) DEFAULT 'active',
    PRIMARY KEY(id),
    FOREIGN KEY(orgId) REFERENCES ORGANIZATIONS(id),
    CONSTRAINT state_check CHECK ( state = 'active' AND state ='inactive' )
);

CREATE TABLE TEACHERS (
    number int unique,
    name varchar(50),
    PRIMARY KEY(number)
);

CREATE TABLE TEAMS (
    id serial,
    cId int,            --class id
    state varchar(50) DEFAULT 'active',
    PRIMARY KEY(id),
    FOREIGN KEY(cId) REFERENCES CLASSROOMS(id),
    CONSTRAINT state_check CHECK ( state = 'active' AND state ='inactive' )
);

CREATE TABLE NOTES (
    tId int,
    date timestamp,
    description varchar(200),
    PRIMARY KEY(tId,date),
    FOREIGN KEY(tId) REFERENCES TEAMS(id)
);

CREATE TABLE STUDENT (
    number int,
    name varchar(50),
    tId int,
    PRIMARY KEY(number),
    FOREIGN KEY(tId) REFERENCES TEAMS(id)
);


CREATE TABLE STUDENTS (
    sId int,
    cId int,
    PRIMARY KEY (sId,cId),
    FOREIGN KEY (sId) REFERENCES STUDENT(number)
);


CREATE TABLE ASSIGNMENTS (
    id serial,
    releaseDate timestamp,
    dueDate timestamp,
    cId int,
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS(id)
);

CREATE TABLE REPOS (
    url varchar(50),
    name varchar(50),
    tId int,
    assId int,
    PRIMARY KEY (url),
    FOREIGN KEY (tId) REFERENCES TEAMS(id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS(id)
);

CREATE TABLE DELIVERIES (
    id serial,
    assId int,
    date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS(id)
);

CREATE TABLE TAGS (
    name varchar(50),
    date timestamp,
    PRIMARY KEY (name)
);

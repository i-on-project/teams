CREATE TABLE ORGANIZATIONS
(
    id          serial,
    name        varchar(50),
    description varchar(200),
    PRIMARY KEY (id)
);

CREATE TABLE CLASSROOMS
(
    id                 serial,
    name               varchar(50),
    description        varchar(200),
    maxGroups          int,
    maxMembersPerGroup int,
    linkRepo           varchar(50),
    schoolYear         varchar, -- (e.g. 2021/22)
    orgId              int,     --organization id
    state              varchar(50) DEFAULT 'active',
    PRIMARY KEY (id),
    FOREIGN KEY (orgId) REFERENCES ORGANIZATIONS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' ),
    CONSTRAINT schoolYear_check CHECK ( schoolYear ~* '[0-9][0-9][0-9][0-9]/[0-9][0-9]')
);

CREATE TABLE INVITE_LINKS
(
    link varchar,
    cId  int, --classroom id
    PRIMARY KEY (link),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE TEACHER
(
    number int unique,
    name   varchar(50),
    email  varchar,
    office varchar(20), --X.X.XX (e.g G.1.16)
    PRIMARY KEY (number),
    CONSTRAINT email_check CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$') --TODO: not sure what it is (CHECK)
);

CREATE TABLE TEAMS
(
    id    serial,
    name varchar(50),
    cId   int,                          --class id
    state varchar(50) DEFAULT 'pending',
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' OR state = 'pending')
);

CREATE TABLE NOTES
(
    id serial,
    tId         int, --team id
    date        timestamp,
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id)
);

CREATE TABLE STUDENT
(
    number int,
    name   varchar(50),
    tId    int, --team id
    cId    int, --class id
    PRIMARY KEY (number),
    FOREIGN KEY (tId) REFERENCES TEAMS (id)
);

CREATE TABLE ASSIGNMENTS
(
    id          serial,
    releaseDate timestamp,
    cId         int, --class id
    description varchar(200),
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE REPOS
(
    id    serial,
    url   varchar(50) unique,
    name  varchar(50),
    tId   int, --team id
    assId int, --assignment id
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id)
);

CREATE TABLE DELIVERIES
(
    id    serial,
    assId int,       --assignment id
    date  timestamp, --due date
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id)
);

CREATE TABLE TAGS
(
    id serial,
    name   varchar(50),
    date   timestamp,
    delId  int, --delivery id
    teamId int, --team id
    PRIMARY KEY (id),
    FOREIGN KEY (delId) REFERENCES DELIVERIES (id)
);



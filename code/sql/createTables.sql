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
    name               varchar(50)  NOT NULL,
    description        varchar(200) NOT NULL,
    maxGroups          int          NOT NULL,
    maxMembersPerGroup int          NOT NULL,
    linkRepo           varchar(50)  NOT NULL,
    schoolYear         varchar      NOT NULL, -- (e.g. 2021/22)
    orgId              int          NOT NULL, --organization id
    state              varchar(50) DEFAULT 'active',
    PRIMARY KEY (id),
    FOREIGN KEY (orgId) REFERENCES ORGANIZATIONS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' ),
    CONSTRAINT schoolYear_check CHECK ( schoolYear ~* '[0-9][0-9][0-9][0-9]/[0-9][0-9]')
);

CREATE TABLE INVITE_LINKS
(
    link varchar NOT NULL,
    cId  int     NOT NULL, --classroom id
    PRIMARY KEY (link),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE TEACHER
(
    number int unique  NOT NULL,
    name   varchar(50) NOT NULL,
    email  varchar     NOT NULL,
    office varchar(20) NOT NULL,                                                             --X.X.XX (e.g G.1.16)
    cId    int,
    UNIQUE (number, cId),
    PRIMARY KEY (number),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id),
    CONSTRAINT email_check CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$') --TODO: not sure what it is (CHECK)
);

CREATE TABLE TEACHERS
(
    number int unique NOT NULL,
    cId    int        NOT NULL,
    PRIMARY KEY (number),
    FOREIGN KEY (number, cId) REFERENCES TEACHER (number, cId)
);

CREATE TABLE TEAMS
(
    id    serial,
    name  varchar(50) NOT NULL,
    cId   int         NOT NULL, --class id
    state varchar(50) DEFAULT 'pending',
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' OR state = 'pending')
);

CREATE TABLE NOTES
(
    id          serial,
    tId         int          NOT NULL, --team id
    date        timestamp DEFAULT current_timestamp,
    description varchar(200) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id)
);

CREATE TABLE STUDENT
(
    number int         NOT NULL,
    name   varchar(50) NOT NULL,
    tId    int         NOT NULL, --team id
    cId    int         NOT NULL, --class id
    PRIMARY KEY (number),
    UNIQUE (number,tId,cId),
    FOREIGN KEY (tId) REFERENCES TEAMS (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE STUDENTS
(
    number int NOT NULL,
    tId    int NOT NULL,
    cId    int NOT NULL,
    PRIMARY KEY (number),
    FOREIGN KEY (number,tId,cId) REFERENCES STUDENT (number,tId,cId)
);

CREATE TABLE ASSIGNMENTS
(
    id          serial,
    releaseDate timestamp    NOT NULL,
    cId         int          NOT NULL, --class id
    description varchar(200) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE REPOS
(
    id    serial,
    url   varchar(50) unique NOT NULL,
    name  varchar(50)        NOT NULL,
    tId   int                NOT NULL, --team id
    assId int                NOT NULL, --assignment id
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id)
);

CREATE TABLE DELIVERIES
(
    id    serial,
    assId int       NOT NULL, --assignment id
    date  timestamp NOT NULL, --due date
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id)
);

CREATE TABLE TAGS
(
    id     serial,
    name   varchar(50) NOT NULL,
    date   timestamp DEFAULT current_timestamp,
    delId  int         NOT NULL, --delivery id
    repoId int         NOT NULL, --team id
    PRIMARY KEY (id),
    FOREIGN KEY (delId) REFERENCES DELIVERIES (id),
    FOREIGN KEY (repoId) REFERENCES REPOS (id)
);



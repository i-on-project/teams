CREATE TABLE ORGANIZATIONS
(
    id          serial,
    name        varchar(50),
    description varchar(200) NOT NULL,
    githubUri   varchar      NOT NULL,
    avatarUri   varchar      NOT NULL,
    deleted     bit(1) DEFAULT B'0',
    PRIMARY KEY (id),
    UNIQUE (name, githubUri, avatarUri)
);

CREATE TABLE CLASSROOMS
(
    id                serial,
    name              varchar(100) NOT NULL,
    description       varchar      NOT NULL,
    maxTeams          int          NOT NULL,
    maxMembersPerTeam int          NOT NULL,
    repoURI           varchar      NOT NULL UNIQUE,
    schoolYear        varchar      NOT NULL, -- (e.g. 2021/22)
    orgId             int          NOT NULL, --organization id
    state             varchar(50) DEFAULT 'active',
    githubUri         varchar      NOT NULL,
    avatarUri         varchar      NOT NULL,
    deleted           bit(1)      DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (orgId) REFERENCES ORGANIZATIONS (id),
    UNIQUE (repoURI, githubUri, avatarUri),
    UNIQUE (id, orgId),
    UNIQUE (name, orgId),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' ),
    CONSTRAINT schoolYear_check CHECK ( schoolYear ~* '[0-9][0-9][0-9][0-9]/[0-9][0-9]')
);

CREATE TABLE INVITE_LINKS
(
    code    varchar NOT NULL,
    cId     int     NOT NULL, --classroom id
    deleted bit(1) DEFAULT B'0',
    UNIQUE (code, cId),
    PRIMARY KEY (code),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE TEACHER
(
    number         int unique  NOT NULL,
    name           varchar(50) NOT NULL,
    githubUsername varchar(50),
    email          varchar     NOT NULL,
    office         varchar(20) NOT NULL, --X.X.XX (e.g G.1.16)
    verified       bit(1) DEFAULT B'0',
    deleted        bit(1) DEFAULT B'0',
    PRIMARY KEY (number),
    CONSTRAINT email_check CHECK (email ~* '^[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$')
);

CREATE TABLE TEACHERS
(
    number  int NOT NULL,
    cId     int,
    orgId   int,
    deleted bit(1) DEFAULT B'0',
    PRIMARY KEY (number, cId, orgId),
    FOREIGN KEY (number) REFERENCES TEACHER (number),
    FOREIGN KEY (cId, orgId) REFERENCES CLASSROOMS (id, orgId)
);

CREATE TABLE TEAMS
(
    id      serial,
    name    varchar(50) NOT NULL,
    cId     int         NOT NULL, --class id
    state   varchar(50) DEFAULT 'pending',
    deleted bit(1)      DEFAULT B'0',
    UNIQUE (name, cId),
    UNIQUE (id, cId),             --used in foreign keys
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' OR state = 'pending')
);

CREATE TABLE STUDENT
(
    number         int         NOT NULL,
    name           varchar(50) NOT NULL,
    githubUsername varchar(50),
    verified       bit(1) DEFAULT B'0',
    deleted        bit(1) DEFAULT B'0',
    UNIQUE (number, name),
    PRIMARY KEY (number)
);

CREATE TABLE STUDENTS
(
    number  int NOT NULL,
    tId     int NOT NULL, --team id
    cId     int NOT NULL, --class id
    deleted bit(1) DEFAULT B'0',
    PRIMARY KEY (number, tId, cId),
    FOREIGN KEY (number) REFERENCES student (number),
    FOREIGN KEY (tId, cId) REFERENCES TEAMS (id, cId)
);

CREATE TABLE NOTES
(
    id          serial,
    tId         int     NOT NULL, --team id
    date        timestamp DEFAULT current_timestamp,
    description varchar NOT NULL,
    deleted     bit(1)    DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id)
);

CREATE TABLE ASSIGNMENTS
(
    id          serial,
    releaseDate timestamp DEFAULT current_timestamp,
    cId         int     NOT NULL, --class id
    name        varchar NOT NULL,
    description varchar NOT NULL,
    deleted     bit(1)    DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (cId) REFERENCES CLASSROOMS (id)
);

CREATE TABLE REPOS
(
    id      serial,
    url     varchar(200) unique NOT NULL,
    name    varchar(50)         NOT NULL,
    state   varchar(50) DEFAULT 'active',
    tId     int                 NOT NULL, --team id
    assId   int                 NOT NULL, --assignment id
    deleted bit(1)      DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (tId) REFERENCES TEAMS (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id),
    CONSTRAINT state_check CHECK ( state = 'active' OR state = 'inactive' )
);

CREATE TABLE DELIVERIES
(
    id      serial,
    assId   int         NOT NULL, --assignment id
    name    varchar(20) NOT NULL,
    date    timestamp   NOT NULL, --due date
    deleted bit(1) DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (assId) REFERENCES ASSIGNMENTS (id)
);

CREATE TABLE TAGS
(
    id      serial,
    name    varchar(50) NOT NULL,
    date    timestamp DEFAULT current_timestamp,
    delId   int         NOT NULL, --delivery id
    repoId  int         NOT NULL, --team id
    deleted bit(1)    DEFAULT B'0',
    PRIMARY KEY (id),
    FOREIGN KEY (delId) REFERENCES DELIVERIES (id),
    FOREIGN KEY (repoId) REFERENCES REPOS (id)
);

CREATE TABLE USER_SESSION
(
    number    int NOT NULL,
    sessionId int NOT NULL,
    userType  varchar(1), -- S for student and T for teacher
    PRIMARY KEY (number, sessionId)
);



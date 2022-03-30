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

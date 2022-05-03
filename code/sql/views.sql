CREATE VIEW ORGANIZATIONS_VIEW (id, name, description) AS
SELECT id, name, description
FROM organizations
WHERE deleted = B'0';

CREATE VIEW CLASSROOMS_VIEW (id, name, description, maxTeams, maxMembersPerTeam, linkRepo, schoolYear, orgId, state) AS
SELECT id,
       name,
       description,
       maxTeams,
       maxMembersPerTeam,
       linkRepo,
       schoolYear,
       orgId,
       state
FROM classrooms
WHERE deleted = B'0';

CREATE VIEW INVITE_LINKS_VIEW (link, cId) AS
SELECT link, cId
FROM invite_links
WHERE deleted = B'0';

CREATE VIEW TEACHERS_VIEW (number,name,cId) AS
SELECT ts.number,t.name,ts.cId
FROM teachers ts JOIN teacher t on ts.number = t.number
WHERE ts.deleted = B'0';

CREATE VIEW TEAMS_VIEW (id,name,cId,state) AS
SELECT id,name,cid,state
FROM teams
WHERE deleted = B'0';

CREATE VIEW STUDENTS_VIEW (number,name,tId,cId) AS
SELECT st.number,st.name,sts.tId,sts.cId
FROM students sts JOIN student st ON sts.number = st.number
WHERE sts.deleted = B'0';

CREATE VIEW NOTES_VIEW (id,tId,date,description) AS
SELECT id,tId,date,description
FROM notes
WHERE deleted = B'0';

CREATE VIEW ASSIGNMENTS_VIEW (id,releaseDate,cId,description) AS
SELECT id,releaseDate,cId,description
FROM assignments
WHERE deleted = B'0';

CREATE VIEW REPOS_VIEW (id,url,name,tId,assId) AS
SELECT id,url,name,tId,assId
FROM repos
WHERE deleted = B'0';

CREATE VIEW DELIVERIES_VIEW (id,assId,date) AS
SELECT id,assId,date
FROM deliveries
WHERE deleted = B'0';

CREATE VIEW TAGS_VIEW (id,name,date,delId,repoId) AS
SELECT id,name,date,delId,repoId
FROM tags
WHERE deleted = B'0';

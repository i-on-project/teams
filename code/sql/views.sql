CREATE VIEW ORGANIZATIONS_VIEW (id, name, description) AS
SELECT id, name, description
FROM organizations
WHERE deleted = B'0';

CREATE VIEW CLASSROOMS_VIEW (id, name, description, maxTeams, maxMembersPerTeam, schoolYear, orgId, state)
AS
SELECT id,
       name,
       description,
       maxteams,
       maxmembersperteam,
       schoolyear,
       orgid,
       state
FROM classrooms
WHERE deleted = B'0';

CREATE VIEW INVITE_CODES_VIEW (code, cId) AS
SELECT code, cId
FROM invite_codes
WHERE deleted = B'0';

CREATE VIEW TEACHERS_VIEW (number, name, email, office) AS
SELECT number, name, email, office, deleted
FROM teacher
WHERE deleted = B'0';

CREATE VIEW TEAMS_VIEW (id, name, cId, state) AS
SELECT id, name, cid, state
FROM teams
WHERE deleted = B'0';

CREATE VIEW REQUESTS_VIEW (tid, teamName, cid) AS
SELECT id, name, cId
FROM teams
WHERE state = 'pending'
  AND deleted = B'0';

CREATE VIEW STUDENTS_VIEW (number, name, githubusername, tId, cId) AS
SELECT sts.number, st.name, st.githubusername, sts.tId, sts.cId
FROM student st
         JOIN students sts ON sts.number = st.number
WHERE sts.deleted = B'0';

CREATE VIEW NOTES_VIEW (id, tId, date, description) AS
SELECT id, tId, date, description
FROM notes
WHERE deleted = B'0';

CREATE VIEW ASSIGNMENTS_VIEW (id, releaseDate, name, description, cId) AS
SELECT id, releaseDate, name, description, cId
FROM assignments
WHERE deleted = B'0';

CREATE VIEW REPOS_VIEW (id, url, name, tId, assId) AS
SELECT id, url, name, tId, assId
FROM repos
WHERE deleted = B'0';

CREATE VIEW DELIVERIES_VIEW (id, assId, name, date) AS
SELECT id, assId, name, date
FROM deliveries
WHERE deleted = B'0';

CREATE VIEW DELIVERIES_WITH_TEAM_VIEW (teamName, delId, date, assid) AS
SELECT t.name, d.id, d.date, d.assid
FROM deliveries d
         JOIN tags tg on d.id = tg.delid
         JOIN repos r on r.id = tg.repoid
         JOIN teams t on t.id = r.tid
WHERE d.deleted = B'0'
  AND tg.deleted = B'0';

CREATE VIEW TAGS_WITH_REPO_AND_TEAM_VIEW (id, name, date, repoId, teamId, teamName, delId) AS
SELECT t.id, t.name, t.date, r.id, tm.id, tm.name, d.id
FROM tags t
         JOIN repos r on r.id = t.repoid
         JOIN teams tm on r.tid = tm.id
         JOIN deliveries d on t.delid = d.id
WHERE t.deleted = B'0'
  AND d.deleted = B'0';

CREATE VIEW TAGS_VIEW (id, name, date, delId, repoId) AS
SELECT id, name, date, delId, repoId
FROM tags
WHERE deleted = B'0';

CREATE VIEW TEACHER_CLASSROOMS_VIEW (id, name, description, maxTeams, maxMembersPerTeam,
    schoolYear, orgId, state,  number) AS
SELECT c.id,
       c.name,
       c.description,
       c.maxTeams,
       c.maxMembersPerTeam,
       c.schoolyear,
       c.orgId,
       c.state,
       t.number
FROM classrooms c
         JOIN teachers_classroom t on c.id = t.cid
WHERE c.deleted = B'0'
  AND t.deleted = B'0';

CREATE VIEW TEACHER_ORGANIZATIONS_VIEW (id, name, description, number) AS
SELECT o.id, o.name, o.description, t.number
FROM organizations o
        JOIN teachers_organization t on o.id = t.orgid
WHERE o.deleted = B'0'
  AND t.deleted = B'0';

CREATE VIEW STUDENT_TEAMS_VIEW (orgName, orgId, className, classId, teamName, teamId, number) AS
SELECT o.name, o.id, c.name, c.id, t.name, t.id, s.number
FROM students s
         JOIN teams t on t.id = s.tid
         JOIN classrooms c on c.id = t.cid
         JOIN organizations o on o.id = c.orgid
WHERE o.deleted = B'0' AND c.deleted = B'0'
  AND t.deleted = B'0'
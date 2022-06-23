CREATE VIEW ORGANIZATIONS_VIEW (id, name, description, githubUri, avatarUri) AS
SELECT id, name, description, githuburi, avataruri
FROM organizations
WHERE deleted = B'0';

CREATE VIEW CLASSROOMS_VIEW
            (id, name, description, maxTeams, maxMembersPerTeam, repoURI, schoolYear, orgId, state, githubURI,
             avatarURI)
AS
SELECT id,
       name,
       description,
       maxteams,
       maxmembersperteam,
       repouri,
       schoolyear,
       orgid,
       state,
       githuburi,
       avataruri
FROM classrooms
WHERE deleted = B'0';

CREATE VIEW INVITE_LINKS_VIEW (code, cId) AS
SELECT code, cId
FROM invite_links
WHERE deleted = B'0';

CREATE VIEW TEACHERS_VIEW (number, name, email, office) AS
SELECT ts.number, t.name, t.email, t.office, ts.cId, ts.orgid
FROM teachers ts
         JOIN teacher t on ts.number = t.number
WHERE ts.deleted = B'0';

CREATE VIEW TEAMS_VIEW (id, name, cId, state) AS
SELECT id, name, cid, state
FROM teams
WHERE deleted = B'0';

CREATE VIEW REQUESTS_VIEW (tid, teamName, cid) AS
SELECT id, name, cId
FROM teams
WHERE state = 'pending'
  AND deleted = B'0';

CREATE VIEW STUDENTS_VIEW (number, name, tId, cId) AS
SELECT sts.number, st.name, sts.tId, sts.cId
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

CREATE VIEW TAGS_WITH_REPO_AND_TEAM (id, name, date, repoId, teamId, teamName, delId) AS
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

CREATE VIEW TEACHER_CLASSROOMS (id, name, description, maxTeams, maxMembersPertTeam, repoUri, schoolYear, orgId, state, githubUri, avatarUri, number) AS
SELECT c.id, c.name, c.description, c.maxTeams, c.maxMembersPerTeam, c.repoUri, c.schoolYear, c.orgId, c.state, c.githubUri, c.avatarUri, t.number
FROM classrooms c
        JOIN teachers t on c.id = t.cid
WHERE c.deleted = B'0'
  AND t.deleted = B'0';
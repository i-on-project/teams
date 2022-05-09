CREATE VIEW ORGANIZATIONS_VIEW (id, name, description, githubUri, avatarUri) AS
SELECT id, name, description, githuburi, avataruri
FROM organizations
WHERE deleted = B'0';

CREATE VIEW CLASSROOMS_VIEW
      ( id, name, description, maxTeams, maxMembersPerTeam, repoURI, schoolYear, orgId, state, githubURI, avatarURI)       AS
SELECT id, name, description, maxteams, maxmembersperteam, repouri, schoolyear, orgid, state, githuburi, avataruri
FROM classrooms
WHERE deleted = B'0';

CREATE VIEW INVITE_LINKS_VIEW (link, cId) AS
SELECT code, cId
FROM invite_links
WHERE deleted = B'0';

CREATE VIEW TEACHERS_VIEW (number, name, cId) AS
SELECT ts.number, t.name, ts.cId
FROM teachers ts
         JOIN teacher t on ts.number = t.number
WHERE ts.deleted = B'0';

CREATE VIEW TEAMS_VIEW (id, name, cId, state) AS
SELECT id, name, cid, state
FROM teams
WHERE deleted = B'0';

CREATE VIEW STUDENTS_VIEW (number, name, tId, cId) AS
SELECT st.number, st.name, sts.tId, sts.cId
FROM students sts
         JOIN student st ON sts.number = st.number
WHERE sts.deleted = B'0';

CREATE VIEW NOTES_VIEW (id, tId, date, description) AS
SELECT id, tId, date, description
FROM notes
WHERE deleted = B'0';

CREATE VIEW ASSIGNMENTS_VIEW (id, releaseDate, cId, description) AS
SELECT id, releaseDate, cId, description
FROM assignments
WHERE deleted = B'0';

CREATE VIEW REPOS_VIEW (id, url, name, tId, assId) AS
SELECT id, url, name, tId, assId
FROM repos
WHERE deleted = B'0';

CREATE VIEW DELIVERIES_VIEW (id, assId,name, date) AS
SELECT id, assId,name, date
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

CREATE VIEW TAGS_WITH_REPO_AND_TEAM (id, name, date, repoId, teamId, delId) AS
SELECT t.id, t.name, t.date, r.id, tm.id, d.id
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

--DROP VIEW IF EXISTS teachers_view;

DROP TRIGGER IF EXISTS check_team_members_counter on student;
DROP TRIGGER IF EXISTS check_team_counter on teams;


DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS deliveries;
DROP TABLE IF EXISTS repos;
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS invite_links;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS organizations;

DROP FUNCTION IF EXISTS check_class_teams_fun();
DROP FUNCTION IF EXISTS check_team_members_fun();
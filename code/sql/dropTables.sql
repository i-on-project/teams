DROP VIEW IF EXISTS organizations_view;
DROP VIEW IF EXISTS classrooms_view;
DROP VIEW IF EXISTS invite_codes_view;
DROP VIEW IF EXISTS teachers_view;
DROP VIEW IF EXISTS teacher_classrooms_view;
DROP VIEW IF EXISTS teacher_organizations_view;
DROP VIEW IF EXISTS teacher_view;
DROP VIEW IF EXISTS teams_view;
DROP VIEW IF EXISTS requests_view;
DROP VIEW IF EXISTS students_view;
DROP VIEW IF EXISTS notes_view;
DROP VIEW IF EXISTS assignments_view;
DROP VIEW IF EXISTS repos_view;
DROP VIEW IF EXISTS deliveries_view;
DROP VIEW IF EXISTS tags_view;
DROP VIEW IF EXISTS deliveries_with_team_view;
DROP VIEW IF EXISTS tags_with_repo_and_team_view;
DROP VIEW IF EXISTS student_teams_view;
DROP VIEW IF EXISTS teachers_by_classroom_view;

DROP TRIGGER IF EXISTS check_team_counter on teams;
DROP TRIGGER IF EXISTS check_team_members_counter on student;
DROP TRIGGER IF EXISTS cascade_delete_organizations on organizations;
DROP TRIGGER IF EXISTS cascade_delete_classrooms on classrooms;
DROP TRIGGER IF EXISTS cascade_delete_teams on teams;
DROP TRIGGER IF EXISTS cascade_delete_assignments on assignments;
DROP TRIGGER IF EXISTS cascade_delete_repos on repos;

DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS deliveries;
DROP TABLE IF EXISTS repos;
DROP TABLE IF EXISTS assignments;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS teachers_organization;
DROP TABLE IF EXISTS teachers_classroom;
DROP TABLE IF EXISTS teacher;
DROP TABLE IF EXISTS invite_codes;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS organizations;
DROP TABLE IF EXISTS user_session;
DROP TABLE IF EXISTS to_verify;
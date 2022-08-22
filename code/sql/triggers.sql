--Classroom Counters Triggers
CREATE TRIGGER check_team_members_counter
    AFTER INSERT OR UPDATE
    ON students
    FOR EACH ROW
EXECUTE PROCEDURE check_team_members_fun();

CREATE TRIGGER check_team_counter
    BEFORE INSERT
    ON teams
    FOR EACH ROW
EXECUTE PROCEDURE check_class_teams_fun();

/************************* Cascade Delete Triggers *************************/

CREATE TRIGGER cascade_delete_organizations
    AFTER UPDATE
    ON organizations
    FOR EACH ROW
EXECUTE PROCEDURE cascade_delete_organizations_fun();

CREATE TRIGGER cascade_delete_classrooms
    AFTER UPDATE
    ON classrooms
    FOR EACH ROW
EXECUTE PROCEDURE cascade_delete_classrooms_fun();

CREATE TRIGGER cascade_delete_teams
    AFTER UPDATE
    ON teams
    FOR EACH ROW
EXECUTE PROCEDURE cascade_delete_teams_fun();

CREATE TRIGGER cascade_delete_assignments
    AFTER UPDATE
    ON assignments
    FOR EACH ROW
EXECUTE PROCEDURE cascade_delete_assignments_fun();

CREATE TRIGGER cascade_delete_repos
    AFTER UPDATE
    ON repos
    FOR EACH ROW
EXECUTE PROCEDURE cascade_delete_repos_fun();

--NOT TESTED
CREATE TRIGGER check_team_members_counter
    AFTER INSERT OR UPDATE
    ON students
    FOR EACH ROW
EXECUTE PROCEDURE check_team_members_fun();

CREATE TRIGGER check_team_counter
    AFTER INSERT
    ON teams
    FOR EACH ROW
EXECUTE PROCEDURE check_class_teams_fun();

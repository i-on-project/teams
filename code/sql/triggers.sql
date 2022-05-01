--NOT TESTED
CREATE TRIGGER team_members_counter
    AFTER INSERT OR UPDATE
    ON student
    FOR EACH ROW
EXECUTE PROCEDURE check_team_members();

CREATE TRIGGER team_counter
    AFTER INSERT
    ON teams
    FOR EACH ROW
EXECUTE PROCEDURE check_class_teams();

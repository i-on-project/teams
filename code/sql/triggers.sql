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

CREATE OR REPLACE FUNCTION check_team_members()
    RETURNS TRIGGER AS
$$
DECLARE
    stdNumber       int = ((SELECT number
                            FROM new FETCH FIRST ROW ONLY));
    tId             int = (SELECT tId
                           FROM student
                           WHERE number = stdNumber);
    members_counter int = (SELECT count(*)
                           FROM student
                           WHERE tid = tId);
BEGIN
    IF ((SELECT cid FROM teams WHERE id = tId) != (SELECT cid FROM student WHERE sid = stdNumber) ||
                                                 (members_counter > (SELECT maxmemberspergroup
                                                                     FROM classrooms
                                                                     WHERE id = (SELECT cid FROM teams WHERE id = tId)))) THEN
        BEGIN
            RAISE EXCEPTION 'Student cannot enter this group.';
        END;
    END IF;
END
$$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION check_class_teams()
    RETURNS TRIGGER AS
$$
DECLARE
    tId       int = (SELECT max(id)
                     FROM teams);
    maxGroups int = (SELECT maxgroups
                     FROM classrooms
                     WHERE id = (SELECT cid FROM teams WHERE teams.id = tId));
BEGIN
    IF ((SELECT count(*) FROM teams where id = tId) > maxGroups) THEN
        BEGIN
            RAISE EXCEPTION 'This class cannot have more teams';
        END;
    END IF;
END
$$
    LANGUAGE 'plpgsql';
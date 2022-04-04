--NOT TESTED
CREATE TRIGGER team_members_counter
    AFTER INSERT OR UPDATE
    ON student
    FOR EACH ROW
EXECUTE PROCEDURE check_team_members();

CREATE TRIGGER team_counter
    AFTER INSERT
    ON team
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
    IF ((SELECT cid FROM team WHERE id = tId) != (SELECT cid FROM student WHERE number = stdNumber) ||
                                                 (members_counter > (SELECT maxmemberspergroup
                                                                     FROM classroom
                                                                     WHERE id = (SELECT cid FROM team WHERE id = tId)))) THEN
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
                     FROM team);
    maxGroups int = (SELECT maxgroups
                     FROM classroom
                     WHERE id = (SELECT cid FROM team WHERE team.id = tId));
BEGIN
    IF ((SELECT count(*) FROM team where id = tId) > maxGroups) THEN
        BEGIN
            RAISE EXCEPTION 'This class cannot have more teams';
        END;
    END IF;
END
$$
    LANGUAGE 'plpgsql';
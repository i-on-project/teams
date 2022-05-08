CREATE OR REPLACE FUNCTION check_team_members_fun() --on STUDENTS
    RETURNS TRIGGER AS
$check_team_members_fun$
DECLARE
    members_counter int = (SELECT count(*)
                           FROM students
                           WHERE tid = new.tid);
BEGIN
    --guarantees that max members per team is not exceed
    IF (members_counter > (SELECT maxmembersperteam FROM classrooms WHERE id = new.cId)) THEN
        BEGIN
            RAISE EXCEPTION 'Student cannot enter this group.';
        END;
    END IF;

    RETURN new;
END
$check_team_members_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION check_class_teams_fun() --on TEAMS
    RETURNS TRIGGER AS
$check_class_teams_fun$
DECLARE
    teamId   int = new.id;
    maxTeams int = (SELECT maxteams
                    FROM classrooms
                    WHERE id = teamId);
BEGIN
    IF (maxTeams < (SELECT count(*) FROM teams where id = teamId)) THEN
        BEGIN
            RAISE EXCEPTION 'This class cannot have more teams';
        END;
    END IF;
    RETURN new;
END
$check_class_teams_fun$
    LANGUAGE 'plpgsql';

/************************* Cascade Delete Functions *************************/

CREATE OR REPLACE FUNCTION cascade_delete_organizations_fun()
    RETURNS TRIGGER AS
$cascade_delete_organizations_fun$
BEGIN
    IF (old.deleted = B'0' AND new.deleted = B'1') THEN
        BEGIN
            UPDATE classrooms SET deleted = B'1' WHERE orgid = new.id;
        END;
    END IF;
    RETURN new;
END
$cascade_delete_organizations_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION cascade_delete_classrooms_fun()
    RETURNS TRIGGER AS
$cascade_delete_classrooms_fun$
BEGIN
    IF (old.deleted = B'0' AND new.deleted = B'1') THEN
        BEGIN
            UPDATE teams SET deleted = B'1' WHERE cid = new.id;
            UPDATE teachers SET deleted = B'1' WHERE cid = new.id;
            UPDATE invite_links SET deleted = B'1' WHERE cid = new.id;
            UPDATE assignments SET deleted = B'1' WHERE cid = new.id;
        END;
    END IF;

    IF (old.state != new.state) THEN
        BEGIN
            UPDATE teams SET state = new.state WHERE cid = new.id;
        END;
    END IF;
    RETURN new;
END
$cascade_delete_classrooms_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION cascade_delete_teams_fun()
    RETURNS TRIGGER AS
$cascade_delete_teams_fun$
BEGIN
    IF (old.deleted = B'0' AND new.deleted = B'1') THEN
        BEGIN
            UPDATE students SET deleted = B'1' WHERE tid = new.id;
            UPDATE notes SET deleted = B'1' WHERE tid = new.id;
            UPDATE repos SET deleted = B'1' WHERE tid = new.id;
        END;
    END IF;
    RETURN new;
END
$cascade_delete_teams_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION cascade_delete_assignments_fun()
    RETURNS TRIGGER AS
$cascade_delete_assignments_fun$
BEGIN
    IF (old.deleted = B'0' AND new.deleted = B'1') THEN
        BEGIN
            UPDATE deliveries SET deleted = B'1' WHERE assid = new.id;
        END;
    END IF;
    RETURN new;
END
$cascade_delete_assignments_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION cascade_delete_repos_fun()
    RETURNS TRIGGER AS
$cascade_delete_repos_fun$
BEGIN
    IF (old.deleted = B'0' AND new.deleted = B'1') THEN
        BEGIN
            UPDATE tags SET deleted = B'1' WHERE repoid = new.id;
        END;
    END IF;
    RETURN new;
END
$cascade_delete_repos_fun$
    LANGUAGE 'plpgsql';


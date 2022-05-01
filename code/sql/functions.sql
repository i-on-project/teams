
CREATE OR REPLACE FUNCTION check_team_members_fun() --on STUDENT
    RETURNS TRIGGER AS
$check_team_members_fun$
DECLARE
    members_counter int = (SELECT count(*)
                           FROM student
                           WHERE tid = new.id);
BEGIN
    --guarantees that max members per team is not exceed
    IF (members_counter > (SELECT maxmembersperteam FROM classrooms WHERE id = new.cId)) THEN
        BEGIN
            RAISE EXCEPTION 'Student cannot enter this group.';
        END;
    END IF;
END
$check_team_members_fun$
    LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION check_class_teams_fun() --on TEAMS
    RETURNS TRIGGER AS
$check_class_teams_fun$
DECLARE
    teamId       int = new.id;
    maxTeams int = (SELECT maxteams
                     FROM classrooms
                     WHERE id = teamId);
BEGIN
    IF (maxTeams < (SELECT count(*) FROM teams where id = teamId)) THEN
        BEGIN
            RAISE EXCEPTION 'This class cannot have more teams';
        END;
    END IF;
END
$check_class_teams_fun$
    LANGUAGE 'plpgsql';
/**
  All the data here presented is fake. Only for tests.
 */

INSERT INTO organizations (name, description)
VALUES ('AVE', 'Ambientes Virtuais de Execução.'),
       ('PC', 'Programação comcurrente.');

INSERT INTO classrooms (name, description, maxteams, maxmembersperteam, linkrepo, schoolyear, orgid)
VALUES ('li4xd', 'Turma x 4 ano.', 3, 2, 'https://github.com/isel-leic-ave/lae-2021-22-sem2-i4xd', '2021/22', 1),
       ('li5xd', 'Turma x 5 ano.', 3, 2, 'https://github.com/isel-leic-pc/pc-2021-22-sem2-i4xd', '2021/22', 2);

INSERT INTO teacher (number, name, email, office)
VALUES (86951, 'Humberto Silva', 'humberto.silva@isel.pt', 'F.0.5'),
       (85462, 'Gilberto Silva', 'gilberto.silva@isel.pt', 'F.0.6');

INSERT INTO teachers (number, cid)
VALUES (86951, 1),
       (85462, 2);

INSERT INTO invite_links (link, cid)
VALUES ('api/orgs/1/classrooms/1/invite-link', 1),
       ('api/orgs/2/classrooms/2/invite-link', 2);

INSERT INTO student (number, name)
VALUES (80000, 'Julinho Silva'),
       (90000, 'Juliano Silva');

INSERT INTO teams (name, cid)
VALUES ('li4xd-g1', 1),
       ('li5xd-g1', 2);

INSERT INTO students (number, tid, cid)
VALUES (80000, 1, 1),
       (90000, 2, 2);

INSERT INTO assignments (cid, description)
VALUES (1, 'Make application yes yes.'),
       (2, 'Make chess game for android.');

INSERT INTO deliveries (assid, name, date)
VALUES (1, 'phase 1', now()),
       (2, 'phase 0', now());


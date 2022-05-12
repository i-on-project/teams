/**
  All the data here presented is fake. Only for tests.
 */

INSERT INTO organizations (name, description, githuburi, avataruri)
VALUES ('AVE', 'Ambientes Virtuais de Execução.', 'https://github.com/example1', 'https://github.com/example1'),
       ('PC', 'Programação comcurrente.', 'https://github.com/example2', 'https://github.com/example2');

INSERT INTO classrooms (name, description, maxteams, maxmembersperteam, repoURI, schoolyear, orgid, githuburi,
                        avataruri)
VALUES ('li4xd', 'Turma x 4 ano.', 3, 2, 'https://github.com/isel-leic-ave/lae-2021-22-sem2-i4xd', '2021/22', 1,
        'https://github.com/example1', 'https://github.com/example1'),
       ('li4xd', 'Turma y 4 ano.', 3, 2, 'https://github.com/isel-leic-ave/lae-2021-22-sem2-i4yd', '2021/22', 1,
        'https://github.com/example2', 'https://github.com/example2'),
       ('li5xd', 'Turma x 5 ano.', 3, 2, 'https://github.com/isel-leic-pc/pc-2021-22-sem2-i4xd', '2021/22', 2,
        'https://github.com/example3', 'https://github.com/example3');

INSERT INTO teacher (number, name, email, office)
VALUES (86951, 'Humberto Silva', 'humberto.silva@isel.pt', 'F.0.5'),
       (85462, 'Gilberto Silva', 'gilberto.silva@isel.pt', 'F.0.6');

INSERT INTO teachers (number, cid)
VALUES (86951, 1),
       (85462, 2);

INSERT INTO invite_links (code, cid)
VALUES ('8b171ab5-2f09-4272-a607-f8fd68eeca31', 1),
       ('426822c5-76e1-4cbb-bd92-b2a3dc4a261b', 2);

INSERT INTO student (number, name)
VALUES (80000, 'Julinho Silva'),
       (90000, 'Juliano Silva'),
       (10000, 'João Silva');

-- Insert teams with state pending
INSERT INTO teams (name, cid)
VALUES ('li4xd-g1', 1),
       ('li5xd-g1', 2);

-- Insert teams with state active
INSERT INTO teams (name, cid, state)
VALUES ('li4xd-g2', 1, 'active');

INSERT INTO students (number, tid, cid)
VALUES (80000, 1, 1),
       (90000, 2, 2),
       (10000, 3, 1);

INSERT INTO assignments (cid, description)
VALUES (1, 'Make application yes yes.'),
       (2, 'Make chess game for android.');

INSERT INTO deliveries (assid, name, date)
VALUES (1, 'phase 1', now()),
       (1, 'phase 2', now()),
       (2, 'phase 0', now());

INSERT INTO repos (url, name, tid, assid)
VALUES ('https://github.com/example1/team1', 'Grupo 1', 1, 1);

INSERT INTO tags (name, delid, repoid)
VALUES ('phase 1', 1, 1),
       ('phase 2', 2, 1);

INSERT INTO notes (tid, description)
VALUES (1, 'Does not meet all requirements.'),
       (2, 'Does meet all requirements.');


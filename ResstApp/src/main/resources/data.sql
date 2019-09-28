TRUNCATE TABLE tacs.user;
INSERT INTO tacs.user (id, username, password, admin) VALUES (1,'tacs1','1234',false),
(2,'tacs2','1234',false),
(3,'tacs3','1234',false),
(4,'admin1','1234',true),
(5,'admin2','1234',true);

TRUNCATE TABLE tacs.repository;
INSERT INTO tacs.repository(id, favs, main_language, nof_forks, score, total_commits, total_issues, owner, name, registration_date)
VALUES (205392106, 5, "C", 6, 1, 5, 5, "LucasBlanco", "TACS", DATE('2019-09-22')),
(161207084, 5, "C", 6, 1, 5, 5, "LuciaRoldan", "tp-tadp", DATE('2019-09-20')),
(153519881, 1, "C", 0, 0, 0, 0, "rociochipian", "gdd_mate_lavado", DATE('2019-09-21')),
(107976932, 2, "JAVA", 2, 5, 10, 4, "matigiorda", "TaTeTiWollok", DATE('2019-09-19')),
(141071043, 10, "JAVA", 3, 1, 3, 32, "lucasmcenturion", "Fail-System", DATE('2019-09-22')),
(138937247, 11, "RUBY", 22, 3, 13, 3, "Juam", "Simplex-Solucion", DATE('2019-09-22'));

TRUNCATE TABLE tacs.user_favourites;
INSERT INTO tacs.user_favourites(user_id, favourites_id)
VALUES(1,1),
(1,3),
(2,1),
(2,2),
(2,3),
(2,4),
(3,5),
(4,1),
(4,5);
TRUNCATE TABLE tacs.user;
INSERT INTO tacs.user (username, password, admin) VALUES 
('tacs1','1234',false),
('tacs2','1234',false),
('tacs3','1234',false),
('admin1','1234',true),
('admin2','1234',true);

TRUNCATE TABLE tacs.repository;
INSERT INTO tacs.repository(id, favs, main_language, nof_forks, score, total_commits, total_issues, owner, name, registration_date, size, stars) VALUES 
(205392106, 3, "C", 6, 1, 5, 5, "LucasBlanco", "TACS", DATE('2019-09-22'), 100, 1),
(161207084, 1, "C", 6, 1, 5, 5, "LuciaRoldan", "tp-tadp", DATE('2019-09-20'), 100, 2),
(153519881, 2, "C", 0, 0, 0, 0, "rociochipian", "gdd_mate_lavado", DATE('2019-09-21'), 100, 3),
(107976932, 1, "JAVA", 2, 5, 10, 4, "matigiorda", "TaTeTiWollok", DATE('2019-09-19'), 100, 4),
(141071043, 2, "JAVA", 3, 1, 3, 32, "lucasmcenturion", "Fail-System", DATE('2019-09-22'), 100, 5),
(138937247, 0, "RUBY", 22, 3, 13, 3, "Juam", "Simplex-Solucion", DATE('2019-09-22'), 100, 6);

TRUNCATE TABLE tacs.user_favourites;
INSERT INTO tacs.user_favourites(user_id, favourites_id) VALUES
(1,205392106),
(1,153519881),
(2,205392106),
(2,161207084),
(2,153519881),
(2,107976932),
(3,141071043),
(4,205392106),
(4,141071043);
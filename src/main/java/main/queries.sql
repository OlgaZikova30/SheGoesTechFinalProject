-- Following information of players will be stored in database:
-- - Player nickname
-- - Player name
-- - Player surname
-- - Player age
-- - Game ID
-- - Moves of each game

CREATE TABLE IF NOT EXISTS Players (
    nickname VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255)NOT NULL,
    age INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS Games (
    id BIGINT auto_increment PRIMARY KEY,
    nickname1 VARCHAR(255) NOT NULL,
    nickname2 VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS Moves (
       gameId INT NOT NULL,
       nickname VARCHAR(255) NOT NULL,
       movesX INT NOT NULL DEFAULT 0,
       movesY INT NOT NULL DEFAULT 0
       );


-- add a player
INSERT INTO Players
(nickname,name, surname, age) VALUES
(?, ?, ?, ?);

-- add game
INSERT INTO Games
(nickname1, nickname2) VALUES
(?, ?);

-- add all moves of each game
INSERT INTO Moves
(gameId, nickname, movesX, movesY) VALUES
(?, ?, ?, ?);

-- list all games of one player
SELECT *
FROM Games
WHERE nickname1, nickname2 = ?;

-- select one game by ID and see all moves
SELECT *
FROM Moves
WHERE gameId = ?;
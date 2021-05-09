-- Following information of players will be stored in database:
-- - Player nickname
-- - Player name
-- - Player surname
-- - Player age
-- - Games played by player
-- - Moves made by player

CREATE TABLE IF NOT EXISTS Players (
    nickname VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255)NOT NULL,
    age INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS Games (
    nickname VARCHAR(255),
    games_played INT,
    moves_made INT
    );

-- add a player
INSERT INTO Players
(nickname,name, surname, age) VALUES
(?, ?, ?, ?);

-- list all games of one player
SELECT *
FROM Games
WHERE nickname = ?;
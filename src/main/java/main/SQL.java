package main;

public class SQL {
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Players (" +
                    "    nickname VARCHAR(255) PRIMARY KEY," +
                    "    name VARCHAR(255) NOT NULL," +
                    "    surname VARCHAR(255)NOT NULL," +
                    "    age INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS Games (" +
                    "    nickname VARCHAR(255) PRIMARY KEY," +
                    "    games_played INT," +
                    "    moves_made INT" +

                    ");";

}

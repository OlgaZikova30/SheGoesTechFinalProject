package main;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./tictactoe";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Scanner scanner = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS Players (" +
                    "    nickname VARCHAR(255) PRIMARY KEY," +
                    "    name VARCHAR(255) NOT NULL," +
                    "    surname VARCHAR(255) NOT NULL," +
                    "    age INT NOT NULL" +
                    ");";
    public static final String CREATE_TABLE1 =
            "CREATE TABLE IF NOT EXISTS Games (" +
                    "    nickname1 VARCHAR(255)," +
                    "    nickname2 VARCHAR(255)," +
                    "    id BIGINT auto_increment PRIMARY KEY " +
                    ");";
    public static final String CREATE_TABLE2 =
            "CREATE TABLE IF NOT EXISTS Moves (" +
                    "    gameId INT NOT NULL," +
                    "    nickname VARCHAR(255)," +
                    "    movesX INT NOT NULL DEFAULT 0," +
                    "    movesY INT NOT NULL DEFAULT 0" +
                    ");";
    public static final String INSERT_PLAYERS = "INSERT INTO Players" + "(nickname,name, surname, age) VALUES" +
            "(?, ?, ?, ?);";

    public static final String INSERT_GAMES = "INSERT INTO Games" +
            "(nickname1, nickname2) VALUES" +
            "(?,?);";
    public static final String GET_GAMES_ID ="SELECT * from Games ORDER BY id DESC LIMIT 1";

    public static final String INSERT_MOVES = "INSERT INTO Moves" +
            "(gameId, nickname, movesX, movesY) VALUES" +
            "(?,?,?,?);";
    public static final String ALL_GAMES = "SELECT * FROM Games;";

    public static final String GAME = "SELECT * FROM Games WHERE gameId = ?;";

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int update = statement.executeUpdate(CREATE_TABLE);
            int update1 = statement.executeUpdate(CREATE_TABLE1);
            int update2 = statement.executeUpdate(CREATE_TABLE2);
            System.out.println("Tables successfully created " + update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            prepareDatabase(connection);

            //Play TicTacToe
            TicTacToe game = new TicTacToe();
            game.play(connection);

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    //NR.1 Check horizontal position. True - if one of the horizontal has three cells in a row
    //    occupied by the sign of the specified player (int playerToCheck)
    public boolean isWinPositionForHorizontals(int[][] field, int playerToCheck) {
        for (int i = 0; i < field.length; i++) {
            if (field[i][0] == playerToCheck &&
                    field[i][1] == playerToCheck &&
                    field[i][2] == playerToCheck) {
                return true;
            }
        }
        return false;
    }

    //NR.2 Check vertical position, True - if one of the horizontal has three cells in a row
    // occupied by the sign of the specified player (int playerToCheck)
    public boolean isWinPositionForVerticals(int[][] field, int playerToCheck) {
        for (int i = 0; i < field.length; i++) {
            if (field[0][i] == playerToCheck &&
                    field[1][i] == playerToCheck &&
                    field[2][i] == playerToCheck) {
                return true;
            }
        }
        return false;
    }

    //NR.3 Check diagonal position, True - if one of the horizontal has three cells in a row
    // occupied by the sign of the specified player (int playerToCheck)
    public boolean isWinPositionForDiagonals(int[][] field, int playerToCheck) {
        if (field[0][0] == playerToCheck &&
                field[1][1] == playerToCheck &&
                field[2][2] == playerToCheck) {
            return true;
        } else if (field[0][2] == playerToCheck &&
                field[1][1] == playerToCheck &&
                field[2][0] == playerToCheck) {
            return true;
        }
        return false;
    }

    //NR.4 Check WIN position by horizontal, vertical, diagonal
    //True if position is victorious for the specified player (int playerToCheck)
    public boolean isWinPosition(int[][] field, int playerToCheck) {
        return isWinPositionForHorizontals(field, playerToCheck) ||
                isWinPositionForVerticals(field, playerToCheck) ||
                isWinPositionForDiagonals(field, playerToCheck);
    }

    //NR.5 - Check Draw position
    public boolean isDrawPosition(int[][] field) {
        boolean empty = false;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == 0) {
                    empty = true;
                    break;
                }
            }
        }
        if (!empty && !isWinPosition(field, 1) && !isWinPosition(field, 2)) {
            return true;
        }
        return false;
    }

    //NR. 6 Create two dimensional array
    //0 is mark of empty cell
    public int[][] createField() {
        int[][] board = new int[3][3];
        for (int[] ints : board) {
            Arrays.fill(ints, 0);
        }
        return board;
    }

    //NR.7 - two coordinates from user and cell where the user wants to go
    public Move getNextMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter x: ");
        int x = scanner.nextInt();
        System.out.print("Enter y: ");
        int y = scanner.nextInt();
        return new Move(x, y);
    }

    //NR.8 - Print field after each move
    public void printFieldToConsole(int[][] field) {
        for (int[] ints : field) {
            for (int anInt : ints) {
                System.out.print(anInt + "\t");
            }
            System.out.println();
        }

    }
    private boolean isPresent (Connection connection, String nickname) throws SQLException {
        try (PreparedStatement c1 = connection.prepareStatement("SELECT count(1) as total FROM Players WHERE nickname = ?;")) {
            c1.setString(1, nickname);
            ResultSet r = c1.executeQuery();
            int count = 0;
            if (r.next()) {
                count = r.getInt("total");
            }
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertPlayer (Connection connection, String nickname, String name, String surname, int age) throws SQLException {
        try (PreparedStatement c1 = connection.prepareStatement(INSERT_PLAYERS)) {
            c1.setString(1, nickname);
            c1.setString(2, name);
            c1.setString(3, surname);
            c1.setInt(4, age);
            c1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playAgain (Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you want play again? (Yes or No)");
        String command = scanner.nextLine();

        while ("Yes".equalsIgnoreCase(command) || "No".equalsIgnoreCase(command)) {
            if ("Yes".equalsIgnoreCase(command)) {
                TicTacToe game = new TicTacToe();
                game.play(connection);
        } else
            System.out.println("GAME OVER!");
            break;
        }
    }

    //NR. 9 determine the order of moves of players 1 or 2, check if cell is not empty, check winner
    public void play(Connection connection) throws SQLException  {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the nickname (Player 1):");
        String player1 = scanner.nextLine();
        if(!this.isPresent(connection, player1)) {
            System.out.println("User is not registered! Please enter name:");
            String player1Name = scanner.nextLine();
            System.out.println("Please enter surname:");
            String player1Surname = scanner.nextLine();
            System.out.println("Please enter the age:");
            int player1Age = Integer.parseInt(scanner.nextLine());
            insertPlayer(connection, player1, player1Name, player1Surname,player1Age);
        }


// select no player
        System.out.println("Please enter the nickname (Player 2):");
        String player2 = scanner.nextLine();
        if(!this.isPresent(connection, player2)) {
            System.out.println("User is not registered! Please enter name:");
            String player2Name = scanner.nextLine();
            System.out.println("Please enter surname:");
            String player2Surname = scanner.nextLine();
            System.out.println("Please enter the age:");
            int player2Age = Integer.parseInt(scanner.nextLine());
            insertPlayer(connection, player2, player2Name, player2Surname, player2Age);
        }

        try (PreparedStatement c1 = connection.prepareStatement(INSERT_GAMES)) {
            c1.setString(1, player1);
            c1.setString(2, player2);
            c1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int gameId = -1;
        try (PreparedStatement c1 = connection.prepareStatement(GET_GAMES_ID)) {
            c1.executeQuery();
            ResultSet r = c1.executeQuery();
            if (r.next()) {
                gameId = r.getInt(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[][] field = createField();

        while (true) {
            System.out.println("Player 1: ");
            printFieldToConsole(field);

            while (true) {
                Move move0 = getNextMove();
                if(move0.getX() >= field.length || move0.getX() < 0) {
                    System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                    continue;
                }
                else if(move0.getY() >= field[0].length || move0.getY() < 0) {
                    System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                    continue;
                }
                else if (field[move0.getX()][move0.getY()] != 0) {
                    System.out.println("Cell not empty! Please select a another one!");
                    continue;
                }
                field[move0.getX()][move0.getY()] = 1;

                try (PreparedStatement c1 = connection.prepareStatement(INSERT_MOVES)) {
                    c1.setInt(1, gameId);
                    c1.setString(2, player1);
                    c1.setInt(3, move0.getX());
                    c1.setInt(4, move0.getY());
                    c1.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            if (isDrawPosition(field)) {
                System.out.println("Tie result!");
                playAgain(connection);
                break;
            }
            if (isWinPosition(field, 1)) {
                System.out.println("Player 1 WIN!");
                playAgain(connection);
                break;
            }

            System.out.println("Player 2: ");
            printFieldToConsole(field);
            while (true) {
                Move move1 = getNextMove();
                if(move1.getX() >= field.length || move1.getX() < 0) {
                    System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                    continue;
                }
                else if(move1.getY() >= field[0].length || move1.getY() < 0) {
                    System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                    continue;
                }
                else if (field[move1.getX()][move1.getY()] != 0) {
                    System.out.println("Cell not empty! Please select a another one!");
                    continue;
                }
                field[move1.getX()][move1.getY()] = 2;

                try (PreparedStatement c1 = connection.prepareStatement(INSERT_MOVES)) {
                    c1.setInt(1, gameId);
                    c1.setString(2, player2);
                    c1.setInt(3, move1.getX());
                    c1.setInt(4, move1.getY());
                    c1.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            }
            //   printFieldToConsole(field);
            if (isWinPosition(field, 2)) {
                System.out.println("Player 2 WIN!");
                playAgain(connection);
                break;
            }
            if (isDrawPosition(field)) {
                System.out.println("Tie result!");
                playAgain(connection);
                break;
            }
        }

    }
}

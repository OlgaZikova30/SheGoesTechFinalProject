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
    public static final String ADD_USERNAME = "INSERT INTO Players" +
            " (nickname,name,surname,age) VALUES" +
            "(?, ?, ?, ?);";

    public static final String CHECK_USERNAME = "SELECT FROM Players WHERE nickname = ?;";

    private static Scanner scanner = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            //Play TicTacToe
            TicTacToe game = new TicTacToe();
            prepareDatabase(connection);
            game.play(connection);


        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            int update = statement.executeUpdate(CREATE_TABLE);
        }
    }

    private static void ADD_USERNAME (Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_USERNAME)) {
          statement.setString(1,"sandra");
          statement.setString(2, "sandra");
          statement.setString(3, "krugalauza");
          statement.setInt(4, 33);
          statement.execute();
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

    //NR. 9 determine the order of moves of players 1 or 2, check if cell is not empty, check winner
    public void play(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the username (Player 1):");
        String player1 = scanner.nextLine();
        try (PreparedStatement statement = connection.prepareStatement(CHECK_USERNAME)) {
            statement.setString(1, player1);
            try (ResultSet rs = statement.executeQuery() ) {
                String usernameCounter;
                while (rs.next()) {
                    usernameCounter = rs.getString(1);
                    if (usernameCounter.equalsIgnoreCase(player1)) {
                        System.out.println("Username is already registered!");
                    }
                    else {
                        System.out.println("Please register!");
                        try (PreparedStatement statement1 = connection.prepareStatement(ADD_USERNAME)){

                        }

                    }
                }
            }

        }

// select no player
            System.out.println("Please enter the username (Player 2):");
            String player2 = scanner.nextLine();

            int[][] field = createField();

            while (true) {
                System.out.println("Player 1: ");
                printFieldToConsole(field);

                while (true) {
                    Move move0 = getNextMove();
                    if (move0.getX() >= field.length || move0.getX() < 0) {
                        System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                        continue;
                    } else if (move0.getY() >= field[0].length || move0.getY() < 0) {
                        System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                        continue;
                    } else if (field[move0.getX()][move0.getY()] != 0) {
                        System.out.println("Cell not empty! Please select a another one!");
                        continue;
                    }
                    field[move0.getX()][move0.getY()] = 1;
                    break;
                }
                if (isDrawPosition(field)) {
                    System.out.println("Tie result!");
                    break;
                }
                if (isWinPosition(field, 1)) {
                    System.out.println("Player 1 WIN!");
                    break;
                }

                System.out.println("Player 2: ");
                printFieldToConsole(field);
                while (true) {
                    Move move1 = getNextMove();
                    if (move1.getX() >= field.length || move1.getX() < 0) {
                        System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                        continue;
                    } else if (move1.getY() >= field[0].length || move1.getY() < 0) {
                        System.out.println("Not valid value. Please enter the value in range 0 to 2!");
                        continue;
                    } else if (field[move1.getX()][move1.getY()] != 0) {
                        System.out.println("Cell not empty! Please select a another one!");
                        continue;
                    }
                    field[move1.getX()][move1.getY()] = 2;
                    break;
                }
                //   printFieldToConsole(field);
                if (isWinPosition(field, 2)) {
                    System.out.println("Player 2 WIN!");
                    break;
                }
                if (isDrawPosition(field)) {
                    System.out.println("Tie result!");
                    break;
                }
            }

        }

}

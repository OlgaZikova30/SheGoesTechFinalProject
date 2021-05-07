package main;

import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        //Play TicTacToe
        TicTacToe game = new TicTacToe();
        game.play();
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
        for (int[] ints : field) {
            for (int j = 0; j < field[1].length; j++) {
                if (ints[j] == -1) {
                    empty = true;
                    break;
                }
            }
            if (!isWinPosition(field, 0) &&
                    !isWinPosition(field, 1) &&
                    !empty) {
                return true;
            }
        }
        return false;
    }

    //NR. 6 Create two dimensional array
    //-1 is mark of empty cell
    public int[][] createField() {
        int[][] board = new int[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = -1;
            }
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
        Move move = new Move(x, y);
        return move;
    }

    //NR.8 - Print field after each move
    public void printFieldToConsole(int[][] field) {
        for (int[] ints : field) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j] + "\t");
            }
            System.out.println();
        }
    }

    //NR. determine the order of moves of players 0 or 1, check if cell is not empty, check winner
    public void play() {
        int[][] field = createField();
        while (true) {
            System.out.println("Player 0:");
            printFieldToConsole(field);
            while (true) {
                Move move0 = getNextMove();
                if (field[move0.getX()][move0.getY()] != -1) {
                    System.out.println("Cell not empty! Please select a another one!");
                    continue;
                }
                field[move0.getX()][move0.getY()] = 0;
                break;
            }
            printFieldToConsole(field);
            if (isWinPosition(field, 0)) {
                System.out.println("Player 0 WIN!");
                break;
            }

            System.out.println("Player 1:");
            printFieldToConsole(field);
            while (true) {
                Move move1 = getNextMove();
                if (field[move1.getX()][move1.getY()] != -1) {
                    System.out.println("Cell not empty! Please select a another one!");
                    continue;
                }
                field[move1.getX()][move1.getY()] = 1;
                break;
            }
            printFieldToConsole(field);
            if (isWinPosition(field, 1)) {
                System.out.println("Player 1 WIN!");
                break;
            }
        }
    }
}

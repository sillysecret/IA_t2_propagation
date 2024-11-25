package org.example.entity;

public class Minimax {

    public int evaluate(int[] board) {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Linhas
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Colunas
                {0, 4, 8}, {2, 4, 6}            // Diagonais
        };

        for (int[] combo : winningCombinations) {
            if (board[combo[0]] == board[combo[1]] &&
                    board[combo[1]] == board[combo[2]] &&
                    board[combo[0]] != 0) {
                return board[combo[0]];
            }
        }
        return 0; // Nenhum vencedor
    }

    public boolean isGameOver(int[] board) {
        return evaluate(board) != 0 || !checkAvailability(board);
    }

    public boolean checkAvailability(int[] board) {
        for (int cell : board) {
            if (cell == 0) {
                return true;
            }
        }
        return false;
    }

    public int minimax(int[] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);

        // Verifica condições de vitória
        if (score == -1) {
            return score;
        }
        if (score == 1) {
            return score;
        }

        // Verifica empate
        if (!checkAvailability(board)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = -1; // Computador joga
                    bestScore = Math.max(bestScore, minimax(board, depth + 1, false));
                    board[i] = 0; // Desfaz movimento
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1; // Jogador joga
                    bestScore = Math.min(bestScore, minimax(board, depth + 1, true));
                    board[i] = 0; // Desfaz movimento
                }
            }
            return bestScore;
        }
    }

    public int findBestMove(double[] board) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0.0) { // Verifica célula vazia no tabuleiro
                board[i] = -1.0; // Computador tenta jogar
                int score = minimax(
                        convertBoardToInt(board), 0, false); // Converte o tabuleiro para int[]
                board[i] = 0.0; // Desfaz movimento

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    private int[] convertBoardToInt(double[] board) {
        int[] intBoard = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            intBoard[i] = (int) board[i]; // Converte valores de double para int
        }
        return intBoard;
    }
}

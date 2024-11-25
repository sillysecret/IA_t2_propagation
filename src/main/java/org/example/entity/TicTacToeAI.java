package org.example.entity;

import java.util.Scanner;

public class TicTacToeAI {

    // Verifica se há um vencedor
    public static int evaluate(int[] board) {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : winningCombinations) {
            if (board[combo[0]] == board[combo[1]] &&
                    board[combo[1]] == board[combo[2]] &&
                    board[combo[0]] != 0) {
                return board[combo[0]]; // -1 para vitória da máquina, 1 para jogador
            }
        }

        return 0; // Empate ou ainda em andamento
    }

    public static boolean checkAvailability(int[] board) {
        for (int cell : board) {
            if (cell == 0) {
                return true;
            }
        }
        return false; // Não há mais jogadas possíveis
    }

    // Implementação do Minimax
    public static int minimax(int[] board, boolean isMaximizing) {
        int score = evaluate(board);

        // Condições de parada
        if (score == -1) return 1;  // Vitória do computador
        if (score == 1) return -1;  // Vitória do jogador
        if (!checkAvailability(board)) return 0;  // Empate

        // Turno do computador (máximo)
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = -1; // Computador faz a jogada
                    bestScore = Math.max(bestScore, minimax(board, false)); // Chama minimax para o próximo turno
                    board[i] = 0; // Reverte a jogada
                }
            }
            return bestScore;
        } else { // Turno do jogador (mínimo)
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) {
                    board[i] = 1; // Jogador faz a jogada
                    bestScore = Math.min(bestScore, minimax(board, true)); // Chama minimax para o próximo turno
                    board[i] = 0; // Reverte a jogada
                }
            }
            return bestScore;
        }
    }

    // Encontra o melhor movimento para o computador
    public static int findBestMove(int[] board) {
        int bestMove = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = -1; // Computador faz a jogada temporária
                int score = minimax(board, false); // Avalia o movimento
                board[i] = 0; // Reverte a jogada

                // Atualiza o melhor movimento
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }

    // Função de movimento do computador
    public static void computerMove(int[] board) {
        int bestMove = findBestMove(board);
        if (bestMove != -1) {
            board[bestMove] = -1; // Computador faz a jogada
        }
    }

    // Função de movimento do jogador
    public static void playerMove(int[] board) {
        Scanner scanner = new Scanner(System.in);
        int move = -1;

        // Solicita uma jogada válida
        while (true) {
            System.out.print("Escolha uma posição (0-8): ");
            move = scanner.nextInt();

            if (move >= 0 && move <= 8 && board[move] == 0) {
                board[move] = 1; // Jogador faz a jogada
                break;
            } else {
                System.out.println("Movimento inválido! Tente novamente.");
            }
        }
    }

    // Método para imprimir o tabuleiro
    public static void printBoard(int[] board) {
        String[] symbols = { " ", "X", "O" }; // 0 é vazio, 1 é jogador (X), -1 é computador (O)
        System.out.println();
        for (int i = 0; i < 9; i++) {
            System.out.print(symbols[board[i]]);
            if (i % 3 == 2) {
                System.out.println();
            } else {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    // Função principal para jogar o jogo
    public static void playGame() {
        int[] board = new int[9]; // 0 = vazio, 1 = jogador, -1 = computador
        int winner = 0;

        // Loop principal do jogo
        while (winner == 0 && checkAvailability(board)) {
            // Exibe o tabuleiro antes de cada turno
            printBoard(board);

            // Turno do jogador
            playerMove(board);
            winner = evaluate(board);
            if (winner != 0) break;

            // Exibe o tabuleiro após a jogada do jogador
            printBoard(board);

            // Turno do computador
            computerMove(board);
            winner = evaluate(board);
        }

        // Exibe o resultado final
        printBoard(board);
        if (winner == 1) {
            System.out.println("Parabéns! Você venceu!");
        } else if (winner == -1) {
            System.out.println("O computador venceu. Tente novamente!");
        } else {
            System.out.println("Empate!");
        }
    }

    public static void main(String[] args) {
        playGame(); // Inicia o jogo
    }
}

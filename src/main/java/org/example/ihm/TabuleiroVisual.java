package org.example.ihm;

import org.example.dominio.Tabuleiro;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import org.example.operacao.Algoritimo;

public class TabuleiroVisual extends JPanel {

    private int tamanhoQuadrado;
    private Tabuleiro tabuleiro;
    private int widthComponent;

    public TabuleiroVisual(Tabuleiro tabuleiro, int w) {
        this.tabuleiro = tabuleiro;
        this.widthComponent = w;
        this.tamanhoQuadrado = widthComponent / Algoritimo.getN();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Atualiza o tamanho do quadrado com base no novo tamanho do painel
        tamanhoQuadrado = getWidth() / Algoritimo.getN();

        // Desenha o tabuleiro
        for (int y = 0; y < Algoritimo.getN(); y++) {
            for (int x = 0; x < Algoritimo.getN(); x++) {
                // Alterna a cor entre preto e branco
                if ((x + y) % 2 == 0) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(tamanhoQuadrado * x, tamanhoQuadrado * y, tamanhoQuadrado, tamanhoQuadrado);
            }
        }

        // Desenha as peças no tabuleiro
        for (int y = 0; y < Algoritimo.getN(); y++) {
            for (int x = 0; x < Algoritimo.getN(); x++) {
                int valor = tabuleiro.getTabuleiroMatriz()[x][y];

                if (valor == 1) { // Desenha a bolinha
                    g.setColor(Color.RED); // Cor para o 'O'
                    g.fillOval(tamanhoQuadrado * x + 10, tamanhoQuadrado * y + 10, tamanhoQuadrado - 20, tamanhoQuadrado - 20); // O desenho da bolinha
                } else if (valor == -1) { // Desenha o 'X'
                    g.setColor(Color.BLUE); // Cor para o 'X'
                    g.drawLine(tamanhoQuadrado * x + 10, tamanhoQuadrado * y + 10, tamanhoQuadrado * (x + 1) - 10, tamanhoQuadrado * (y + 1) - 10); // Linha 1 do 'X'
                    g.drawLine(tamanhoQuadrado * (x + 1) - 10, tamanhoQuadrado * y + 10, tamanhoQuadrado * x + 10, tamanhoQuadrado * (y + 1) - 10); // Linha 2 do 'X'
                }
            }
        }

    }

    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        repaint();  // Atualiza o painel
    }
}


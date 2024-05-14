package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PrimFrame extends JFrame {
    JPanel p = new JPanel();
    public final int LARG_DEFAULT;
    public final int ALT_DEFAULT;
    public final int CELULA_SIZE = 40; // Tamanho de cada celula do tabuleiro
    public final int NUMERO_COLUNAS = 16;
    public final int NUMERO_LINHAS = 16;

    public PrimFrame(String s) {
        super(s);

        // Calculando o tamanho da janela com base no tamanho do tabuleiro
        LARG_DEFAULT = CELULA_SIZE * (NUMERO_COLUNAS + 2) ;
        ALT_DEFAULT = CELULA_SIZE * (NUMERO_LINHAS + 2);

        setSize(LARG_DEFAULT, ALT_DEFAULT);
        getContentPane().add(new PrimPanel());
    }

    public static void main(String[] args) {
        PrimFrame f = new PrimFrame("Batalha naval");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    class PrimPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            // Desenhar o tabuleiro
            for (int row = 0; row < NUMERO_LINHAS; row++) {
                for (int col = 0; col < NUMERO_COLUNAS; col++) {
                    // Calcular a posição da célula
                    int x = col * CELULA_SIZE;
                    int y = row * CELULA_SIZE;

                    // Desenhar o retângulo representando a célula
                    Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                    g2d.setColor(Color.WHITE);
                    g2d.fill(cell);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(cell);

                    // Desenhar as letras da primeira coluna
                    if (col == 0 && row > 0) {
                        String letraLinha = String.valueOf((char) ('A' + row - 1));
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(letraLinha);
                        g2d.drawString(letraLinha, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);// centralizando a escrita na celula 
                    }

                    // Desenhar os números da primeira linha
                    if (row == 0 && col > 0) {
                        String numeroColuna = String.valueOf(col);
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(numeroColuna);
                        g2d.drawString(numeroColuna, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);// centralizando a escrita na celula 
                    }
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(LARG_DEFAULT, ALT_DEFAULT);
        }
    }
}



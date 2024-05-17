package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class PrimFrame extends JFrame {
    JPanel p = new JPanel();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // Tamanho inteiro da tela
    public final int LARG_DEFAULT = d.width;
    public final int ALT_DEFAULT = d.height;
    public final int CELULA_SIZE = 30; // Tamanho de cada celula do tabuleiro
    public final int NUMERO_COLUNAS = 16;
    public final int NUMERO_LINHAS = 16;

    public PrimFrame(String s) {
        super(s);

        // Calculando o tamanho da janela com base no tamanho do tabuleiro
        //LARG_DEFAULT = CELULA_SIZE * (NUMERO_COLUNAS + 2) ;
        //ALT_DEFAULT = CELULA_SIZE * (NUMERO_LINHAS + 2);

        //setSize(LARG_DEFAULT, ALT_DEFAULT);
        setSize(d);
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
            int right_x = LARG_DEFAULT/2; // para o tabuleiro ficar na direita da tela
            int down_y = ALT_DEFAULT/8; // como foi especificado pelo ivan
            
            // Desenhar o tabuleiro
            for (int row = 0; row < NUMERO_LINHAS; row++) {
                for (int col = 0; col < NUMERO_COLUNAS; col++) {
                    // Calcular a posição da célula
                    int x = right_x + (col * CELULA_SIZE);
                    int y = down_y + (row * CELULA_SIZE);

                    // Desenhar o retângulo representando a célula
                    if (row != 0 && col != 0){
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.WHITE);
                        g2d.fill(cell);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(cell);
                    }

                    // Desenhar as letras da primeira coluna
                    else if (col == 0 && row > 0) {
                        String letraLinha = String.valueOf((char) ('A' + row - 1));
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(letraLinha);
                        g2d.drawString(letraLinha, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);// centralizando a escrita na celula 
                    }

                    // Desenhar os números da primeira linha
                    else if (row == 0 && col > 0) {
                        String numeroColuna = String.valueOf(col);
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(numeroColuna);
                        g2d.drawString(numeroColuna, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);// centralizando a escrita na celula 
                    }
                }
            }
            
            // Desenhar os navios
            for (int i = 0; i < 14; i++) {
            	for (int j = 0; j < 19; j++){
            		int x = 30 + (j * CELULA_SIZE); // +40 para nao ficar colado na esquerda
                    int y = down_y + 30 + (i * CELULA_SIZE); // +20 para ficar alinhado com o tabuleiro

                    if (i == 0 && (j == 1 || j == 5 || j == 9 || j == 13 || j == 17)) {
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.green.darker());
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
                    }
                    
                    if (i == 1 && j % 2 == 0) {
                		Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.green.darker());
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
                	}
                    
                    if (i == 4 && j % 2 == 0 && j < 7) {
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.blue);
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
                    }
                    
                    if (i == 7 && (j == 0 || j == 1 || j == 3 || j == 4 || j == 6 || j == 7)) {
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.yellow);
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
                    }
                    
                    if (i == 10 && j != 4 && j < 9) {
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.orange);
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
                    }
                    
                    if (i == 13 && j < 5) {
                    	Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.orange.darker().darker().darker());
                        g2d.fill(cell);
                        g2d.setColor(Color.black);
                        g2d.draw(cell);
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



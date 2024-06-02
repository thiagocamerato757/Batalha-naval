package View;

import javax.swing.*;

import Model.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class AttackFrame extends JFrame {
    private List<Navio> opponentShips1;
    private List<Navio> opponentShips2;
    private List<Point>tiros1;
    private List<Point>tiros2;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public final int LARG_DEFAULT = d.width;
    public final int ALT_DEFAULT = d.height;
    public final int CELULA_SIZE = 30;
    public final int NUMERO_COLUNAS = 16;
    public final int NUMERO_LINHAS = 16;
    AttackPanel panel;

    public AttackFrame(String s) {
        super(s);
        setSize(d);
        panel = new AttackPanel();
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class AttackPanel extends JPanel {
        int left_x = LARG_DEFAULT / 2;
        int down_y = ALT_DEFAULT / 7;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawBoard(g2d, left_x, down_y);
            int secondBoardXOffset = left_x - (NUMERO_COLUNAS + 2) * CELULA_SIZE;
            drawBoard(g2d, secondBoardXOffset, down_y);
        }

        private void drawBoard(Graphics2D g2d, int startX, int startY) {
            for (int row = 0; row < NUMERO_LINHAS; row++) {
                for (int col = 0; col < NUMERO_COLUNAS; col++) {
                    int x = startX + (col * CELULA_SIZE);
                    int y = startY + (row * CELULA_SIZE);

                    if (row != 0 && col != 0) {
                        Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        g2d.setColor(Color.WHITE);
                        g2d.fill(cell);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(cell);
                    } else if (col == 0 && row > 0) {
                        String letraLinha = String.valueOf((char) ('A' + row - 1));
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(letraLinha);
                        g2d.drawString(letraLinha, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);
                    } else if (row == 0 && col > 0) {
                        String numeroColuna = String.valueOf(col);
                        FontMetrics fm = g2d.getFontMetrics();
                        int width = fm.stringWidth(numeroColuna);
                        g2d.drawString(numeroColuna, x + CELULA_SIZE / 2 - width / 2, y + CELULA_SIZE / 2 + fm.getHeight() / 2);
                    }
                }
            }
        }
    }
    
    protected List<Navio> getOpponentShips1() {
		return opponentShips1;
	}

	protected void setOpponentShips1(List<Navio> opponentShips1) {
		this.opponentShips1 = opponentShips1;
	}

	protected List<Navio> getOpponentShips2() {
		return opponentShips2;
	}

	protected void setOpponentShips2(List<Navio> opponentShips2) {
		this.opponentShips2 = opponentShips2;
	}

	public static void main(String[] args) {
        AttackFrame attackFrame = new AttackFrame("Batalha naval - Ataque");
        attackFrame.setVisible(true);
    }
}

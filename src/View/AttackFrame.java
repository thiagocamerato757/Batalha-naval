package View;

import javax.swing.*;
import Model.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttackFrame extends JFrame {
    private List<Navio> opponentShips1;
    private List<Navio> opponentShips2;
    private Map<Point, Color> tiros1 = new HashMap<>();
    private Map<Point, Color> tiros2 = new HashMap<>();
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public final int LARG_DEFAULT = d.width;
    public final int ALT_DEFAULT = d.height;
    public final int CELULA_SIZE = 30;
    public final int NUMERO_COLUNAS = 16;
    public final int NUMERO_LINHAS = 16;
    AttackPanel panel;
    private Point p;
    private passaInfoATK vezJogador = new passaInfoATK();

    public AttackFrame(String s) {
        super(s);
        setSize(d);
        panel = new AttackPanel();
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                p = e.getPoint();

                int col = ((p.x - panel.right_x) / CELULA_SIZE) - 1;
                int row = ((p.y - panel.down_y) / CELULA_SIZE) - 1;
                Point coordTabu = new Point(col, row);
                if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
                    if (vezJogador.isVez1()) {
                        Color shotColor = Color.CYAN;
                        for (Navio ship : opponentShips2) {
                            if (ship.getCoordenadas().contains(coordTabu)) {
                            	shotColor = Color.gray;
                            	if (isShipSunk(ship.getCoordenadas(), tiros1)) {
                            		shotColor = Color.BLACK;
                            	}
                                System.out.println("Você atingiu um " + ship.getTipo());
                                break;
                            }
                        }
                        tiros1.put(coordTabu, shotColor);
                    } else {
                        Color shotColor = Color.CYAN;
                        for (Navio ship : opponentShips1) {
                            if (ship.getCoordenadas().contains(coordTabu)) {
                                shotColor = Color.BLACK;
                                System.out.println("Você atingiu um " + ship.getTipo());
                                break;
                            }
                        }
                        tiros2.put(coordTabu, shotColor);
                    }
                    panel.repaint();
                }
            }
        });
    }
    
    private boolean isShipSunk(List<Point> shipCoords, Map<Point, Color> shots) {
        for (Point coord : shipCoords) {
            if (!shots.containsKey(coord)) {
                return false; // Se algum ponto do navio não foi atingido, o navio não foi afundado
            }
        }
        return true; // Se todos os pontos do navio foram atingidos, o navio foi afundado
    }

    class AttackPanel extends JPanel {
        int right_x = LARG_DEFAULT / 2;
        int down_y = ALT_DEFAULT / 7;
        int secondBoardXOffset = right_x - (NUMERO_COLUNAS + 2) * CELULA_SIZE;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawBoard(g2d, right_x, down_y);
            drawBoard(g2d, secondBoardXOffset, down_y);
            drawShots(g2d, right_x, down_y, tiros1);
            drawShots(g2d, secondBoardXOffset, down_y, tiros2);
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

        private void drawShots(Graphics2D g2d, int startX, int startY, Map<Point, Color> shots) {
            for (Map.Entry<Point, Color> entry : shots.entrySet()) {
                Point shot = entry.getKey();
                Color color = entry.getValue();
                int x = startX + (shot.x * CELULA_SIZE) + CELULA_SIZE;
                int y = startY + (shot.y * CELULA_SIZE) + CELULA_SIZE;
                Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);

                g2d.setColor(color);
                g2d.fill(cell);
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
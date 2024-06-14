package View;

import javax.swing.*;
import Model.*;
import View.PrimFrame.PrimPanel;
import Model.BatalhaNaval;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private BatalhaNavalFacade bat = new BatalhaNavalFacade();
    private int navios_restantes = bat.getNaviosRestantes();
    AttackPanel panel;
    private Point p;
    private boolean vezJogador = true, blocked1 = true, blocked2 = true;
    Color shotColor = null;
    int max_tiros = 1;
    String nomeJogador1, nomeJogador2;
    private JLabel statusLabel, turnLabel, blockedLabel;
    JButton unblock, start, hide;
    
    protected boolean passaVez() {
        return !vezJogador;
    }

    public void setNomeJogador1(String nomeJogador1) {
        this.nomeJogador1 = nomeJogador1;
    }
    
    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }
    
    public AttackFrame(String s) {
        super(s);
        setSize(d);
        panel = new AttackPanel();
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	if (blocked1 == false || blocked2 == false) {
	                p = e.getPoint();
	                if (vezJogador) {
	                    handleShot(tiros1, opponentShips2, panel.right_x, panel.down_y, p);
	                    panel.repaint();
	                    if (playerWin(opponentShips2, 1)) {
	                    	JOptionPane.showMessageDialog(AttackFrame.this, nomeJogador1 + " ganhou o jogo!!");
	                    }
	                } else {
	                    handleShot(tiros2, opponentShips1, panel.secondBoardXOffset, panel.down_y, p);
	                    panel.repaint();
	                    if (playerWin(opponentShips1, 1)) {
	                    	JOptionPane.showMessageDialog(AttackFrame.this, nomeJogador2 + " ganhou o jogo!!");
	                    }
	                }
            	}
            }
        });
    }

    private void handleShot(Map<Point, Color> tiros, List<Navio> opponentShips, int boardXOffset, int boardYOffset, Point p) {
        statusLabel.setVisible(true);
    	int col = ((p.x - boardXOffset) / CELULA_SIZE) - 1;
        int row = ((p.y - boardYOffset) / CELULA_SIZE) - 1;
        Point coordTabu = new Point(col, row);
        if (col >= 0 && col < NUMERO_COLUNAS - 1 && row >= 0 && row < NUMERO_LINHAS - 1 && tiros.get(coordTabu) == null && max_tiros<=3) {
        	if (max_tiros == 3) {
        		hide.setVisible(true);
            }
            boolean hit = false;
            for (Navio ship : opponentShips) {
                if (ship.getCoordenadas().contains(coordTabu)) {
                    tiros.put(coordTabu, Color.GRAY);
                    if (isShipSunk(ship.getCoordenadas(), tiros)) {
                    	ship.setSunk(true);
                        for (Point part : ship.getCoordenadas()) {
                            tiros.put(part, Color.BLACK);
                        }
                        statusLabel.setText("Você afundou um " + ship.getTipo());
                    } else {
                        statusLabel.setText("Você atingiu um " + ship.getTipo());
                    }
                    hit = true;
                    break;
                }
            }
            if (!hit) {
                tiros.put(coordTabu, Color.cyan.darker());
                statusLabel.setText("Você atingiu a água");
            }
            max_tiros++;
        }
    }

    private boolean isShipSunk(List<Point> shipCoords, Map<Point, Color> shots) {
        for (Point coord : shipCoords) {
            if (!shots.containsKey(coord) || shots.get(coord) != Color.GRAY) {
                return false;
            }
        }
        return true;
    }
    
    private boolean playerWin(List<Navio> opponentShip, int num_navios) {
    	for (Navio ship : opponentShip) {
    		if (ship.isSunk()) {
    			num_navios--;
    		}
    	}
    	if (num_navios == 0) {
    		hide.setVisible(false);
    		return true;
    	}
    	return false;
    }

    class AttackPanel extends JPanel {
        int right_x = LARG_DEFAULT / 2;
        int down_y = ALT_DEFAULT / 7;
        int secondBoardXOffset = right_x - (NUMERO_COLUNAS + 2) * CELULA_SIZE;
        
        public AttackPanel() {
        	blockedLabel = new JLabel("aperta o botão para desbloquear sua visão");
        	blockedLabel.setBounds(LARG_DEFAULT / 2 - 150, ALT_DEFAULT - 300, 600, 20);
        	blockedLabel.setFont(new Font("Arial", Font.BOLD, 18));
        	blockedLabel.setVisible(false);
        	add(blockedLabel);
        	unblock = new JButton("Desbloquear visão");
            setLayout(null);
            unblock.setBounds(LARG_DEFAULT / 2 - 60, ALT_DEFAULT - 180, 150, 30);
            add(unblock);
            unblock.setVisible(false);
            unblock.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    vezJogador = passaVez();
                    if (vezJogador) {
                    	blocked1 = false;
                    	blocked2 = true;
                    }
                    else {
                    	blocked1 = true;
                    	blocked2 = false;
                    }
                    max_tiros = 1;
                    unblock.setVisible(false);
                    blockedLabel.setVisible(false);
                    panel.repaint();
                }
            });
            start = new JButton("Começar jogo!");
            setLayout(null);
            start.setBounds(LARG_DEFAULT / 2 - 60, ALT_DEFAULT - 180, 150, 30);
            add(start);
            start.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		start.setVisible(false);
            		blocked1 = false;
            		panel.repaint();
            	}
            });

            hide = new JButton("Próximo Jogador");
            setLayout(null);
            hide.setBounds(LARG_DEFAULT / 2 - 60, ALT_DEFAULT - 180, 150, 30);
            hide.setVisible(false);
            add(hide);
            hide.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		blocked1 = true;
            		blocked2 = true;
            		hide.setVisible(false);
            		unblock.setVisible(true);
            		blockedLabel.setVisible(true);
            		statusLabel.setVisible(false);
            		panel.repaint();
            	}
            });
           
            statusLabel = new JLabel("");
            statusLabel.setBounds(LARG_DEFAULT / 2 - 120, ALT_DEFAULT - 300, 300, 20);
            statusLabel.setForeground(Color.RED);
            statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
            add(statusLabel);
            
            turnLabel = new JLabel("");
            turnLabel.setBounds(LARG_DEFAULT / 2 - 120, 100, 400, 20);
            turnLabel.setFont(new Font("Arial", Font.BOLD, 18));
            add(turnLabel);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawBoard(g2d, right_x, down_y, blocked1);
            drawBoard(g2d, secondBoardXOffset, down_y, blocked2);
            if (vezJogador) {
            	turnLabel.setText("É o turno de: " + nomeJogador1); //Lembrar de adicionar o nome do jogador
            	drawShots(g2d, right_x, down_y, tiros1, blocked1);
            }
            else {
            	turnLabel.setText("É o turno de: " + nomeJogador2);
            	drawShots(g2d, secondBoardXOffset, down_y, tiros2, blocked2);
            }
        }

        private void drawBoard(Graphics2D g2d, int startX, int startY, boolean blocked) {
            for (int row = 0; row < NUMERO_LINHAS; row++) {
                for (int col = 0; col < NUMERO_COLUNAS; col++) {
                    int x = startX + (col * CELULA_SIZE);
                    int y = startY + (row * CELULA_SIZE);

                    if (row != 0 && col != 0) {
                        Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                        if (blocked) {
                        	g2d.setColor(Color.cyan.darker());
                        }
                        else {
                        	g2d.setColor(Color.white);
                        }
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

        private void drawShots(Graphics2D g2d, int startX, int startY, Map<Point, Color> shots, boolean blocked) {
            for (Map.Entry<Point, Color> entry : shots.entrySet()) {
                Point shot = entry.getKey();
                Color color = entry.getValue();
                int x = startX + (shot.x * CELULA_SIZE) + CELULA_SIZE;
                int y = startY + (shot.y * CELULA_SIZE) + CELULA_SIZE;
                Rectangle2D.Double cell = new Rectangle2D.Double(x, y, CELULA_SIZE, CELULA_SIZE);
                if (!blocked) {
	                g2d.setColor(color);
	                g2d.fill(cell);
	                g2d.setColor(Color.BLACK);
	                g2d.draw(cell);
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
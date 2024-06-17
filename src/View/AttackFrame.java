package View;

import javax.swing.*;

import Controller.Jogo;
import Model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
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
    private int navios_restantes1 = bat.getNaviosRestantes();
    private int navios_restantes2 = bat.getNaviosRestantes();
    AttackPanel panel;
    private Point p;
    private boolean vezJogador = true, blocked1 = true, blocked2 = true;
    Color shotColor = null;
    int max_tiros = 0;
    private String nomeJogador1, nomeJogador2;
    private JLabel statusLabel, turnLabel, blockedLabel;
    JButton unblock, start, hide;
    SalvarArquivo saveFile;
    
    protected boolean passaVez() {
        return !vezJogador;
    }
    
    public void setVezJogador(boolean vezJogador) {
    	this.vezJogador = vezJogador;
    }

    public void setSaveFile(SalvarArquivo saveFile) {
    	this.saveFile = saveFile;
    }
    public void setNomeJogador1(String nomeJogador1) {
        this.nomeJogador1 = nomeJogador1;
    }
    
    public void setNomeJogador2(String nomeJogador2) {
        this.nomeJogador2 = nomeJogador2;
    }
    
    public Map<Point, Color> getTiros1() {
		return tiros1;
	}

	public void setTiros1(Map<Point, Color> tiros1) {
		this.tiros1 = tiros1;
	}

	public Map<Point, Color> getTiros2() {
		return tiros2;
	}

	public void setTiros2(Map<Point, Color> tiros2) {
		this.tiros2 = tiros2;
	}
	
	public void setNavios_restantes1(int navios_restantes1) {
		this.navios_restantes1 = navios_restantes1;
	}

	public void setNavios_restantes2(int navios_restantes2) {
		this.navios_restantes2 = navios_restantes2;
	}
	
	public void setMax_tiros(int max_tiros) {
		this.max_tiros = max_tiros;
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
	                    handleShot(tiros1, opponentShips2, panel.right_x, panel.down_y, p, vezJogador);
	                    panel.repaint();
	                    if (playerWin(navios_restantes1)) {
	                    	JOptionPane.showMessageDialog(AttackFrame.this, nomeJogador1 + " ganhou o jogo!!");
	                    	finishGame();
	                    }
	                } else {
	                    handleShot(tiros2, opponentShips1, panel.secondBoardXOffset, panel.down_y, p, vezJogador);
	                    panel.repaint();
	                    if (playerWin(navios_restantes2)) {
	                    	JOptionPane.showMessageDialog(AttackFrame.this, nomeJogador2 + " ganhou o jogo!!");
	                    	finishGame();
	                    }
	                }
            	}
            }
        });
    }

	private void handleShot(Map<Point, Color> tiros, List<Navio> opponentShips, int boardXOffset, int boardYOffset, Point p, boolean vezJogador) {
	    statusLabel.setVisible(true);
	    int col = ((p.x - boardXOffset) / CELULA_SIZE) - 1;
	    int row = ((p.y - boardYOffset) / CELULA_SIZE) - 1;
	    Point coordTabu = new Point(col, row);
	    if (col >= 0 && col < NUMERO_COLUNAS - 1 && row >= 0 && row < NUMERO_LINHAS - 1 && tiros.get(coordTabu) == null && max_tiros <= 2) {
	        if (max_tiros == 2) {
	            hide.setVisible(true);
	        }
	        boolean hit = false;
	        for (Navio ship : opponentShips) {
	        	System.out.println("ship" + ship.getCoordenadas());
	        	System.out.println("tiros" + tiros);
	            if (ship.getCoordenadas().contains(coordTabu)) {
	                tiros.put(coordTabu, Color.GRAY);
	                if (isShipSunk(ship.getCoordenadas(), tiros)) {
	                    ship.setSunk(true);
	                    if (vezJogador) {
	                    	navios_restantes1--;
	                    }
	                    else {
	                    	navios_restantes2--;
	                    }
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
            if (!shots.containsKey(coord)) {
                return false;
            }
            
        }
        return true;
    }
    
    private boolean playerWin(int num_navios) {
    	if (num_navios == 0) {
    		hide.setVisible(false);
    		return true;
    	}
    	return false;
    }
    
    private void finishGame() {
    	int i = JOptionPane.showConfirmDialog(AttackFrame.this, "Deseja iniciar um novo jogo?", "Batalha Naval", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
    	if (i == JOptionPane.YES_OPTION) {
    		tiros1.clear();
    		tiros2.clear();
    		opponentShips1.clear();
    		opponentShips2.clear();
    		navios_restantes1 = 15;
    		navios_restantes2 = 15;
    		max_tiros = 0;
    		statusLabel.setVisible(false);
    		AttackFrame.this.dispose();
    		Jogo.startNewGame();
    	}
    	else {
    		AttackFrame.this.dispose();
    	}
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
                    if (vezJogador) {
                    	blocked1 = false;
                    	blocked2 = true;
                    }
                    else {
                    	blocked1 = true;
                    	blocked2 = false;
                    }
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
            		if (max_tiros == 3) {
            			hide.setVisible(true);
            		}
            		if (vezJogador) {
            			blocked1 = false;
                    	blocked2 = true;
            		}
            		else {
            			blocked1 = true;
                    	blocked2 = false;
            		}
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
            		vezJogador = passaVez();
            		
            		max_tiros = 0;
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
            
            JButton save = new JButton("Salvar e fechar");
            setLayout(null);
            save.setBounds(LARG_DEFAULT - 200, ALT_DEFAULT - 180, 150, 30);
            add(save);
            save.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		try {
            			saveFile.setShots1(tiros1);
            			saveFile.setShots2(tiros2);
            			saveFile.setNavios_restantes1(navios_restantes1);
            			saveFile.setNavios_restantes2(navios_restantes2);
            			saveFile.setMaxTiros(max_tiros);
            			saveFile.setVezJogador(vezJogador);
            			JFileChooser fileChooser = new JFileChooser();
            			int returnValue = fileChooser.showOpenDialog(AttackFrame.this);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            saveFile.writeFile(selectedFile);
                        }
                        if (returnValue == JFileChooser.CANCEL_OPTION) {
                        	return;
                        }
					} catch (IOException e1) {
						System.out.println("Não foi possível salvar o jogo");
					}
            		AttackFrame.this.dispose();
            		AtkSingleton.getInstance().resetInstance();
            	}
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            drawBoard(g2d, right_x, down_y, blocked1);
            drawBoard(g2d, secondBoardXOffset, down_y, blocked2);
            if (vezJogador) {
            	turnLabel.setText("É o turno de: " + AttackFrame.this.nomeJogador1);
            	drawShots(g2d, right_x, down_y, tiros1, blocked1);
            }
            else {
            	turnLabel.setText("É o turno de: " + AttackFrame.this.nomeJogador2);
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

    protected void setOpponentShips2(List<Navio> opponentShips2) {
        this.opponentShips2 = opponentShips2;
    }

    protected List<Navio> getOpponentShips2() {
        return opponentShips2;
    }

    protected void setOpponentShips1(List<Navio> opponentShips1) {
        this.opponentShips1 = opponentShips1;
    }

    public static void main(String[] args) {
        AttackFrame attackFrame = new AttackFrame("Batalha naval - Ataque");
        attackFrame.setVisible(true);
    }
}
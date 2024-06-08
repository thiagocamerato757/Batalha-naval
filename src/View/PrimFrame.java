package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import Model.Navio;
import Model.BatalhaNaval;
import java.util.List;

public class PrimFrame extends JFrame {
    private ArrayList<Navio> ships = new ArrayList<>();
    private List<Point>cells = new ArrayList<>();
    private Navio selectedShip = null;
    private Navio pendingShip = null;
    private Color originalColor = null;
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public final int LARG_DEFAULT = d.width;
    public final int ALT_DEFAULT = d.height;
    public final int CELULA_SIZE = 30;
    public final int NUMERO_COLUNAS = 16;
    public final int NUMERO_LINHAS = 16;
    String nomeJogador;
    private Point p;
    private BatalhaNaval tabuleiro = new BatalhaNaval();
    
    PrimPanel panel;

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }
    
    public PrimFrame(String s) {
        super(s);
        setSize(d);
        panel = new PrimPanel();
        getContentPane().add(panel);
        Notificador notify = new Notificador();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                p = e.getPoint();

                if (pendingShip == null && selectedShip == null) {
                    for (Navio ship : ships) {
                        if (!ship.isConfirmed() && ship.getShape().contains(p)) {
                            selectedShip = ship;
                            originalColor = ship.getCor();
                            selectedShip.setCor(getLighterColor(originalColor));
                            panel.repaint();
                            return;
                        }
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    if (selectedShip != null && !selectedShip.isConfirmed()) {
                        selectedShip.rotate();
                        int col = (p.x - panel.right_x) / CELULA_SIZE;
                        int row = (p.y - panel.down_y) / CELULA_SIZE;
                        if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
                            double newX = panel.right_x + col * CELULA_SIZE;
                            double newY = panel.down_y + row * CELULA_SIZE;
                            pendingShip = selectedShip;
                            pendingShip.setPosition(newX, newY);
                        }
                    }
                }

                if (selectedShip != null) {
                    int col = (p.x - panel.right_x) / CELULA_SIZE;
                    int row = (p.y - panel.down_y) / CELULA_SIZE;

                    if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
                        double newX = panel.right_x + col * CELULA_SIZE;
                        double newY = panel.down_y + row * CELULA_SIZE;
                        pendingShip = selectedShip;
                        pendingShip.setPosition(newX, newY);
                        if (!isOverlapping(newX, newY, selectedShip) && isValidPosition(col, row, selectedShip)) {
                            pendingShip.setCor(getLighterColor(originalColor));
                        } else {
                            pendingShip.setCor(Color.RED);
                        }
                    }
                }
                panel.repaint();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (pendingShip != null && pendingShip.getCor() != Color.RED) {
                        pendingShip.setConfirmed(true);
                        int col = (p.x - panel.right_x) / CELULA_SIZE;
                        int row = (p.y - panel.down_y) / CELULA_SIZE;
                        double newX = panel.right_x + col * CELULA_SIZE;
                        double newY = panel.down_y + row * CELULA_SIZE;
                        cells = getShipCells(newX, newY, pendingShip);
                        Coordenadas coord = new Coordenadas(cells, panel.right_x, panel.down_y);
                        coord.coordenadaGraficaParaIndices(pendingShip); // envia as coords matriciais para a embarcacao
                        notify.NotificaObserverTabu(tabuleiro, pendingShip);
                        cells.clear();
                        tabuleiro.addNavios(pendingShip);
                        pendingShip.setCor(originalColor); // Restore the original color
                        selectedShip = null;
                        pendingShip = null;
                        panel.repaint();
                    } else if (selectedShip != null && pendingShip == null) {
                        selectedShip.setCor(originalColor); // Restore the original color
                        panel.repaint();
                        selectedShip = null;
                    } else {
                        selectedShip.restoreOriginalPosition();
                        selectedShip.setCor(originalColor); // Restore the original color
                        panel.repaint();
                        selectedShip = null;
                        pendingShip = null;
                    }
                }
                //panel.updateButtonState();
            }
        });

        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    private Color getLighterColor(Color color) {
        int r = color.getRed() + (255 - color.getRed()) / 2;
        int g = color.getGreen() + (255 - color.getGreen()) / 2;
        int b = color.getBlue() + (255 - color.getBlue()) / 2;
        return new Color(r, g, b);
    }
    
    private List<Point> getShipCells(double x, double y, Navio ship) {
        int tamanho = ship.getTamanho();
        int rotation = ship.getRotationCount();
        List<Point> cells = new ArrayList<>();

        if (tamanho == 3 && ship.getShape() instanceof Path2D.Double) {
            // Hidroavião
            switch (rotation) {
                case 0: // Normal
                    cells.add(new Point((int) x, (int) y));
                    cells.add(new Point((int) x + CELULA_SIZE, (int) y - CELULA_SIZE));
                    cells.add(new Point((int) (x + 2 * CELULA_SIZE), (int) y));
                    break;
                case 1: // 90 degrees
                    cells.add(new Point((int) x, (int) y));
                    cells.add(new Point((int) x + CELULA_SIZE, (int) y + CELULA_SIZE));
                    cells.add(new Point((int) x, (int) (y + 2 * CELULA_SIZE)));
                    break;
                case 2: // 180 degrees
                    cells.add(new Point((int) x, (int) y));
                    cells.add(new Point((int) x - CELULA_SIZE, (int) y + CELULA_SIZE));
                    cells.add(new Point((int) (x - 2 * CELULA_SIZE), (int) y));
                    break;
                case 3: // 270 degrees
                    cells.add(new Point((int) x, (int) y));
                    cells.add(new Point((int) x - CELULA_SIZE, (int) y - CELULA_SIZE));
                    cells.add(new Point((int) x, (int) (y - 2 * CELULA_SIZE)));
                    break;
            }
        } else {
            // Navios normais
            for (int i = 0; i < tamanho; i++) {
                switch (rotation) {
                    case 0: // Normal
                        cells.add(new Point((int) x, (int) (y + i * CELULA_SIZE)));
                        break;
                    case 1: // 90 degrees
                        cells.add(new Point((int) (x + i * CELULA_SIZE), (int) y));
                        break;
                    case 2: // 180 degrees
                        cells.add(new Point((int) x, (int) (y - i * CELULA_SIZE)));
                        break;
                    case 3: // 270 degrees
                        cells.add(new Point((int) (x - i * CELULA_SIZE), (int) y));
                        break;
                }
            }
        }

        return cells;
    
    }
    
    private boolean isValidPosition(int col, int row, Navio ship) {
        if (col <= 0 || row <= 0 || col >= NUMERO_COLUNAS || row >= NUMERO_LINHAS) {
            return false;
        }
        
        if (ship.getRotationCount() == 0) {
            // Verifica se o navio vertical passa dos limites do tabuleiro
        	if (ship.getTamanho() == 2 ) {
            	if (row + 1 >= NUMERO_LINHAS) {
            		return false;
            	}
            }
        	if (ship.getTamanho() == 3 ) {
            	if (col + 2 >= NUMERO_COLUNAS  || row - 1 <= 0) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 4) {
            	if (row + 3 >= NUMERO_LINHAS) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 5) {
            	if (row + 4 >= NUMERO_LINHAS) {
            		return false;
            	}
            }
        } else if (ship.getRotationCount() == 1) {
            // Verifica se o navio horizontal passa dos limites do tabuleiro
        	if (ship.getTamanho() == 2 ) {
            	if (col + 1 >= NUMERO_COLUNAS) {
            		return false;
            	}
            }
        	if (ship.getTamanho() == 3) {
        		if (col + 1 >= NUMERO_COLUNAS || row + 2 >= NUMERO_LINHAS) {
            		return false;
            	}
        	}
            if (ship.getTamanho() == 4) {
            	if (col + 3 >= NUMERO_COLUNAS) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 5) {
            	if (col + 4 >= NUMERO_COLUNAS) {
            		return false;
            	}
            }
        } else if (ship.getRotationCount() == 2) {
        	if (ship.getTamanho() == 2 ) {
            	if (row - 1 <= 0) {
            		return false;
            	}
            }
        	if (ship.getTamanho() == 3 ) {
            	if (row + 1 >= NUMERO_LINHAS || col - 2 <= 0) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 4) {
            	if (row - 3 <= 0) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 5) {
            	if (row - 4 <= 0) {
            		return false;
            	}
            }
        }
        else {
        	if (ship.getTamanho() == 2 ) {
            	if (col - 1 <= 0) {
            		return false;
            	}
            }
        	if (ship.getTamanho() == 3 ) {
            	if (col - 1 <= 0 || row - 2 <= 0) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 4) {
            	if (col - 3 <= 0) {
            		return false;
            	}
            }
            if (ship.getTamanho() == 5) {
            	if (col - 4 <= 0) {
            		return false;
            	}
            }
        }

        for (Navio otherShip : ships) {
            if (otherShip != ship) {
                if (shapesOverlap(ship.getShape(), otherShip.getShape()) || shapesAdjacent(ship, otherShip)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    private boolean shapesOverlap(Shape s1, Shape s2) {
        return s1.getBounds2D().intersects(s2.getBounds2D());
    }

    private boolean shapesAdjacent(Navio s1, Navio s2) {
    	Shape shapeS1 = s1.getShape();
    	Shape shapeS2 = s2.getShape();
    	Rectangle2D bounds1 = shapeS1.getBounds2D();
        Rectangle2D bounds2 = shapeS2.getBounds2D();
        
        if (s1.getTamanho() == 3) {
        	Rectangle2D quadrado1 = new Rectangle2D.Double(
        			bounds1.getX() - CELULA_SIZE,
                    bounds1.getY(),
                    bounds1.getWidth(),
                    bounds1.getHeight() + CELULA_SIZE
            );
        	
        	Rectangle2D quadrado2 = new Rectangle2D.Double(
        			bounds1.getX(),
                    bounds1.getY() - CELULA_SIZE,
                    bounds1.getWidth(),
                    bounds1.getHeight() + CELULA_SIZE
            );
        	Rectangle2D quadrado3 = new Rectangle2D.Double(
        			bounds1.getX() + CELULA_SIZE,
                    bounds1.getY(),
                    bounds1.getWidth(),
                    bounds1.getHeight() + CELULA_SIZE
            );
        }
        if (s2.getTamanho() == 3) {
        	Rectangle2D quadrado1s2 = new Rectangle2D.Double(
        			bounds2.getX() - CELULA_SIZE,
                    bounds2.getY(),
                    bounds2.getWidth(),
                    bounds2.getHeight() + CELULA_SIZE
            );
        	
        	Rectangle2D quadrado2s2 = new Rectangle2D.Double(
        			bounds2.getX(),
                    bounds2.getY() - CELULA_SIZE,
                    bounds2.getWidth(),
                    bounds2.getHeight() + CELULA_SIZE
            );
        	Rectangle2D quadrado3s2 = new Rectangle2D.Double(
        			bounds2.getX() + CELULA_SIZE,
                    bounds2.getY(),
                    bounds2.getWidth(),
                    bounds2.getHeight() + CELULA_SIZE
            );
        }

	    bounds1 = new Rectangle2D.Double(
	        bounds1.getX() - CELULA_SIZE,
	        bounds1.getY() - CELULA_SIZE,
	        bounds1.getWidth() + 2 * CELULA_SIZE,
	        bounds1.getHeight() + 2 * CELULA_SIZE
	    );
        return bounds1.intersects(bounds2);
    }

    private boolean isOverlapping(double x, double y, Navio ship) {
        List<Point> coord = getShipCells(x, y, ship);
		List<Point> otherCoord = null;
		Coordenadas coordenadas;
		ArrayList<Navio> confirmedShips = tabuleiro.getNavios();
		for (Navio otherShip : confirmedShips) {
            if (otherShip != ship) {
            	coordenadas = new Coordenadas(otherShip.getCoordenadas(), panel.right_x, panel.down_y);
            	otherCoord = coordenadas.coordenadaIndicesParaGrafica(otherShip);
            	for (int i = 0; i < otherCoord.size(); i++) {
            		for (int j = 0; j < coord.size(); j++) {
            			if(otherCoord.get(i).equals(coord.get(j))) {
            				return true;
            			}
            		}
            	}
            }
		}
        return false;
    }
    
    private boolean areAllShipsConfirmed() {
        for (Navio ship : ships) {
            if (!ship.isConfirmed()) {
            	return false;
            }
        }
        return true;
    }
    	
    
    protected ArrayList<Navio> getShips() {
		return ships;
	}

	class PrimPanel extends JPanel {
		AtkSingleton atk;
        JButton b1 = new JButton("Confirmar");
        TrocaContexto troca = new TrocaContexto();
        passaInfoATK atq = new passaInfoATK();
        int right_x = LARG_DEFAULT / 2;
        double X = LARG_DEFAULT / 7;
        double Y = ALT_DEFAULT / 7;
        int down_y = ALT_DEFAULT / 7;

        public PrimPanel() {
        	atk = AtkSingleton.getInstance();
            initShips();
            setLayout(null);
            //b1.setEnabled(false);
            add(b1);
            b1.setBounds(LARG_DEFAULT / 2, ALT_DEFAULT - 180, 80, 30);
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    troca.atualizaEstadoTab(tabuleiro, true);
                    atq.passaInfo(PrimFrame.this,atk.getTabuleiro()); //salva a lista de navios para a tela de ataque
                    PrimFrame.this.dispose();
                    if(troca.getContProntos() == 2) {
                        troca.trocaPraAtaque(); //troca para tela de ataque
                    }
               }
            });
        }
        
        private void updateButtonState() {
            b1.setEnabled(areAllShipsConfirmed());
        }

        private void initShips() {
            for (int i = 0; i < 4; i++) {
                ships.add(new Navio(1)); // Submarino
            }
            for (int i = 0; i < 3; i++) {
                ships.add(new Navio(2)); // Destroyer
            }
            for (int i = 0; i < 5; i++) {
                ships.add(new Navio(3)); // Hidroavião
            }
            for (int i = 0; i < 2; i++) {
                ships.add(new Navio(4)); // Cruzador
            }
            for (int i = 0; i < 1; i++) {
                ships.add(new Navio(5)); // Couraçado
            }
            int espaco = 60;
            int total = 1;

            int j = 0;
            for (int i = 0; i < ships.size(); i++) {
                Navio ship = ships.get(i);
                if (ship.getTamanho() == 3 && j != 0) { // para abrir
                    X += espaco + (CELULA_SIZE * 2);
                } else {
                    X = 30 + (j * CELULA_SIZE * 2);
                }
                ship.setPosition(X, Y + 30);
                ship.saveOriginalPosition(X, Y + 30);
                j++;
                if (total == 4 || total == 7 || total == 12 || total == 14 || total == 15) {
                    if (total != 15) {
                        Y += 40 * ships.get(i + 1).getTamanho();
                        X = 30;
                        j = 0;
                    }
                }
                total++;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2d.drawString(nomeJogador + " posicione suas armas", (LARG_DEFAULT / 2) - 50, ALT_DEFAULT - 200);

            b1.setBounds(LARG_DEFAULT / 2, ALT_DEFAULT - 180, 100, 30);
            add(b1);
            

            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            for (int row = 0; row < NUMERO_LINHAS; row++) {
                for (int col = 0; col < NUMERO_COLUNAS; col++) {
                    int x = right_x + (col * CELULA_SIZE);
                    int y = down_y + (row * CELULA_SIZE);

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

            for (Navio ship : ships) {
            	g2d.setColor(ship.getCor());
                g2d.fill(ship.getShape());
                g2d.setColor(Color.BLACK);
                g2d.draw(ship.getShape());
            }
        }
    }

    public static void main(String[] args) {
        PrimFrame f = new PrimFrame("Batalha naval");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setNomeJogador("Jogador 1");
    }
}
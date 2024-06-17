package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import Model.Navio;
import Model.SalvarArquivo;
import Model.BatalhaNavalFacade;

import java.util.List;

public class PrimFrame extends JFrame {
	private ArrayList<Navio> ships = new ArrayList<>();
	private List<Point> cells = new ArrayList<>();
	private Navio selectedShip = null;
	private Navio pendingShip = null;
	private Color originalColor = null;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	public final int LARG_DEFAULT = d.width;
	public final int ALT_DEFAULT = d.height;
	public final int CELULA_SIZE = 30;
	public final int NUMERO_COLUNAS = 16;
	public final int NUMERO_LINHAS = 16;
	private Point p;
	private BatalhaNavalFacade tabuleiro = new BatalhaNavalFacade();
	SalvarArquivo saveFile;

	PrimPanel panel;

	public void setSaveFile(SalvarArquivo saveFile) {
		this.saveFile = saveFile;
	}

	public void setNomeJogador(String nomeJogador) {
		this.tabuleiro.setNome(nomeJogador);
	}

	public String getNomeJogador() {
		return this.tabuleiro.getNome();
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
				double newX = 0;
				double newY = 0;
				int col = 0;
				int row = 0;

				if (pendingShip == null && selectedShip == null) {
					for (Navio ship : ships) {
						if (!ship.isConfirmed() && ship.getShape().contains(p)) {
							selectedShip = ship;
							originalColor = ship.getCor();
							//System.out.println(originalColor);
							selectedShip.setCor(getLighterColor(originalColor));
							panel.repaint();
							return;
						}
					}
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					if (selectedShip != null && !selectedShip.isConfirmed()) {
						selectedShip.rotate();
						col = (p.x - panel.right_x) / CELULA_SIZE;
						row = (p.y - panel.down_y) / CELULA_SIZE;
						if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
							newX = panel.right_x + col * CELULA_SIZE;
							newY = panel.down_y + row * CELULA_SIZE;
							pendingShip = selectedShip;
							pendingShip.setPosition(newX, newY);
							cells = getShipCells(newX, newY, pendingShip);
							Coordenadas coord = new Coordenadas(cells, panel.right_x, panel.down_y);
							coord.coordenadaGraficaParaIndices(pendingShip); // envia as coords matriciais para a embarcacao
							if (notify.NotificaObserverTabu(tabuleiro, pendingShip, col, row)) {
								pendingShip.setCor(getLighterColor(originalColor));
							} else {
								pendingShip.setCor(Color.RED);
							}
							cells.clear();
						}
					}
				}

				if (selectedShip != null) {
					col = (p.x - panel.right_x) / CELULA_SIZE;
					row = (p.y - panel.down_y) / CELULA_SIZE;

					if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
						newX = panel.right_x + col * CELULA_SIZE;
						newY = panel.down_y + row * CELULA_SIZE;
						pendingShip = selectedShip;
						pendingShip.setPosition(newX, newY);
						cells = getShipCells(newX, newY, pendingShip);
						Coordenadas coord = new Coordenadas(cells, panel.right_x, panel.down_y);
						coord.coordenadaGraficaParaIndices(pendingShip); // envia as coords matriciais para a embarcacao
						if (notify.NotificaObserverTabu(tabuleiro, pendingShip, col, row)) {
							pendingShip.setCor(getLighterColor(originalColor));
						} else {
							pendingShip.setCor(Color.RED);
						}
						cells.clear();
						 
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
						pendingShip.setCor(originalColor); // Restore the original color
						tabuleiro.addNavio(pendingShip);
						if (tabuleiro.getNavios().size() == tabuleiro.getNumNavios()) {
							tabuleiro.setEstadoPos(true);
						}
						System.out.println(pendingShip.getCor());
						selectedShip = null;
						pendingShip = null;
						panel.repaint();
					} else if (selectedShip != null && pendingShip == null) {
						selectedShip.setCor(originalColor); // Restore the original color
						System.out.println(pendingShip.getCor());
						panel.repaint();
						selectedShip = null;
					} else {
						selectedShip.restoreOriginalPosition();
						selectedShip.setCor(originalColor); // Restore the original color
						//System.out.println(pendingShip.getCor());
						panel.repaint();
						selectedShip = null;
						pendingShip = null;
					}
				}
				panel.updateButtonState();
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
			b1.setEnabled(false);
			add(b1);
			b1.setBounds(LARG_DEFAULT / 2, ALT_DEFAULT - 180, 80, 30);
			b1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					troca.atualizaEstadoTab(tabuleiro, true);
					atq.passaInfo(saveFile, PrimFrame.this, atk.getTabuleiro()); // salva a lista de navios para a tela
																					// de ataque
					PrimFrame.this.dispose();
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
				ships.add(new Navio(3)); // HidroaviÃ£o
			}
			for (int i = 0; i < 2; i++) {
				ships.add(new Navio(4)); // Cruzador
			}
			for (int i = 0; i < 1; i++) {
				ships.add(new Navio(5)); // CouraÃ§ado
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
			g2d.drawString(getNomeJogador() + " posicione suas armas", (LARG_DEFAULT / 2) - 50, ALT_DEFAULT - 200);

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
						g2d.drawString(letraLinha, x + CELULA_SIZE / 2 - width / 2,
								y + CELULA_SIZE / 2 + fm.getHeight() / 2);
					} else if (row == 0 && col > 0) {
						String numeroColuna = String.valueOf(col);
						FontMetrics fm = g2d.getFontMetrics();
						int width = fm.stringWidth(numeroColuna);
						g2d.drawString(numeroColuna, x + CELULA_SIZE / 2 - width / 2,
								y + CELULA_SIZE / 2 + fm.getHeight() / 2);
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
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class InsereNavio extends JPanel {
    private ArrayList<Rectangle2D.Double> ships = new ArrayList<>();
    private Rectangle2D.Double selectedShip = null;
    private final int CELULA_SIZE = 30;
    private final int NUMERO_COLUNAS = 16;
    private final int NUMERO_LINHAS = 16;

    public InsereNavio() {
        // Example initialization of ships
        ships.add(new Rectangle2D.Double(30, 30, CELULA_SIZE, CELULA_SIZE));
        ships.add(new Rectangle2D.Double(70, 30, CELULA_SIZE, CELULA_SIZE));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Obtém a posição do clique do mouse
                Point p = e.getPoint();

                // Verifica se o clique foi em um navio
                for (Rectangle2D.Double ship : ships) {
                    if (ship.contains(p)) {
                        selectedShip = ship;
                        return;
                    }
                }

                // Se um navio estiver selecionado, tenta posicioná-lo no tabuleiro
                if (selectedShip != null) {
                    int col = (p.x - 30) / CELULA_SIZE;
                    int row = (p.y - 30) / CELULA_SIZE;

                    if (col >= 0 && col < NUMERO_COLUNAS && row >= 0 && row < NUMERO_LINHAS) {
                        // Ajusta a posição do navio para a célula do tabuleiro
                        selectedShip.setRect(30 + col * CELULA_SIZE, 30 + row * CELULA_SIZE, CELULA_SIZE, CELULA_SIZE);
                        selectedShip = null; // Desseleciona o navio após posicioná-lo
                        repaint(); // Redesenha o painel para mostrar o navio na nova posição
                    }
                }
            }
        });
    }

}

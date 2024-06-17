package Model;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class BatalhaNavalFacade implements OrbservaTabuleiro {
    private BatalhaNaval batalhaNaval;
    private enum Mode { POSITIONING, ATTACK }
    private Mode currentMode;
    private boolean posicaoPronta = false;
    private String nome;

    // Retorna o nome do jogador
    public String getNome() {
        return this.nome;
    }

    // Define o nome do jogador
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Construtor que inicializa a batalha naval e define o modo como POSITIONING
    public BatalhaNavalFacade() {
        this.batalhaNaval = new BatalhaNaval();
        this.currentMode = Mode.POSITIONING;
    }

    // Define se a posição está pronta
    public void setPosicaoPronta(boolean posicaoPronta) {
        this.posicaoPronta = posicaoPronta;
    }

    // Retorna o número de navios restantes
    public int getNaviosRestantes() {
        return batalhaNaval.getNaviosRestantes();
    }

    // Retorna a lista de navios
    public ArrayList<Navio> getNavios() {
        return batalhaNaval.getNavios();
    }

    // Retorna o estado da posição
    public boolean getEstadoPos() {
        return this.posicaoPronta;
    }

    // Define o estado da posição
    public void setEstadoPos(boolean TF) {
        this.posicaoPronta = TF;
    }

    // Verifica se a posição é válida para um navio
    private boolean isValidPosition(int col, int row, Navio ship) {
        // Verifica se a posição está dentro dos limites do tabuleiro
        if (col <= 0 || row <= 0 || col >= batalhaNaval.getTAMANHO_TABULEIRO() + 1 || row >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
            return false;
        }

        // Verifica se o navio vertical passa dos limites do tabuleiro
        if (ship.getRotationCount() == 0) {
            if (ship.getTamanho() == 2 && row + 1 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
            if (ship.getTamanho() == 3 && (col + 2 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1 || row - 1 <= 0)) {
                return false;
            }
            if (ship.getTamanho() == 4 && row + 3 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
            if (ship.getTamanho() == 5 && row + 4 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
        } 
        // Verifica se o navio horizontal passa dos limites do tabuleiro
        else if (ship.getRotationCount() == 1) {
            if (ship.getTamanho() == 2 && col + 1 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
            if (ship.getTamanho() == 3 && (col + 1 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1 || row + 2 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1)) {
                return false;
            }
            if (ship.getTamanho() == 4 && col + 3 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
            if (ship.getTamanho() == 5 && col + 4 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1) {
                return false;
            }
        } 
        // Verifica se o navio vertical invertido passa dos limites do tabuleiro
        else if (ship.getRotationCount() == 2) {
            if (ship.getTamanho() == 2 && row - 1 <= 0) {
                return false;
            }
            if (ship.getTamanho() == 3 && (row + 1 >= batalhaNaval.getTAMANHO_TABULEIRO() + 1 || col - 2 <= 0)) {
                return false;
            }
            if (ship.getTamanho() == 4 && row - 3 <= 0) {
                return false;
            }
            if (ship.getTamanho() == 5 && row - 4 <= 0) {
                return false;
            }
        } 
        // Verifica se o navio horizontal invertido passa dos limites do tabuleiro
        else {
            if (ship.getTamanho() == 2 && col - 1 <= 0) {
                return false;
            }
            if (ship.getTamanho() == 3 && (col - 1 <= 0 || row - 2 <= 0)) {
                return false;
            }
            if (ship.getTamanho() == 4 && col - 3 <= 0) {
                return false;
            }
            if (ship.getTamanho() == 5 && col - 4 <= 0) {
                return false;
            }
        }

        // Verifica se o navio colide com outros navios
        for (Navio otherShip : batalhaNaval.getNavios()) {
            if (otherShip != ship) {
                if (shapesOverlap(ship.getShape(), otherShip.getShape()) || shapesAdjacent(ship, otherShip)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Verifica se duas formas se sobrepõem
    private boolean shapesOverlap(Shape s1, Shape s2) {
        return s1.getBounds2D().intersects(s2.getBounds2D());
    }

    // Verifica se dois navios estão adjacentes
    private boolean shapesAdjacent(Navio s1, Navio s2) {
        Shape shapeS1 = s1.getShape();
        Shape shapeS2 = s2.getShape();
        Rectangle2D bounds1 = shapeS1.getBounds2D();
        Rectangle2D bounds2 = shapeS2.getBounds2D();

        // Ajusta os limites para considerar adjacência para navios de tamanho 3
        if (s1.getTamanho() == 3) {
            Rectangle2D quadrado1 = new Rectangle2D.Double(bounds1.getX() - 30, bounds1.getY(), bounds1.getWidth(), bounds1.getHeight() + 30);
            Rectangle2D quadrado2 = new Rectangle2D.Double(bounds1.getX(), bounds1.getY() - 30, bounds1.getWidth(), bounds1.getHeight() + 30);
            Rectangle2D quadrado3 = new Rectangle2D.Double(bounds1.getX() + 30, bounds1.getY(), bounds1.getWidth(), bounds1.getHeight() + 30);
        }
        if (s2.getTamanho() == 3) {
            Rectangle2D quadrado1s2 = new Rectangle2D.Double(bounds2.getX() - 30, bounds2.getY(), bounds2.getWidth(), bounds2.getHeight() + 30);
            Rectangle2D quadrado2s2 = new Rectangle2D.Double(bounds2.getX(), bounds2.getY() - 30, bounds2.getWidth(), bounds2.getHeight() + 30);
            Rectangle2D quadrado3s2 = new Rectangle2D.Double(bounds2.getX() + 30, bounds2.getY(), bounds2.getWidth(), bounds2.getHeight() + 30);
        }

        // Expande os limites para verificar adjacência
        bounds1 = new Rectangle2D.Double(bounds1.getX() - 30, bounds1.getY() - 30, bounds1.getWidth() + 2 * 30, bounds1.getHeight() + 2 * 30);
        return bounds1.intersects(bounds2);
    }

    // Verifica se um navio está sobreposto a outro
    private boolean isOverlapping(Navio ship) {
        List<Point> coord = ship.getCoordenadas();
        ArrayList<Navio> confirmedShips = batalhaNaval.getNavios();
        for (Navio otherShip : confirmedShips) {
            if (otherShip != ship) {
                for (int i = 0; i < otherShip.getCoordenadas().size(); i++) {
                    for (int j = 0; j < coord.size(); j++) {
                        if (otherShip.getCoordenadas().get(i).equals(coord.get(j))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Tenta posicionar um navio em uma posição específica
    public boolean placeShip(Navio navio, int col, int row) {
        if (currentMode == Mode.POSITIONING) {
            if (!isOverlapping(navio) && isValidPosition(col, row, navio)) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Você não pode posicionar navios no modo de ataque.");
            return false;
        }
    }

    
    // Retorna se a posição está pronta
    public boolean isPosicaoPronta() {
        return posicaoPronta;
    }

    // Adiciona um navio à lista de navios
    public void addNavio(Navio navio) {
        batalhaNaval.addNavios(navio);
    }

    // Retorna o número de navios
    public int getNumNavios() {
        return batalhaNaval.getNUM_NAVIOS();
    }
}

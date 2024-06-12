package Model;

import java.util.ArrayList;

public class BatalhaNavalFacade implements OrbservaTabuleiro {
    private BatalhaNaval batalhaNaval;
    private enum Mode { POSITIONING, ATTACK }
    private Mode currentMode;
    private boolean posicaoPronta = false;

    public BatalhaNavalFacade() {
        this.batalhaNaval = new BatalhaNaval();
        this.currentMode = Mode.POSITIONING;
    }

    public void setPosicaoPronta(boolean posicaoPronta) {
        this.posicaoPronta = posicaoPronta;
    }
    public int getNaviosRestantes() {
        return batalhaNaval.getNaviosRestantes();
    }
    public ArrayList<Navio> getNavios() {
        return batalhaNaval.getNavios();
    }
    public boolean getEstadoPos() {
         return this.posicaoPronta;
     }

     public void setEstadoPos(boolean TF) {
         this.posicaoPronta = TF;
     }

    public boolean placeShip(Navio navio) {
        if (currentMode == Mode.POSITIONING) {
            batalhaNaval.addNavios(navio);
            batalhaNaval.AtualizaTab_Pos(navio);
            if (batalhaNaval.getNavios().size() == batalhaNaval.getNUM_NAVIOS()) {
                setEstadoPos(true);
            }
            return true;
        } else {
            System.out.println("Você não pode posicionar navios no modo de ataque.");
        }
        return false;
    }

    public void attack(int x, int y) {
        if (currentMode == Mode.ATTACK) {
            // Implementar a lógica de ataque aqui
            // Por exemplo: batalhaNaval.attack(x, y);
        } else {
            System.out.println("Você não pode atacar no modo de posicionamento.");
        }
    }

    public void exibirTabuleiro() {
        batalhaNaval.exibirTabuleiro(batalhaNaval.getTabuleiro());
    }
    public boolean isPosicaoPronta() {
        return posicaoPronta;
    }
}
package View;
import Model.Navio;
import Model.OrbservaTabuleiro;

public interface ObservadoTabuleiro {
    public boolean NotificaObserverTabu(OrbservaTabuleiro obs,Navio ship);
}
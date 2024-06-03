package View;
import Model.Navio;
import Model.OrbservaTabuleiro;

public interface ObservadoTabuleiro {
	public void NotificaObserverTabu(OrbservaTabuleiro obs,Navio ship);
}

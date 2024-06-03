package View;

import Model.Navio;
import Model.OrbservaTabuleiro;

public class Notificador implements ObservadoTabuleiro {

	@Override
	public void NotificaObserverTabu(OrbservaTabuleiro obs,Navio ship) {
		
		obs.AtualizaTab_Pos(ship);
	}

}

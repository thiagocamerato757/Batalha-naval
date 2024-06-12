package View;

import Model.Navio;
import Model.OrbservaTabuleiro;

public class Notificador implements ObservadoTabuleiro {

    @Override
    public boolean NotificaObserverTabu(OrbservaTabuleiro obs,Navio ship) {
        return obs.placeShip(ship);
    }

}
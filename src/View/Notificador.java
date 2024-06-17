package View;

import Model.Navio;
import Model.OrbservaTabuleiro;

public class Notificador implements ObservadoTabuleiro {

    // Método para notificar o observador sobre a posição do navio no tabuleiro
    @Override
    public boolean NotificaObserverTabu(OrbservaTabuleiro obs, Navio ship, int col, int row) {
        return obs.placeShip(ship, col, row); // Chama o método do observador para posicionar o navio
    }

}

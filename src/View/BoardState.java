package View;

import java.util.ArrayList;
import java.util.List;
import Model.*;

public class BoardState {
    private List<Navio> ships;

    public BoardState() {
        this.ships = new ArrayList<>();
    }

    public void addShip(Navio ship) {
        this.ships.add(ship);       
    }

    public List<Navio> getShips() {
        return ships;
    }

    public void setShips(List<Navio> ships) {
        this.ships = ships;
    }
}

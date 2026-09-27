package domain;

import java.awt.Color;


/**
 * @author (Santiago Vargas y Alejandro Barbosa) 
 */
public class Bush extends Organism implements Entity {
    // instance variables - replace the example below with your own
    private final EcoSafari habitat;
    private int age;         // tics vividos
    private boolean hasActed;
    
    /**
     * Constructor for objects of class Bush
     */
    public Bush(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set(this, row, column);
        age = 0;
        hasActed = false;
    }
    
    public EcoSafari getHabitat(){
        return habitat;
    }
    
    public Color getColor(){
        return (age < 4 ? Color.GREEN : Color.YELLOW );
    }
    
        public void tic() {
        if (!hasActed) {
            if (nearElephant()) {
                disappear();
            } else {
                age++;
                if (age == 2) {
                    sprout();
                }
            }
        }
        hasActed = true;
    }

    public void tac() {
        hasActed = false;
    }

    // Revisa las 8 celdas vecinas en busca de un elefante
    private boolean nearElephant() {
        int[] pos = habitat.find(this);
        if (pos == null) return false;
        int r = pos[0], c = pos[1];
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                Entity neighbor = habitat.get(r + dr, c + dc);
                if (neighbor instanceof Elephant) {
                    return true;
                }
            }
        }
        return false;
    }

    // Intenta reproducirse en un vecino vacío: norte, sur, este, oeste
    private void sprout() {
        int[] pos = habitat.find(this);
        if (pos == null) return;
        int r = pos[0], c = pos[1];

        int[][] priority = { {-1, 0}, {1, 0}, {0, 1}, {0, -1} }; // N, S, E, O

        for (int[] dir : priority) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (habitat.isInside(nr, nc) && habitat.get(nr, nc) == null) {
                new Bush(habitat, nr, nc);
                break;
            }
        }
    }
}
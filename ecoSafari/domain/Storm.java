package domain;

import java.awt.Color;

public class Storm implements Entity {

    private final EcoSafari habitat;
    private int direction;
    private int steps;

    public Storm(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set(this, row, column);
        direction = 0;
        steps = 0;
    }

    public EcoSafari getHabitat() {
        return habitat;
    }

    public Color getColor() {
        return Color.BLACK;
    }

    public int shape() {
        return Entity.SQUARE;
    }

    public void tic() {
        int[] position = habitat.find(this);

        if (position != null) {

            int[][] directions = {
                {-1, 1},  // NE
                {1, 1},   // SE
                {1, -1},  // SO
                {-1, -1}  // NO
            };

            int r = position[0];
            int c = position[1];

            int newRow = r + directions[direction][0];
            int newColumn = c + directions[direction][1];

            if (habitat.isInside(newRow, newColumn)) {

                //Destruir lo que esta en el centro
                habitat.set(null, newRow, newColumn);
                
                //Desplazarse
                habitat.set(null, r, c);
                habitat.set(this, newRow, newColumn);
            }

            steps++;

            if (steps == 3) {
                steps = 0;
                direction = (direction + 1) % 4;
            }
        }
    }

    public void tac() {
    }
}
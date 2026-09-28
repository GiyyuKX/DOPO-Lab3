package domain;

import java.awt.Color;

public class Sabertooth extends Organism implements Entity {

    private final EcoSafari habitat;
    private boolean hasActed;

    public Sabertooth(EcoSafari habitat, int row, int column) {
        this.habitat = habitat;
        habitat.set(this, row, column);
        hasActed = false;
    }

    public EcoSafari getHabitat() {
        return habitat;
    }

    public Color getColor() {
        return Color.ORANGE;
    }

    public int shape() {
        return Entity.ROUND;
    }

    public void tic() {
        if (!hasActed) {
            int[] elephant = findElephant();

            if (elephant != null) {
                eatElephant(elephant);
            } else {
                if (move(2, 2)) {
                    changeEnergy(-5);

                    if (getEnergy() == 0) {
                        disappear();
                    }
                }
            }
        }

        hasActed = true;
    }

    public void tac() {
        hasActed = false;
    }

    private int[] findElephant() {
        int[] position = habitat.find(this);

        if (position == null) {
            return null;
        }

        int r = position[0];
        int c = position[1];

        for (int dr = -4; dr <= 4; dr++) {
            for (int dc = -4; dc <= 4; dc++) {

                Entity entity = habitat.get(r + dr, c + dc);

                if (entity instanceof Elephant) {
                    return new int[] {r + dr, c + dc};
                }
            }
        }

        return null;
    }

    private void eatElephant(int[] position) {
        int[] current = habitat.find(this);

        habitat.set(null, current[0], current[1]);
        habitat.set(this, position[0], position[1]);

        changeEnergy(30);
    }
}
package domain;

public class HungryElephant extends Elephant {

    private boolean hasActed;

    public HungryElephant(EcoSafari habitat, int row, int column) {
        super(habitat, row, column);
        hasActed = false;
    }

    @Override
    public void tic() {
        if (!hasActed) {
            if (!eatBush()) {
                if (move(1, 1)) {
                    changeEnergy(-10);

                    if (getEnergy() == 0) {
                        disappear();
                    }
                }
            }
        }

        hasActed = true;
    }

    @Override
    public void tac() {
        hasActed = false;
    }

    private boolean eatBush() {
        int[] position = getHabitat().find(this);

        if (position == null) {
            return false;
        }

        int r = position[0];
        int c = position[1];

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {

                if (dr == 0 && dc == 0) {
                    continue;
                }

                Entity entity = getHabitat().get(r + dr, c + dc);

                if (entity instanceof Bush) {
                    getHabitat().set(this, r + dr, c + dc);
                    getHabitat().set(null, r, c);
                    changeEnergy(20);
                    return true;
                }
            }
        }

        return false;
    }
}
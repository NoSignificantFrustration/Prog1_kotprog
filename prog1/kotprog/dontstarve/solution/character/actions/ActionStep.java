package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.utility.Direction;

/**
 * A lépés akció leírására szolgáló osztály: a karakter egy lépést tesz balra, jobbra, fel vagy le.
 */
public class ActionStep extends Action {
    /**
     * A mozgás iránya.
     */
    private final Direction direction;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param direction a mozgás iránya
     */
    public ActionStep(Direction direction) {
        super(ActionType.STEP);
        this.direction = direction;
    }

    /**
     * A direction gettere.
     *
     * @return a mozgás iránya
     */
    public Direction getDirection() {
        return direction;
    }
}

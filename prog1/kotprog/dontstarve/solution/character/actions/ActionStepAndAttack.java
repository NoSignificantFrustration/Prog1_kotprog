package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.utility.Direction;

/**
 * A lépés és támadás akció leírására szolgáló osztály: a karakter egy lépést tesz balra, jobbra, fel vagy le,
 * majd megtámadja a legközelebbi karaktert.
 */
public class ActionStepAndAttack extends Action {
    /**
     * A mozgás iránya.
     */
    private final Direction direction;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionStepAndAttack(Direction direction) {
        super(ActionType.STEP_AND_ATTACK);
        this.direction = direction;
    }

    /**
     * Az irány lekérdezésére szolgáló getter.
     *
     * @return a mozgás iránya
     */
    public Direction getDirection() {
        return direction;
    }
}

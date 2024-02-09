package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * A tárgy levétele akció leírására szolgáló osztály: az aktuálisan kézben lévő item visszarakása az inventory-ba.
 */
public class ActionUnequip extends Action {
    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionUnequip() {
        super(ActionType.UNEQUIP);
    }
}

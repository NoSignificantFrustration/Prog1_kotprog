package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * A tárgy kézbe vétele akció leírására szolgáló osztály: egy inventory-ban lévő felvehető tárgy kézbe vétele.
 */
public class ActionEquip extends Action {
    /**
     * A felvenni kívánt tárgy pozíciója az inventory-ban.
     */
    private final int index;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index a felvenni kívánt tárgy pozíciója az inventory-ban
     */
    public ActionEquip(int index) {
        super(ActionType.EQUIP);
        this.index = index;
    }

    /**
     * Az index gettere.
     *
     * @return a felvenni kívánt tárgy pozíciója az inventory-ban
     */
    public int getIndex() {
        return index;
    }
}

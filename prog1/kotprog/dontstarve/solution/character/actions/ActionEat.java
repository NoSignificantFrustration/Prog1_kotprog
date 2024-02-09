package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * Az étel elfogyasztása akció leírására szolgáló osztály: egy étel elfogyasztása az inventory-ból.
 */
public class ActionEat extends Action {
    /**
     * A megenni kívánt étel pozíciója az inventory-ban.
     */
    private final int index;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index a megenni kívánt étel pozíciója az inventory-ban
     */
    public ActionEat(int index) {
        super(ActionType.EAT);
        this.index = index;
    }

    /**
     * Az index gettere.
     *
     * @return a megenni kívánt étel pozíciója az inventory-ban
     */
    public int getIndex() {
        return index;
    }
}

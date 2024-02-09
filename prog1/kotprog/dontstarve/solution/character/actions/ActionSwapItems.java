package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * A tárgyak cseréje akció leírására szolgáló osztály: az inventory-ban két itemet megcserélünk.
 */
public class ActionSwapItems extends Action {
    /**
     * A cserében részt vevő első tárgy indexe az inventory-ban.
     */
    private final int index1;

    /**
     * A cserében részt vevő második tárgy indexe az inventory-ban.
     */
    private final int index2;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index1 a cserében részt vevő első tárgy indexe az inventory-ban
     * @param index2 a cserében részt vevő második tárgy indexe az inventory-ban
     */
    public ActionSwapItems(int index1, int index2) {
        super(ActionType.SWAP_ITEMS);
        this.index1 = index1;
        this.index2 = index2;
    }

    /**
     * Az index1 gettere.
     *
     * @return a cserében részt vevő első tárgy indexe az inventory-ban
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * Az index2 gettere.
     *
     * @return a cserében részt vevő második tárgy indexe az inventory-ban
     */
    public int getIndex2() {
        return index2;
    }
}

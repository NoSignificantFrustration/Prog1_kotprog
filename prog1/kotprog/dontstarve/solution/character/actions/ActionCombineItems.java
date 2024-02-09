package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * A tárgyak kombinálása akció leírására szolgáló osztály: két item egyesítése az inventory-ban.
 */
public class ActionCombineItems extends Action {
    /**
     * A kombinálásban részt vevő első tárgy indexe az inventory-ban.
     */
    private final int index1;

    /**
     * A kombinálásban részt vevő második tárgy indexe az inventory-ban.
     */
    private final int index2;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index1 a kombinálásban részt vevő első tárgy indexe az inventory-ban
     * @param index2 a kombinálásban részt vevő második tárgy indexe az inventory-ban
     */
    public ActionCombineItems(int index1, int index2) {
        super(ActionType.COMBINE_ITEMS);
        this.index1 = index1;
        this.index2 = index2;
    }

    /**
     * az index1 gettere.
     *
     * @return a kombinálásban részt vevő első tárgy indexe az inventory-ban
     */
    public int getIndex1() {
        return index1;
    }

    /**
     * az index2 gettere.
     *
     * @return a kombinálásban részt vevő második tárgy indexe az inventory-ban
     */
    public int getIndex2() {
        return index2;
    }
}

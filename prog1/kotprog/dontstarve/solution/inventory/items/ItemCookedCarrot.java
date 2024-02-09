package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A főtt répa item leírására szolgáló osztály.
 */
public class ItemCookedCarrot extends AbstractItem implements Cloneable {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemCookedCarrot(int amount) {
        super(ItemType.COOKED_CARROT, amount);
    }
}

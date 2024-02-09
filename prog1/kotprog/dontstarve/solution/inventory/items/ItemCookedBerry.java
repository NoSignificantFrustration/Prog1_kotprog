package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A főtt bogyó item leírására szolgáló osztály.
 */
public class ItemCookedBerry extends AbstractItem implements Cloneable {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemCookedBerry(int amount) {
        super(ItemType.COOKED_BERRY, amount);
    }
}

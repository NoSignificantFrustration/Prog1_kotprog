package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A fa item leírására szolgáló osztály.
 */
public class ItemLog extends AbstractItem implements Cloneable {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemLog(int amount) {
        super(ItemType.LOG, amount);
    }
}

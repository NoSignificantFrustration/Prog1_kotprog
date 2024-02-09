package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A csákány item leírására szolgáló osztály.
 */
public class ItemPickaxe extends EquippableItem implements Cloneable {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public ItemPickaxe() {
        super(ItemType.PICKAXE);
    }
}

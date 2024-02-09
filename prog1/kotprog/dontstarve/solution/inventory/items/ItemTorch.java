package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A fáklya item leírására szolgáló osztály.
 */
public class ItemTorch extends EquippableItem implements Cloneable {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public ItemTorch() {
        super(ItemType.TORCH);
    }
}

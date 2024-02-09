package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

/**
 * A kraftolás akció leírására szolgáló osztály: adott típusú item kraftolása.
 */
public class ActionCraft extends Action {
    /**
     * A lekraftolandó item típusa.
     */
    private final ItemType itemType;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param itemType a lekraftolandó item típusa
     */
    public ActionCraft(ItemType itemType) {
        super(ActionType.CRAFT);
        this.itemType = itemType;
    }

    /**
     * Az itemType gettere.
     *
     * @return a lekraftolandó item típusa
     */
    public ItemType getItemType() {
        return itemType;
    }
}

package prog1.kotprog.dontstarve.solution.inventory.items;


/**
 * Felvehető / kézbe vehető item leírására szolgáló osztály.
 */
public abstract class EquippableItem extends AbstractItem implements Cloneable {

    private final float maxDurability;
    private float durability;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type az item típusa
     */
    public EquippableItem(ItemType type) {
        super(type, 1);
        switch (type) {
            case PICKAXE -> {
                maxDurability = 30;
            }
            case AXE -> {
                maxDurability = 40;
            }
            case SPEAR -> {
                maxDurability = 10;
            }
            case TORCH -> {
                maxDurability = 20;
            }
            default -> maxDurability = 100;
        }

        durability = maxDurability;
    }

    /**
     * Megadja, hogy milyen állapotban van a tárgy.
     *
     * @return a tárgy használatlansága, %-ban (100%: tökéletes állapot)
     */
    public float percentage() {
        return durability / maxDurability * 100f;
    }

    public boolean use() {
        if (durability <= 0) {
            return true;
        }
        durability--;

        return durability <= 0;

    }

}

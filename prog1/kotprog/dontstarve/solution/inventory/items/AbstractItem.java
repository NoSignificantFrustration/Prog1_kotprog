package prog1.kotprog.dontstarve.solution.inventory.items;


/**
 * Egy általános itemet leíró osztály.
 */
public abstract class AbstractItem implements Cloneable {
    /**
     * Az item típusa.
     *
     * @see ItemType
     */
    private final ItemType type;

    /**
     * Az item mennyisége.
     */
    private int amount;


    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type   az item típusa
     * @param amount az item mennyisége
     */
    public AbstractItem(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }


    /**
     * A type gettere.
     *
     * @return a tárgy típusa
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Az amount gettere.
     *
     * @return a tárgy mennyisége
     */
    public int getAmount() {
        return amount;
    }

    public void setAmount(int newAmount) {
        amount = newAmount;
    }


    public int getStackSize() {
        switch (type) {
            case AXE, SPEAR, PICKAXE, TORCH -> {
                return 1;
            }
            case LOG -> {
                return 15;
            }
            case STONE, RAW_CARROT, COOKED_CARROT, RAW_BERRY, COOKED_BERRY -> {
                return 10;
            }
            case TWIG -> {
                return 20;
            }

        }
        //System.err.println("Couldn't parse stack size for " + type);
        return 0;
    }

    @Override
    public AbstractItem clone() {
        try {
            return (AbstractItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

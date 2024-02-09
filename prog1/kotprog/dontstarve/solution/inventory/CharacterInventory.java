package prog1.kotprog.dontstarve.solution.inventory;


import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

public class CharacterInventory implements BaseInventory {

    private static final int INVENTORY_SIZE = 10;
    private EquippableItem hand;
    private final AbstractItem[] mainInventory;
    private int currentEmptySlots;


    public CharacterInventory() {
        mainInventory = new AbstractItem[INVENTORY_SIZE];
        currentEmptySlots = INVENTORY_SIZE;
    }

    @Override
    public boolean addItem(AbstractItem item) {
        int stackSize = item.getStackSize();
        //System.out.println("Tried to add " + item.getType());

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (mainInventory[i] != null && mainInventory[i].getType() == item.getType()) {
                int sum = mainInventory[i].getAmount() + item.getAmount();
                if (sum <= stackSize) {
                    mainInventory[i].setAmount(sum);
                    item.setAmount(0);
                    return true;
                } else {
                    mainInventory[i].setAmount(stackSize);
                    item.setAmount(sum - stackSize);
                }
            }
        }

        if (currentEmptySlots == 0) {
            return false;
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (mainInventory[i] == null) {
                if (item.getAmount() > stackSize) {
                    mainInventory[i] = item.clone();
                    mainInventory[i].setAmount(stackSize);
                    currentEmptySlots--;
                    item.setAmount(item.getAmount() - stackSize);
                } else {
                    mainInventory[i] = item;
                    currentEmptySlots--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AbstractItem dropItem(int index) {
        if (index < 0 || index >= INVENTORY_SIZE) {
            return null;
        }
        AbstractItem item = mainInventory[index];
        mainInventory[index] = null;
        currentEmptySlots++;
        return item;
    }

    @Override
    public boolean removeItem(ItemType type, int amount) {
        int itemCount = 0;

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (mainInventory[i] != null && mainInventory[i].getType() == type) {
                itemCount += mainInventory[i].getAmount();
            }
        }

        if (itemCount < amount) {
            return false;
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {

            if (mainInventory[i] == null) {
                continue;
            }

            if (mainInventory[i].getType() == type) {
                int invAmount = mainInventory[i].getAmount();
                if (invAmount < amount) {
                    amount -= invAmount;
                    mainInventory[i] = null;
                    currentEmptySlots++;
                } else if (invAmount == amount) {
                    mainInventory[i] = null;
                    currentEmptySlots++;
                    break;
                } else {
                    mainInventory[i].setAmount(invAmount - amount);
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public boolean swapItems(int index1, int index2) {
        if (index1 < 0 || index1 >= INVENTORY_SIZE || index2 < 0 || index2 >= INVENTORY_SIZE) {
            return false;
        }
        if (mainInventory[index1] == null || mainInventory[index2] == null) {
            return false;
        }
        AbstractItem tmp = mainInventory[index1];
        mainInventory[index1] = mainInventory[index2];
        mainInventory[index2] = tmp;
        return true;

    }

    @Override
    public boolean moveItem(int index, int newIndex) {

        if (index < 0 || index >= INVENTORY_SIZE || newIndex < 0 || newIndex >= INVENTORY_SIZE) {
            return false;
        }

        if (mainInventory[index] != null && mainInventory[newIndex] == null) {
            mainInventory[newIndex] = mainInventory[index];
            mainInventory[index] = null;
            return true;
        }

        return false;
    }

    @Override
    public boolean combineItems(int index1, int index2) {
        if (index1 < 0 || index1 >= INVENTORY_SIZE) {
            return false;
        }
        if (index2 < 0 || index2 >= INVENTORY_SIZE) {
            return false;
        }

        if (mainInventory[index1] != null && mainInventory[index2] != null && mainInventory[index1].getType() == mainInventory[index2].getType()) {
            int stackSize = mainInventory[index1].getStackSize();
            int sum = mainInventory[index1].getAmount() + mainInventory[index2].getAmount();

            if (sum == stackSize * 2 || mainInventory[index1].getAmount() == stackSize) {
                return false;
            }

            if (sum <= stackSize) {
                mainInventory[index1].setAmount(sum);
                mainInventory[index2] = null;
                currentEmptySlots++;

            } else {
                mainInventory[index1].setAmount(stackSize);
                mainInventory[index2].setAmount(sum - stackSize);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equipItem(int index) {
        if (index < 0 || index >= INVENTORY_SIZE) {

            return false;
        }

        if (mainInventory[index] == null) {

            return false;
        }


        if (mainInventory[index] instanceof EquippableItem) {
            AbstractItem tmp = hand;
            hand = (EquippableItem) mainInventory[index];
            mainInventory[index] = tmp;
            if (tmp == null) {
                currentEmptySlots++;
            }
            return true;
        }

        return false;

    }

    @Override
    public EquippableItem unequipItem() {
        if (currentEmptySlots == 0) {
            EquippableItem item = hand;
            hand = null;
            return item;
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (mainInventory[i] == null) {
                mainInventory[i] = hand;
                hand = null;
                return null;
            }
        }
        return null;
    }

    @Override
    public ItemType cookItem(int index) {
        if (index < 0 || index >= INVENTORY_SIZE) {
            return null;
        }
        if (mainInventory[index] == null) {
            return null;
        }

        ItemType type = mainInventory[index].getType();

        if (mainInventory[index].getType() == ItemType.RAW_BERRY || mainInventory[index].getType() == ItemType.RAW_CARROT) {

            removeOne(index);
            return type;
        }
        return null;
    }

    @Override
    public ItemType eatItem(int index) {
        if (index < 0 || index >= INVENTORY_SIZE) {
            return null;
        }
        if (mainInventory[index] == null) {
            return null;
        }
        ItemType type = mainInventory[index].getType();

        if (type == ItemType.RAW_BERRY || type == ItemType.RAW_CARROT || type == ItemType.COOKED_CARROT || type == ItemType.COOKED_BERRY) {
            removeOne(index);
            return type;
        }

        return null;
    }

    @Override
    public int countItem(ItemType type) {
        int count = 0;
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (mainInventory[i] != null && mainInventory[i].getType() == type) {
                count += mainInventory[i].getAmount();
            }
        }
        return count;
    }

    private void removeOne(int index) {
        int itemCount = mainInventory[index].getAmount();
        if (itemCount > 1) {
            mainInventory[index].setAmount(itemCount - 1);
        } else {
            mainInventory[index] = null;
            currentEmptySlots++;
        }
    }

    @Override
    public void destroyEquippedItem() {
        hand = null;
    }

    @Override
    public int emptySlots() {
        return currentEmptySlots;
    }

    public static int getInventorySize() {
        return INVENTORY_SIZE;
    }

    @Override
    public EquippableItem equippedItem() {
        return hand;
    }

    @Override
    public AbstractItem getItem(int index) {
        if (index < 0 || index >= INVENTORY_SIZE) {
            return null;
        }
        return mainInventory[index];
    }


}

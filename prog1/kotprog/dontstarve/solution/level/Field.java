package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.*;

import java.util.ArrayList;
import java.util.List;

public class Field implements BaseField {

    private boolean walkable;
    private boolean isTree;
    private boolean isStone;
    private boolean isTwig;
    private boolean isBerry;
    private boolean isCarrot;
    private boolean isFire;
    private int fireLife;
    private final List<AbstractItem> contents;
    private int harvestTime;
    private TileType type;


    public Field(TileType type) {
        this.type = type;
        contents = new ArrayList<>();
        walkable = true;

        switch (type) {
            case Empty:
                break;
            case Water:
                walkable = false;
                break;
            case Tree:
                isTree = true;
                harvestTime = 4;
                break;
            case Boulder:
                isStone = true;
                harvestTime = 5;
                break;
            case Twig:
                isTwig = true;
                harvestTime = 2;
                break;
            case Berry:
                isBerry = true;
                harvestTime = 1;
                break;
            case Carrot:
                isCarrot = true;
                harvestTime = 1;
                break;
        }
    }

    @Override
    public void removeItem(AbstractItem item) {
        contents.remove(item);
    }

    @Override
    public void addItem(AbstractItem item) {
        contents.add(item);
    }

    @Override
    public void harvest(BaseInventory inventory) {
        if (harvestTime == 0) {
            return;
        }
        //System.out.println("Harvesting with " + inventory.equippedItem());

        if (type == TileType.Tree) {
            if (inventory.equippedItem() instanceof ItemAxe axe) {
                axe.use();
                harvestTime--;

            } else {
                return;
            }
        } else if (type == TileType.Boulder) {
            if (inventory.equippedItem() instanceof ItemPickaxe pick) {
                pick.use();
                harvestTime--;
            } else {
                return;
            }
        } else {
            harvestTime--;
        }

        if (harvestTime > 0) {
            return;
        }

        AbstractItem item = null;
        switch (type) {
            case Carrot -> {
                item = new ItemRawCarrot(1);
                System.out.println("Harvested carrot");
                isCarrot = false;
                break;
            }
            case Berry -> {
                item = new ItemRawBerry(1);
                System.out.println("Harvested berry");
                isBerry = false;
                break;
            }
            case Twig -> {
                item = new ItemTwig(1);
                System.out.println("Harvested twig");
                isTwig = false;
                break;
            }
            case Boulder -> {
                contents.add(new ItemStone(3));
                System.out.println("Dropped 3 stone");
                type = TileType.Empty;
                isStone = false;
                break;
            }
            case Tree -> {
                contents.add(new ItemLog(2));
                System.out.println("Dropped 2 logs");
                type = TileType.Empty;
                isTree = false;
                break;
            }
        }

        if (item != null && !inventory.addItem(item)) {
            contents.add(item);
        }
    }

    @Override
    public void makeFire() {
        if (type != TileType.Empty) {
            return;
        }
        GameManager.getInstance().addFire(this);
        isFire = true;
        fireLife = 60;
    }

    @Override
    public boolean fireTick() {
        fireLife--;
        if (fireLife <= 0) {
            isFire = false;
            return false;
        }
        return true;
    }

    @Override
    public boolean isWalkable() {
        return walkable;
    }

    @Override
    public boolean hasTree() {
        return isTree;
    }

    @Override
    public boolean hasStone() {
        return isStone;
    }

    @Override
    public boolean hasTwig() {
        return isTwig;
    }

    @Override
    public boolean hasBerry() {
        return isBerry;
    }

    @Override
    public boolean hasCarrot() {
        return isCarrot;
    }

    @Override
    public boolean hasFire() {
        return isFire;
    }

    @Override
    public AbstractItem[] items() {

        return contents.toArray(new AbstractItem[contents.size()]);
    }

    @Override
    public TileType getType() {
        return type;
    }

    @Override
    public int getHarvestTime() {
        return harvestTime;
    }

    public enum TileType {
        Empty, Water, Tree, Boulder, Twig, Berry, Carrot
    }
}

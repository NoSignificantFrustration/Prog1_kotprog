package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.actions.*;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.*;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;


public class CharacterActionManager {

    private BaseCharacter character;

    public void doCharacterAction(BaseCharacter character, Action action) {

        this.character = character;


        switch (action.getType()) {
            case NONE -> {
                break;
            }
            case STEP -> {
                if (action instanceof ActionStep step) {
                    moveCharacter(step.getDirection());
                }
                break;
            }
            case STEP_AND_ATTACK -> {
                if (action instanceof ActionStepAndAttack stepAndAttack) {
                    moveCharacter(stepAndAttack.getDirection());
                    attack();
                }
                break;
            }
            case ATTACK -> {
                if (action instanceof ActionAttack attackAction) {
                    attack();
                }
                break;
            }
            case INTERACT -> {

                harvestField();
                break;
            }
            case COLLECT_ITEM -> {
                pickUpItem();
                break;
            }
            case COOK -> {
                Position wholePos = character.getCurrentPosition().getNearestWholePosition();
                if (action instanceof ActionCook cook && GameManager.getInstance().getField((int) wholePos.getX(), (int) wholePos.getY()).hasFire()) {
                    cook(cook.getIndex());
                }
                break;
            }
            case EQUIP -> {
                if (action instanceof ActionEquip equip) {
                    character.getInventory().equipItem(equip.getIndex());
                }
                break;
            }
            case UNEQUIP -> {
                if (action instanceof ActionUnequip) {
                    character.getInventory().unequipItem();
                }
                break;
            }
            case MOVE_ITEM -> {
                if (action instanceof ActionMoveItem moveItem) {
                    character.getInventory().moveItem(moveItem.getOldIndex(), moveItem.getNewIndex());
                }
                break;
            }
            case SWAP_ITEMS -> {
                if (action instanceof ActionSwapItems swapItems) {
                    character.getInventory().swapItems(swapItems.getIndex1(), swapItems.getIndex2());
                }
                break;
            }
            case DROP_ITEM -> {
                if (action instanceof ActionDropItem drop) {
                    character.dropItem(drop.getIndex());
                }
                break;
            }
            case COMBINE_ITEMS -> {
                if (action instanceof ActionCombineItems combine) {
                    character.getInventory().combineItems(combine.getIndex1(), combine.getIndex2());
                }
                break;
            }
            case EAT -> {
                if (action instanceof ActionEat actionEat) {
                    eat(actionEat.getIndex());
                }
            }
            case CRAFT -> {
                if (action instanceof ActionCraft actionCraft) {
                    craft(actionCraft.getItemType());
                }
            }

        }

        character.setLastAction(action);
    }

    private void moveCharacter(Direction dir) {
        Position nextPos = new Position(character.getCurrentPosition().getX(), character.getCurrentPosition().getY());
        switch (dir) {
            case UP -> {
                nextPos.setY(nextPos.getY() - character.getSpeed());
                break;
            }
            case DOWN -> {
                nextPos.setY(nextPos.getY() + character.getSpeed());
                break;
            }
            case LEFT -> {
                nextPos.setX(nextPos.getX() - character.getSpeed());
                break;
            }
            case RIGHT -> {
                nextPos.setX(nextPos.getX() + character.getSpeed());
                break;
            }
        }

        GameManager gameManager = GameManager.getInstance();

        Position wholePos = nextPos.getNearestWholePosition();
        if (wholePos.getX() < 0 || wholePos.getX() >= gameManager.getLevelX() || wholePos.getY() < 0 || wholePos.getY() >= gameManager.getLevelY()) {
            return;
        }
        if (gameManager.getField((int) wholePos.getX(), (int) wholePos.getY()).getType() == Field.TileType.Water) {
            return;
        }
        character.setCurrentPosition(nextPos);/*
        character.getCurrentPosition().setX(nextPos.getX());
        character.getCurrentPosition().setY(nextPos.getY());*/
    }

    private void cook(int index) {

        AbstractItem item = character.getInventory().getItem(index);
        if (item == null) {
            return;
        }

        if (item.getType() == ItemType.RAW_BERRY) {
            character.getInventory().removeItem(ItemType.RAW_BERRY, 1);
            character.getInventory().addItem(new ItemCookedBerry(1));

        } else if (item.getType() == ItemType.RAW_CARROT) {
            character.getInventory().removeItem(ItemType.RAW_CARROT, 1);
            character.getInventory().addItem(new ItemCookedCarrot(1));
        }

    }

    private void attack() {

        //System.out.println("Attacking");

        BaseCharacter closestCharacter = GameManager.getInstance().getClosestEnemy(character);
        if (closestCharacter == null) {
            //System.out.println("Target not found");
            return;
        }

        float damage = 4;
        if (character.getInventory().equippedItem() != null) {

            if (character.getInventory().equippedItem().getType() == ItemType.TORCH) {
                damage = 6;
            } else {
                ItemType type = character.getInventory().equippedItem().getType();
                if (type == ItemType.SPEAR) {
                    damage = 19;
                } else if (type == ItemType.AXE || type == ItemType.PICKAXE) {
                    damage = 8;
                }

                character.getInventory().equippedItem().use();
                if (character.getInventory().equippedItem().percentage() <= 0) {
                    character.getInventory().destroyEquippedItem();
                }
            }
        }
        closestCharacter.setHp(closestCharacter.getHp() - damage);

    }

    private void harvestField() {
        Position wholePos = character.getCurrentPosition().getNearestWholePosition();
        BaseField field = GameManager.getInstance().getField((int) wholePos.getX(), (int) wholePos.getY());
        field.harvest(character.getInventory());
    }

    private void pickUpItem() {

        Position wholePos = character.getCurrentPosition().getNearestWholePosition();
        BaseField field = GameManager.getInstance().getField((int) wholePos.getX(), (int) wholePos.getY());


        AbstractItem[] items = field.items();
        if (items.length > 0 && character.getInventory().addItem(items[0])) {
            field.removeItem(items[0]);
        }

    }

    private void eat(int index) {
        if (character.getInventory().getItem(index) == null || character.getHunger() == 100) {
            return;
        }

        ItemType type = character.getInventory().getItem(index).getType();

        int hungerDelta = 0;
        int hpDelta = 0;
        ItemType foodType;

        switch (type) {
            case RAW_BERRY -> {
                hpDelta = -5;
                hungerDelta = 20;
                foodType = ItemType.RAW_BERRY;
            }
            case RAW_CARROT -> {
                hpDelta = 1;
                hungerDelta = 12;
                foodType = ItemType.RAW_CARROT;
            }
            case COOKED_BERRY -> {
                hpDelta = 1;
                hungerDelta = 10;
                foodType = ItemType.COOKED_BERRY;
            }
            case COOKED_CARROT -> {
                hpDelta = 3;
                hungerDelta = 10;
                foodType = ItemType.COOKED_CARROT;
            }
            default -> {
                return;
            }
        }


        character.setHunger(character.getHunger() + hungerDelta);
        character.setHp(character.getHp() + hpDelta);
        character.getInventory().removeItem(foodType, 1);


    }

    private void craft(ItemType item) {
        BaseInventory inventory = character.getInventory();
        switch (item) {
            case AXE -> {
                if (inventory.countItem(ItemType.TWIG) >= 3) {
                    inventory.removeItem(ItemType.TWIG, 3);
                    inventory.addItem(new ItemAxe());
                }
            }
            case PICKAXE -> {
                if (inventory.countItem(ItemType.LOG) >= 2 && inventory.countItem(ItemType.TWIG) >= 2) {
                    inventory.removeItem(ItemType.LOG, 2);
                    inventory.removeItem(ItemType.TWIG, 2);
                    inventory.addItem(new ItemPickaxe());
                }
            }
            case SPEAR -> {
                if (inventory.countItem(ItemType.LOG) >= 2 && inventory.countItem(ItemType.STONE) >= 2) {
                    inventory.removeItem(ItemType.LOG, 2);
                    inventory.removeItem(ItemType.STONE, 2);
                    inventory.addItem(new ItemSpear());
                }
            }
            case TORCH -> {
                if (inventory.countItem(ItemType.LOG) >= 1 && inventory.countItem(ItemType.TWIG) >= 3) {
                    inventory.removeItem(ItemType.LOG, 1);
                    inventory.removeItem(ItemType.TWIG, 3);
                    inventory.addItem(new ItemTorch());
                }
            }
            case FIRE -> {
                Position wholePos = character.getCurrentPosition().getNearestWholePosition();
                BaseField field = GameManager.getInstance().getField((int) wholePos.getX(), (int) wholePos.getY());
                if (field.getType() != Field.TileType.Empty) {
                    return;
                }

                if (inventory.countItem(ItemType.TWIG) >= 2 && inventory.countItem(ItemType.LOG) >= 2 && inventory.countItem(ItemType.STONE) >= 4) {
                    inventory.removeItem(ItemType.TWIG, 2);
                    inventory.removeItem(ItemType.LOG, 2);
                    inventory.removeItem(ItemType.STONE, 4);
                    field.makeFire();
                }
            }
        }
    }

}

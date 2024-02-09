package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionNone;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.CharacterInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.utility.Position;

public class Character implements BaseCharacter {

    private float hunger;
    private float hp;
    private final BaseInventory inventory;
    private Position position;
    private Action lastAction;
    private final String name;
    private static final float[] hpRanges = new float[]{50f, 30f, 10f};
    private static final float[] hpSpeedValues = new float[]{0.9f, 0.75f, 0.6f};
    private static final float[] hungerRanges = new float[]{50f, 20f, 0f};
    private static final float[] hungerSpeedValues = new float[]{0.9f, 0.8f, 0.5f};
    private static final float MAX_STATS = 100f;

    public Character(String name, Position position) {
        this.position = position;
        this.name = name;
        inventory = new CharacterInventory();
        hunger = MAX_STATS;
        hp = MAX_STATS;
        lastAction = new ActionNone();
    }


    public void dropItem(int index) {
        AbstractItem item = inventory.dropItem(index);
        if (item == null) {
            return;
        }

        Position wholePos = position.getNearestWholePosition();
        GameManager.getInstance().getField((int) wholePos.getX(), (int) wholePos.getY()).addItem(item);

    }


    @Override
    public float getSpeed() {
        float hungerMultiplier = getValueInRange(hp, hpRanges, hpSpeedValues);
        float hpMultiplier = getValueInRange(hunger, hungerRanges, hungerSpeedValues);;

        return hungerMultiplier * hpMultiplier;
    }

    private float getValueInRange(float inVal, float[] ranges, float[] rangeValues){
        if (inVal == 0f){
            return rangeValues[rangeValues.length - 1];
        }
        if (inVal >= ranges[0]){
            return 1;
        }

        for (int i = 1; i < ranges.length; i++) {
            if (inVal >= ranges[i]){
                return rangeValues[i - 1];
            }
        }
        return rangeValues[rangeValues.length - 1];
    }

    @Override
    public float getHunger() {
        return hunger;
    }

    @Override
    public void setHunger(float newVal) {
        hunger = newVal;

    }

    @Override
    public float getHp() {
        return hp;
    }

    @Override
    public void setHp(float newVal) {
        if (hp == 0) {
            return;
        }

        hp = newVal;
        if (hp > MAX_STATS) {
            hp = MAX_STATS;
        } else if (hp <= 0) {
            hp = 0;
            for (int i = 0; i < CharacterInventory.getInventorySize(); i++) {
                dropItem(i);
            }
            GameManager.getInstance().characterDeath(this);
        }
    }

    @Override
    public BaseInventory getInventory() {
        return inventory;
    }

    @Override
    public Position getCurrentPosition() {
        return position;
    }

    @Override
    public void setCurrentPosition(Position newPos) {
        position = newPos;
    }

    @Override
    public Action getLastAction() {
        return lastAction;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public void setLastAction(Action action) {
        lastAction = action;
    }

}

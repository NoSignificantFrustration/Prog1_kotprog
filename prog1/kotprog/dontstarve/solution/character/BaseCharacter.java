package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.utility.Position;

/**
 * Egy egyszerű karakter leírására szolgáló interface.
 */
public interface BaseCharacter {

    void dropItem(int index);

    /**
     * A karakter mozgási sebességének lekérdezésére szolgáló metódus.
     *
     * @return a karakter mozgási sebessége
     */
    float getSpeed();

    /**
     * A karakter jóllakottságának mértékének lekérdezésére szolgáló metódus.
     *
     * @return a karakter jóllakottsága
     */
    float getHunger();

    void setHunger(float newVal);

    /**
     * A karakter életerejének lekérdezésére szolgáló metódus.
     *
     * @return a karakter életereje
     */
    float getHp();

    void setHp(float newVal);

    /**
     * A karakter inventory-jának lekérdezésére szolgáló metódus.
     * <br>
     * A karakter inventory-ja végig ugyanaz marad, amelyet referencia szerint kell visszaadni.
     *
     * @return a karakter inventory-ja
     */
    BaseInventory getInventory();

    /**
     * A játékos aktuális pozíciójának lekérdezésére szolgáló metódus.
     *
     * @return a játékos pozíciója
     */
    Position getCurrentPosition();

    void setCurrentPosition(Position newPos);

    /**
     * Az utolsó cselekvés lekérdezésére szolgáló metódus.
     * <br>
     * Egy létező Action-nek kell lennie, nem lehet null.
     *
     * @return az utolsó cselekvés
     */
    Action getLastAction();

    void setLastAction(Action action);

    /**
     * A játékos nevének lekérdezésére szolgáló metódus.
     *
     * @return a játékos neve
     */
    String getName();
}

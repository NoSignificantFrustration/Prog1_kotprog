package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;

/**
 * A pálya egy általános mezőjét leíró interface.
 */
public interface BaseField {
    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mező járható-e.
     *
     * @return igaz, amennyiben a mező járható; hamis egyébként
     */
    boolean isWalkable();

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e fa.
     *
     * @return igaz, amennyiben van fa; hamis egyébként
     */
    boolean hasTree();

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e kő.
     *
     * @return igaz, amennyiben van kő; hamis egyébként
     */
    boolean hasStone();

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e gally.
     *
     * @return igaz, amennyiben van gally; hamis egyébként
     */
    boolean hasTwig();

    /**
     * Ezen metódus segítségével lekérdezheő, hogy a mezőn van-e bogyó.
     *
     * @return igaz, amennyiben van bogyó; hamis egyébként
     */
    boolean hasBerry();

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e répa.
     *
     * @return igaz, amennyiben van répa; hamis egyébként
     */
    boolean hasCarrot();

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e tűz rakva.
     *
     * @return igaz, amennyiben van tűz; hamis egyébként
     */
    boolean hasFire();

    /**
     * Ezen metódus segítségével a mezőn lévő tárgyak lekérdezhetők.<br>
     * A tömbben az a tárgy jön hamarabb, amelyik korábban került az adott mezőre.<br>
     * A karakter ha felvesz egy tárgyat, akkor a legkorábban a mezőre kerülő tárgyat fogja felvenni.<br>
     * Ha nem sikerül felvenni, akkor a (nem) felvett tárgy a tömb végére kerül.
     *
     * @return a mezőn lévő tárgyak
     */
    AbstractItem[] items();

    void removeItem(AbstractItem item);

    void addItem(AbstractItem item);

    void harvest(BaseInventory inventory);

    Field.TileType getType();

    int getHarvestTime();

    void makeFire();

    boolean fireTick();

}

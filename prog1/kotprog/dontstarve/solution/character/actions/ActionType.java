package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * A lehetséges akciók típusának enumja.
 */
public enum ActionType {
    /**
     * Nincs semmilyen cselekvés.
     */
    NONE,

    /**
     * Lépés.
     */
    STEP,

    /**
     * Interakcióba lépés az aktuális mezőn lévő tereptárggyal (favágás, kőcsákányozás, gally / bogyó / répa leszedése).
     * A mezőre eldobott itemekre nem vonatkozik, arra a COLLECT_ITEM action használható.
     */
    INTERACT,

    /**
     * A legközelebb lévő karakter megtámadása.
     */
    ATTACK,

    /**
     * Lépés egy megadott irányba, majd a legközelebb lévő karakter megtámadása.
     */
    STEP_AND_ATTACK,

    /**
     * Egy adott item lekraftolása.
     */
    CRAFT,

    /**
     * Az aktuális mezőn lévő legelső tárgy begyűjtése.
     */
    COLLECT_ITEM,

    /**
     * Egy megadott item eldobása.
     */
    DROP_ITEM,

    /**
     * Egy adott étel elfogyasztása.
     */
    EAT,

    /**
     * Egy adott tárgy kézbe vétele.
     */
    EQUIP,

    /**
     * A kézben tartott tárgy inventory-ba helyezése.
     */
    UNEQUIP,

    /**
     * Egy adott étel megfőzése.
     */
    COOK,

    /**
     * Egy adott item mozgatása az inventory-ban.
     */
    MOVE_ITEM,

    /**
     * Két item pozíciójának megcserélése az inventory-ban.
     */
    SWAP_ITEMS,

    /**
     * Két item egyesítése az inventory-ban.
     */
    COMBINE_ITEMS
}

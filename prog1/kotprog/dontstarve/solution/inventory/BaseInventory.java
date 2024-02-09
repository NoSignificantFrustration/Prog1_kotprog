package prog1.kotprog.dontstarve.solution.inventory;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

/**
 * Egy egyszerű Inventory-t leíró interface.
 */
public interface BaseInventory {
    /**
     * Egy item hozzáadása az inventory-hoz.<br>
     * Ha a hozzáadni kívánt tárgy halmozható, akkor a meglévő stack-be kerül (ha még fér, különben új stacket kezd),
     * egyébként a legelső új helyre kerül.<br>
     * Ha egy itemből van több megkezdett stack, akkor az inventory-ban hamarabb következőhöz adjuk hozzá
     * (ha esetleg ott nem fér el mind, akkor azt feltöltjük, és utána folytatjuk a többivel).<br>
     * Ha az adott itemből nem fér el mind az inventory-ban, akkor ami elfér azt adjuk hozzá, a többit pedig nem
     * (ebben az esetben a hívó félnek tudnia kell, hogy mennyit nem sikerült hozzáadni).
     *
     * @param item a hozzáadni kívánt tárgy
     * @return igaz, ha sikerült hozzáadni a tárgyat teljes egészében; hamis egyébként
     */
    boolean addItem(AbstractItem item);

    /**
     * Egy tárgy kidobása az inventory-ból.
     * Hatására a tárgy eltűnik az inventory-ból.
     *
     * @param index a slot indexe, amelyen lévő itemet szeretnénk eldobni
     * @return az eldobott item
     */
    AbstractItem dropItem(int index);

    /**
     * Bizonyos mennyiségű, adott típusú item törlése az inventory-ból. A törölt itemek véglegesen eltűnnek.<br>
     * Ha nincs elegendő mennyiség, akkor nem történik semmi.<br>
     * Az item törlése a legkorábban lévő stackből (stackekből) történik, akkor is, ha van másik megkezdett stack.<br>
     *
     * @param type   a törlendő item típusa
     * @param amount a törlendő item mennyisége
     * @return igaz, amennyiben a törlés sikeres volt
     */
    boolean removeItem(ItemType type, int amount);

    /**
     * Két item pozíciójának megcserélése az inventory-ban.<br>
     * Csak akkor használható, ha mind a két pozíción már van item.
     *
     * @param index1 a slot indexe, amelyen az első item található
     * @param index2 a slot indexe, amelyen a második item található
     * @return igaz, ha sikerült megcserélni a két tárgyat; hamis egyébként
     */
    boolean swapItems(int index1, int index2);

    /**
     * Egy item átmozgatása az inventory egy másik pozíciójára.<br>
     * Csak akkor használható, ha az eredeti indexen van tárgy, az új indexen viszont nincs.
     *
     * @param index    a mozgatni kívánt item pozíciója az inventory-ban
     * @param newIndex az új pozíció, ahova mozgatni szeretnénk az itemet
     * @return igaz, ha sikerült a mozgatás; hamis egyébként
     */
    boolean moveItem(int index, int newIndex);

    /**
     * Két azonos típusú tárgy egyesítése.<br>
     * Csak stackelhető tárgyakra használható. Ha a két stack méretének összege a maximális stack méreten belül van,
     * akkor az egyesítés az első pozíción fog megtörténni. Ha nem, akkor az első pozíción lévő stack maximálisra
     * töltődik, a másikon pedig a maradék marad.<br>
     *
     * @param index1 első item pozíciója az inventory-ban
     * @param index2 második item pozíciója az inventory-ban
     * @return igaz, ha sikerült az egyesítés (változott valami a művelet hatására)
     */
    boolean combineItems(int index1, int index2);

    /**
     * Egy item felvétele a karakter kezébe.<br>
     * Csak felvehető tárgyra használható. Ilyenkor az adott item a karakter kezébe kerül.
     * Ha a karakternek már tele volt a keze, akkor a kezében lévő item a most felvett item helyére kerül
     * (tehát gyakorlatilag helyet cserélnek).
     *
     * @param index a kézbe venni kívánt tárgy pozíciója az inventory-ban
     * @return igaz, amennyiben az itemet sikerült a kezünkbe venni
     */
    boolean equipItem(int index);

    /**
     * A karakter kezében lévő tárgy inventory-ba helyezése.<br>
     * A karakter kezében lévő item az inventory első szabad pozíciójára kerül.
     * Ha a karakternek üres volt a keze, akkor nem történik semmi.<br>
     * Ha nincs az inventory-ban hely, akkor a levett item a pálya azon mezőjére kerül, ahol a karakter állt.
     *
     * @return a levetett item, amennyiben az nem fért el az inventory-ban; null egyébként
     */
    EquippableItem unequipItem();

    /**
     * Egy item megfőzése.<br>
     * Csak nyers étel főzhető meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     *
     * @param index A megfőzni kívánt item pozíciója az inventory-ban
     * @return A megfőzni kívánt item típusa
     */
    ItemType cookItem(int index);

    /**
     * Egy item elfogyasztása.<br>
     * Csak ételek ehetők meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     *
     * @param index A megenni kívánt item pozíciója az inventory-ban
     * @return A megenni kívánt item típusa
     */
    ItemType eatItem(int index);

    /**
     * A rendelkezésre álló üres inventory slotok számának lekérdezése.
     *
     * @return az üres inventory slotok száma
     */
    int emptySlots();

    /**
     * Az aktuálisan viselt tárgy lekérdezése.<br>
     * Ha a karakter jelenleg egy tárgyat sem visel, akkor null.<br>
     *
     * @return Az aktuálisan viselt tárgy
     */
    EquippableItem equippedItem();

    int countItem(ItemType type);

    /**
     * Adott inventory sloton lévő tárgy lekérdezése.<br>
     * Az inventory-ban lévő legelső item indexe 0, a következőé 1, és így tovább.<br>
     * Ha az adott pozíció üres, akkor null.<br>
     *
     * @param index a lekérdezni kívánt pozíció
     * @return az adott sloton lévő tárgy
     */
    AbstractItem getItem(int index);

    void destroyEquippedItem();


}

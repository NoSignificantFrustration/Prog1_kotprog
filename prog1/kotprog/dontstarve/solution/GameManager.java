package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.Character;
import prog1.kotprog.dontstarve.solution.character.CharacterActionManager;
import prog1.kotprog.dontstarve.solution.character.EnemyAI;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.inventory.items.*;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.level.MapColors;
import prog1.kotprog.dontstarve.solution.utility.Pathfinder;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

/**
 * A játék működéséért felelős osztály.<br>
 * Az osztály a singleton tervezési mintát valósítja meg.
 */
public final class GameManager {

    /**
     * Az osztályból létrehozott egyetlen példány (nem lehet final).
     */
    private static GameManager instance = new GameManager();
    private final CharacterActionManager actionManager = new CharacterActionManager();
    private EnemyAI enemyAI;
    private BaseField[][] level;
    private int levelX;
    private int levelY;
    private final List<BaseCharacter> characters = new ArrayList<BaseCharacter>();
    private BaseCharacter player;
    private final List<BaseField> fireFields = new ArrayList<>();
    private boolean isPlayerJoined;
    private boolean hasGameStarted;
    private boolean hasGameEnded;
    private boolean isTutorial;
    private int currentTime;
    private String playerName;
    private int livingCharacters;


    /**
     * Random objektum, amit a játék során használni lehet.
     */
    private final Random random = new Random();

    /**
     * Az osztály privát konstruktora.
     */
    private GameManager() {
    }

    /**
     * Az osztályból létrehozott példány elérésére szolgáló metódus.
     *
     * @return az osztályból létrehozott példány
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * A létrehozott random objektum elérésére szolgáló metódus.
     *
     * @return a létrehozott random objektum
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Egy karakter becsatlakozása a játékba.<br>
     * A becsatlakozásnak számos feltétele van:
     * <ul>
     *     <li>A pálya már be lett töltve</li>
     *     <li>A játék még nem kezdődött el</li>
     *     <li>Csak egyetlen emberi játékos lehet, a többi karaktert a gép irányítja</li>
     *     <li>A névnek egyedinek kell lennie</li>
     * </ul>
     *
     * @param name   a csatlakozni kívánt karakter neve
     * @param player igaz, ha emberi játékosról van szó; hamis egyébként
     * @return a karakter pozíciója a pályán, vagy (Integer.MAX_VALUE, Integer.MAX_VALUE) ha nem sikerült hozzáadni
     */
    public Position joinCharacter(String name, boolean player) {
        if (level == null || hasGameStarted || getCharacter(name) != null || (player && isPlayerJoined)) {
            return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        Position spawnPos = findSpawnPoint();

        if (spawnPos == null) {
            return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);

        }

        BaseCharacter character = new Character(name, spawnPos);

        for (int i = 0; i < 4; i++) {
            int choice = random.nextInt(0, 5);
            switch (choice) {
                case 0: {
                    character.getInventory().addItem(new ItemLog(1));
                    break;
                }
                case 1: {
                    character.getInventory().addItem(new ItemStone(1));
                    break;
                }
                case 2: {
                    character.getInventory().addItem(new ItemTwig(1));
                    break;
                }
                case 3: {
                    character.getInventory().addItem(new ItemRawBerry(1));
                    break;
                }
                case 4: {
                    character.getInventory().addItem(new ItemRawCarrot(1));
                    break;
                }
            }
        }


        characters.add(character);
        if (player) {
            isPlayerJoined = true;
            playerName = name;
            this.player = character;
        } else {
            enemyAI.addBlackBoard(character);
        }
        livingCharacters++;
        return spawnPos;
    }

    public Position findSpawnPoint() {

        Position pos = new Position(0, 0);
        for (int l = 50; l >= 0; l -= 5) {


            for (int i = 0; i < 100; i++) {
                int xPos = random.nextInt(levelX);
                int yPos = random.nextInt(levelY);
                if (level[yPos][xPos].getType() != Field.TileType.Empty) {
                    continue;
                }
                pos.setX(xPos);
                pos.setY(yPos);

                if (isTooClose(pos, l)) {
                    continue;
                }
                return pos;

            }

            for (int i = 0; i < levelY; i++) {
                for (int j = 0; j < levelX; j++) {
                    pos.setX(j);
                    pos.setY(i);
                    if (level[i][j].getType() == Field.TileType.Empty && !isTooClose(pos, l)) {
                        return pos;
                    }
                }
            }
        }
        System.err.println("Unable to find spawn point");
        return null;
    }

    private boolean isTooClose(Position pos, int minDistance) {
        for (BaseCharacter ch : characters) {
            if (pos.getDistance(ch.getCurrentPosition()) < minDistance) {
                return true;
            }
        }

        return false;
    }

    public BaseCharacter getClosestEnemy(BaseCharacter attacker) {

        float minDistance = Float.MAX_VALUE;
        BaseCharacter closestCharacter = null;

        if (attacker == player) {

            for (BaseCharacter ch : characters) {

                if (ch.getHp() <= 0 || ch == attacker) {
                    continue;
                }
                float distance = attacker.getCurrentPosition().getDistance(ch.getCurrentPosition());

                if (distance < minDistance) {
                    closestCharacter = ch;
                    minDistance = distance;
                }
            }
        } else {
            closestCharacter = player;
            minDistance = attacker.getCurrentPosition().getDistance(player.getCurrentPosition());
        }
        if (closestCharacter == null || minDistance > 2f) {
            return null;
        }
        return closestCharacter;
    }

    /**
     * Egy adott nevű karakter lekérésére szolgáló metódus.<br>
     *
     * @param name A lekérdezni kívánt karakter neve
     * @return Az adott nevű karakter objektum, vagy null, ha már a karakter meghalt vagy nem is létezett
     */
    public BaseCharacter getCharacter(String name) {
        for (BaseCharacter ch : characters) {
            if (ch.getName().equals(name)) {
                if (ch.getHp() > 0) {
                    return ch;
                } else {
                    break;
                }
            }
        }

        return null;

    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy hány karakter van még életben.
     *
     * @return Az életben lévő karakterek száma
     */
    public int remainingCharacters() {
        return livingCharacters;
    }

    /**
     * Ezen metódus segítségével történhet meg a pálya betöltése.<br>
     * A pálya betöltésének azelőtt kell megtörténnie, hogy akár 1 karakter is csatlakozott volna a játékhoz.<br>
     * A pálya egyetlen alkalommal tölthető be, később nem módosítható.
     *
     * @param level a fájlból betöltött pálya
     */
    public void loadLevel(Level level) {
        if (this.level != null || level == null) {
            return;
        }


        levelX = level.getWidth();
        levelY = level.getHeight();
        this.level = new BaseField[levelY][levelX];
        BitSet traversableSet = new BitSet();
        traversableSet.set(0, levelX * levelY);

        int twig = 0;

        for (int i = 0; i < levelY; i++) {
            for (int j = 0; j < levelX; j++) {
                //System.out.println(String.format("0x%08X", level.getColor(i, j)));

                switch (level.getColor(j, i)) {
                    case 0xFF32C832:
                        this.level[i][j] = new Field(Field.TileType.Empty);
                        break;
                    case 0xFF3264C8:
                        this.level[i][j] = new Field(Field.TileType.Water);
                        traversableSet.set(levelX * i + j, false);
                        break;
                    case 0xFFC86432:
                        this.level[i][j] = new Field(Field.TileType.Tree);
                        break;
                    case 0xFFC8C8C8:
                        this.level[i][j] = new Field(Field.TileType.Boulder);
                        break;
                    case 0xFFF0B478:
                        this.level[i][j] = new Field(Field.TileType.Twig);
                        twig++;
                        break;
                    case 0xFFFF0000:
                        this.level[i][j] = new Field(Field.TileType.Berry);
                        break;
                    case 0xFFFAC800:
                        this.level[i][j] = new Field(Field.TileType.Carrot);
                        break;

                }

            }
        }
        enemyAI = new EnemyAI(new Pathfinder(levelX, levelY, traversableSet));


    }

    /**
     * A pálya egy adott pozícióján lévő mező lekérdezésére szolgáló metódus.
     *
     * @param x a vízszintes (x) irányú koordináta
     * @param y a függőleges (y) irányú koordináta
     * @return az adott koordinátán lévő mező
     */
    public BaseField getField(int x, int y) {
        return level[y][x];
    }

    /**
     * A játék megkezdésére szolgáló metódus.<br>
     * A játék csak akkor kezdhető el, ha legalább 2 karakter már a pályán van,
     * és közülük pontosan az egyik az emberi játékos által irányított karakter.
     *
     * @return igaz, ha sikerült elkezdeni a játékot; hamis egyébként
     */
    public boolean startGame() {
        if (remainingCharacters() > 1 && isPlayerJoined && !hasGameStarted) {
            hasGameStarted = true;
            return true;
        }

        return false;
    }

    /**
     * Ez a metódus jelzi, hogy 1 időegység eltelt.<br>
     * A metódus először lekezeli a felhasználói inputot, majd a gépi ellenfelek cselekvését végzi el,
     * végül eltelik egy időegység.<br>
     * Csak akkor csinál bármit is, ha a játék már elkezdődött, de még nem fejeződött be.
     *
     * @param action az emberi játékos által végrehajtani kívánt akció
     */
    public void tick(Action action) {
        if (!hasGameStarted || hasGameEnded) {
            return;
        }

        for (int i = 0; i < fireFields.size(); ) {
            if (fireFields.get(i).fireTick()) {
                i++;
            } else {
                fireFields.remove(i);
            }
        }


        if (player.getHp() <= 0) {
            hasGameEnded = true;
            return;
        }

        actionManager.doCharacterAction(player, action);
        //System.out.println(player.getName());


        for (BaseCharacter ch : characters) {
            if (ch.getHp() > 0) {

                if (ch.getInventory().equippedItem() instanceof ItemTorch torch) {
                    torch.use();

                    if (torch.percentage() <= 0) {
                        ch.getInventory().destroyEquippedItem();
                    }
                }

                if (!isTutorial && ch != player) {
                    actionManager.doCharacterAction(ch, enemyAI.makeDecision(ch));
                    //System.out.println(ch.getName());
                }
                ch.setHunger(ch.getHunger() - 0.4f);
                if (ch.getHunger() <= 0) {
                    ch.setHunger(0);
                    ch.setHp(ch.getHp() - 5);
                } else if (ch.getHunger() > 100) {
                    ch.setHunger(100);
                }
            }
        }


        currentTime++;
    }

    public void addFire(BaseField field) {
        if (!fireFields.contains(field)) {
            fireFields.add(field);
        }
    }

    public void characterDeath(BaseCharacter character) {
        livingCharacters--;
        if (character.getName().equals(playerName) || livingCharacters == 1) {
            hasGameEnded = true;
        }
    }


    /**
     * Ezen metódus segítségével lekérdezhető az aktuális időpillanat.<br>
     * A játék kezdetekor ez az érték 0 (tehát a legelső időpillanatban az idő 0),
     * majd minden eltelt időpillanat után 1-gyel növelődik.
     *
     * @return az aktuális időpillanat
     */
    public int time() {
        return currentTime;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük a játék győztesét.<br>
     * Amennyiben a játéknak még nincs vége (vagy esetleg nincs győztes), akkor null-t ad vissza.
     *
     * @return a győztes karakter vagy null
     */
    public BaseCharacter getWinner() {
        if (!isGameEnded() || getCharacter(playerName) == null) {
            return null;
        }
        return getCharacter(playerName);
    }

    public BaseCharacter getPlayer() {
        return player;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék elkezdődött-e már.
     *
     * @return igaz, ha a játék már elkezdődött; hamis egyébként
     */
    public boolean isGameStarted() {
        return hasGameStarted;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék befejeződött-e már.
     *
     * @return igaz, ha a játék már befejeződött; hamis egyébként
     */
    public boolean isGameEnded() {
        return hasGameEnded;
    }

    /**
     * Ezen metódus segítségével beállítható, hogy a játékot tutorial módban szeretnénk-e elindítani.<br>
     * Alapértelmezetten (ha nem mondunk semmit) nem tutorial módban indul el a játék.<br>
     * Tutorial módban a gépi karakterek nem végeznek cselekvést, csak egy helyben állnak.<br>
     * A tutorial mód beállítása még a karakterek csatlakozása előtt történik.
     *
     * @param tutorial igaz, amennyiben tutorial módot szeretnénk; hamis egyébként
     */
    public void setTutorial(boolean tutorial) {
        this.isTutorial = tutorial;
    }

    public int getLevelX() {
        return levelX;
    }

    public int getLevelY() {
        return levelY;
    }

    public EnemyAI getEnemyAI() {
        return enemyAI;
    }
}

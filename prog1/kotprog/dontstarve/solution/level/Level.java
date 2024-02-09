package prog1.kotprog.dontstarve.solution.level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A pályát leíró kép betöltéséért felelő osztály.
 */
public class Level {
    /**
     * A kép, ami a pályát tartalmazza.
     */
    private BufferedImage image;

    /**
     * Konstruktor, amely a megadott fájlból beolvassa a pályát.
     *
     * @param fileName a fájl, amely a pályát tartalmazza
     */
    public Level(String fileName) {
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.err.println("A pálya beolvasása nem sikerült!");
        }
    }

    /**
     * A pálya magasságát lekérdező metódus.
     *
     * @return a pálya magassága
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * A pálya szélességét lekérdező metódus.
     *
     * @return a pály szélessége
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * A pályát reprezentáló kép egy adott pixelének színét lekérdező metódus.<br>
     *
     * @param x a képpont oszlopszáma
     * @param y a képpont sorszáma
     * @return a kép adott pozíciójának színe
     */
    public int getColor(int x, int y) {
        return image.getRGB(x, y);
    }
}

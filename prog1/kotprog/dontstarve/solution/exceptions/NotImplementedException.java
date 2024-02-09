package prog1.kotprog.dontstarve.solution.exceptions;

/**
 * Kivétel osztály, amit a (még) nem implementált metódusok dobnak.
 * Amennyiben az összes metódus meg van valósítva, nincs rá szükség.
 */
public class NotImplementedException extends RuntimeException {
    /**
     * Default konstruktor, mellyel az objektum létrehozható.
     */
    public NotImplementedException() {
        super("A metódus még nem lett implementálva");
    }
}

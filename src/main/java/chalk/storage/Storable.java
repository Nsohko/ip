package chalk.storage;

/**
 * The Storable interface represents an object that can be stored in a file.
 */
public interface Storable {

    /**
     * Returns the representation of the object to be stored in the file as a
     * string
     */
    public abstract String toFileStorage();
}

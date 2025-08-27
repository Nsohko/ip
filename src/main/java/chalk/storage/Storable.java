package chalk.storage;

public interface Storable {

    /**
     * Returns the representation of the object to be stored in the file as a
     * string
     */
    public abstract String toFileStorage();
}

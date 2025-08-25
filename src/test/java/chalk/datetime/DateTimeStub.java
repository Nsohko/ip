package chalk.datetime;

/**
 * A test stub for DateTime that returns fixed strings for toString() and
 * toFileStorage().
 *
 * Put this in src/test/java so it's only used by tests.
 */
public class DateTimeStub extends DateTime {

    private final String stringValue;
    private final String storageValue;

    /**
     * @param stringValue value returned by toString()
     * @param storageValue value returned by toFileStorage()
     */
    public DateTimeStub(String storageValue, String stringValue) {
        super(storageValue);
        this.stringValue = stringValue;
        this.storageValue = storageValue;
    }

    public DateTimeStub() {
        this("19/04/2025 1800", "19 April 2025 1800hrs");
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public String toFileStorage() {
        return storageValue;
    }
}

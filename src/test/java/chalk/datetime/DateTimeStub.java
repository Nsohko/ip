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
    private final int orderValue; // used for before / after calculations

    /**
     * @param stringValue value returned by toString()
     * @param storageValue value returned by toFileStorage()
     */
    public DateTimeStub(String storageValue, String stringValue) {
        this(storageValue, stringValue, 0);
    }

    /**
     * @param stringValue value returned by toString()
     * @param storageValue value returned by toFileStorage()
     * @param orderValue value used to determine ordering (before/after)
     */
    public DateTimeStub(String storageValue, String stringValue, int orderValue) {
        super("1/1/1970 0000"); // dummy value, won't be used
        this.stringValue = stringValue;
        this.storageValue = storageValue;
        this.orderValue = orderValue;
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

    @Override
    public boolean isBefore(DateTime other) {
        return this.orderValue < ((DateTimeStub) other).orderValue;
    }

    @Override
    public boolean isAfter(DateTime other) {
        return this.orderValue > ((DateTimeStub) other).orderValue;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DateTimeStub that = (DateTimeStub) other;
        return this.orderValue == that.orderValue;
    }
}

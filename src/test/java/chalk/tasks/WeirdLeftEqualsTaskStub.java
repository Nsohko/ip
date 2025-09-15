package chalk.tasks;

/**
 * Asymmetric equals (for testing checkConflict OR logic):
 * Left.equals(Right) returns true; everything else false.
 */
class WeirdLeftEqualsTaskStub extends Task {
    WeirdLeftEqualsTaskStub(String name) { super(name); }

    @Override
    public boolean equals(Object other) {
        return other instanceof WeirdRightEqualsTaskStub;
    }
}

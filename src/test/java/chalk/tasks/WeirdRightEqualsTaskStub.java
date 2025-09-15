package chalk.tasks;

/**
 * Asymmetric counterpart that never returns true.
 */
class WeirdRightEqualsTaskStub extends Task {
    WeirdRightEqualsTaskStub(String name) { super(name); }

    @Override
    public boolean equals(Object other) {
        return false;
    }
}

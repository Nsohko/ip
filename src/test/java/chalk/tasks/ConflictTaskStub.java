package chalk.tasks;

import java.util.Set;

/**
 * A Task stub whose conflict behavior is name-based:
 * it conflicts with any other task whose name is in `conflictWithNames`.
 */
class ConflictTaskStub extends TaskStub {
    private final Set<String> conflictWithNames;

    ConflictTaskStub(String name, String... conflictWith) {
        super(name);
        this.conflictWithNames = Set.of(conflictWith);
    }

    @Override
    public boolean checkConflict(Task other) {
        return conflictWithNames.contains(other.getName());
    }
}

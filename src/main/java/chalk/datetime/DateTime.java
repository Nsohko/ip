package chalk.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import chalk.storage.Storable;

public class DateTime implements Storable {

    private final LocalDateTime dateTime;

    public DateTime(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime parsedDateTime = LocalDateTime.parse(input, formatter);

        this.dateTime = parsedDateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM uuuu HHmm'hrs'");
        return this.dateTime.format(formatter);
    }

    @Override
    public String toFileStorage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return this.dateTime.format(formatter);
    }
}

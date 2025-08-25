package chalk.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import chalk.datetime.DateTime;
import chalk.tasks.Deadline;
import chalk.tasks.Event;
import chalk.tasks.Task;
import chalk.tasks.TaskList;
import chalk.tasks.Todo;

class FileStorageTest {

    @Test
    void load_noFile_createsMissingFileandReturnsEmptyList(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("Storage.txt");
        FileStorage storage = new FileStorage(file.toString());

        TaskList list = storage.load();

        assertEquals(0, list.size());
        assertTrue(Files.exists(file));
        assertTrue(Files.isRegularFile(file));
    }

    @Test
    void load_normalInput_readsValidLines(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("Storage.txt");
        String content = String.join("\n",
                "todo buy milk | 0",
                "deadline return book /by 6/6/2025 1820 | 1",
                "event project meeting /from 1/1/2025 0900 /to 1/1/2025 1000 | 0",
                ""
        );
        Files.writeString(file, content);

        FileStorage storage = new FileStorage(file.toString());
        TaskList list = storage.load();

        assertEquals(3, list.size());
        // Light-touch checks via string output (keeps it simple)
        String printed = list.toString();
        assertEquals("""
        1. [T][ ] buy milk
        2. [D][X] return book (by: 6 June 2025 1820hrs)
        3. [E][ ] project meeting (from: 1 January 2025 0900hrs to: 1 January 2025 1000hrs)
        """, printed);
    }

    @Test
    void load_missingSeparator_throwsOnMalformedLine(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("Storage.txt");
        // No " | " separator → should trigger IOException in load()
        Files.writeString(file, "todo buy milk\n");

        FileStorage storage = new FileStorage(file.toString());
        IOException ex = assertThrows(IOException.class, storage::load);
        assertTrue(ex.getMessage().contains("Error parsing task data"));
    }

    @Test
    void load_skipsUnknownOrBadCommandbutKeepsOthers(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("Storage.txt");
        // Second line has an unknown command; load() should skip it and keep the valid ones
        String content = String.join("\n",
                "todo buy milk | 0",
                "remind me to sleep | 0", // Unknown → skipped
                "todo read book | 1",
                ""
        );
        Files.writeString(file, content);

        FileStorage storage = new FileStorage(file.toString());
        TaskList list = storage.load();

        assertEquals(2, list.size());
        String printed = list.toString();
        assertEquals("""
                    1. [T][ ] buy milk
                    2. [T][X] read book
                    """, printed);
    }

    @Test
    void addTask_appendsLine(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("Storage.txt");
        FileStorage storage = new FileStorage(file.toString());

        // Ensure file exists and is empty
        storage.load();

        Task t1 = new Todo("buy milk");
        storage.addTask(t1);

        String text = Files.readString(file);
        assertTrue(text.contains("todo buy milk | 0"));

        Task t2 = new Deadline("return book", new DateTime("6/6/2025 1820"));
        t2.markAsDone();
        storage.addTask(t2);

        text = Files.readString(file);
        assertTrue(text.contains("deadline return book /by 6/6/2025 1820 | 1"));
    }

    @Test
    void overWriteWithTaskList_writesExactlyTaskList(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("Storage.txt");
        FileStorage storage = new FileStorage(file.toString());

        TaskList list = new TaskList();
        list.addTask(new Todo("buy milk"));
        Task d = new Deadline("return book", new DateTime("6/6/2025 1820"));
        d.markAsDone();
        list.addTask(d);
        list.addTask(new Event("meet", new DateTime("1/1/2025 0900"), new DateTime("1/1/2025 1000")));

        storage.overWriteWithTaskList(list);

        String text = Files.readString(file);
        // Compare with TaskList's own serialization to avoid brittle string expectations
        assertEquals(list.toFileStorage(), text);
        // Quick spot checks
        assertTrue(text.contains("todo buy milk | 0"));
        assertTrue(text.contains("deadline return book /by 6/6/2025 1820 | 1"));
        assertTrue(text.contains("event meet /from 1/1/2025 0900 /to 1/1/2025 1000 | 0"));
    }

    @Test
    void addTask_wrapsIoError(@TempDir Path tempDir) {
        // Use a directory path as the "file"; FileWriter will fail opening a directory
        Path dirAsFile = tempDir; // directory, not a file
        FileStorage storage = new FileStorage(dirAsFile.toString());

        IOException ex = assertThrows(IOException.class, () -> storage.addTask(new Todo("x")));

        assertEquals("Failed to write Task information to file!", ex.getMessage());
    }

    @Test
    void overWriteWithTaskList_wrapsIoError(@TempDir Path tempDir) {
        // Use a directory path as the "file"; FileWriter will fail opening a directory
        FileStorage storage = new FileStorage(tempDir.toString());

        TaskList list = new TaskList();
        list.addTask(new Todo("x"));

        IOException ex = assertThrows(IOException.class, () -> storage.overWriteWithTaskList(list));

        assertEquals("Failed to update task in Storage!", ex.getMessage());
    }
}

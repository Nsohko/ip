package chalk.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    void toString_validInput_works() {
        DateTime dt = new DateTime("2/12/2019 1800");
        assertEquals("2 December 2019 1800hrs", dt.toString());
    }

    @Test
    void toFileStorage_validInput_works() {
        DateTime dt = new DateTime("6/6/2025 1820");
        assertEquals("6/6/2025 1820", dt.toFileStorage());
    }

    @Test
    void toString_leadingZeros_areCanonicalized() {
        DateTime dt = new DateTime("01/01/2020 0000");
        assertEquals("1 January 2020 0000hrs", dt.toString());
    }

    @Test
    void toFileStorage_leadingZeros_areCanonicalized() {
        DateTime dt = new DateTime("01/01/2020 0000");
        assertEquals("1 January 2020 0000hrs", dt.toString());
    }
}

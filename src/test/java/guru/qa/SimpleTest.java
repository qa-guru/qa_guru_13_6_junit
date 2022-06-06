package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleTest {

    @DisplayName("Проверяем что 3 больше, чем 2")
    @Disabled("TICKET-21341")
    @Test
    void simpleTest() {
        Assertions.assertTrue(3 > 2);
    }

    @Test
    void simpleTest1() {
        Assertions.assertTrue(3 < 2);
    }

    @Test
    void simpleTest2() {
        throw new RuntimeException("Просто другой эксепшн");
    }
}

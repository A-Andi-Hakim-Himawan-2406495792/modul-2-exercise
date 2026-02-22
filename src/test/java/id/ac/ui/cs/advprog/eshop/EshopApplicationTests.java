package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "Spring context should load");
    }

    @Test
    void mainMethodRuns() {
        assertDoesNotThrow(
                () -> EshopApplication.main(new String[] {}),
                "Main method should run without exception"
        );
    }
}
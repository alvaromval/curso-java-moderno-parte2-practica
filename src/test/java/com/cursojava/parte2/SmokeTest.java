package com.cursojava.parte2;

import com.cursojava.parte2.domain.Pedido;
import com.cursojava.parte2.legacy.LegacyService;
import com.cursojava.parte2.modern.ModernService;
import com.cursojava.parte2.modern.Outcome;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SmokeTest {
    @Test
    void legacyRuns() {
        var legacy = new LegacyService();
        try {
            String result = legacy.process(new Pedido(1L, "Ana", 120.5));
            assertNotNull(result);
            assertTrue(result.contains("LEGACY"), "Should contain LEGACY marker");
        } finally {
            legacy.shutdown();
        }
    }

    @Test
    void modernRuns() {
        var modern = new ModernService();
        Outcome outcome = modern.process(new Pedido(2L, "Ana", 120.5));
        assertNotNull(outcome);
        assertTrue(outcome instanceof Outcome.Ok || outcome instanceof Outcome.Fail);
    }
}

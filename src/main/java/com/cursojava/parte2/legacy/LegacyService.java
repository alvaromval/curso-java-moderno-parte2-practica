package com.cursojava.parte2.legacy;

import com.cursojava.parte2.domain.Pedido;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.*;

public class LegacyService {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

    public String process(Pedido pedido) {
        String requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8);
        LegacyRequestContext.set(requestId);
        try {
            final String ctx = LegacyRequestContext.get(); // “copiamos” contexto
            Future<String> pago = pool.submit(() -> withContext(ctx, () -> simularPago(pedido.total())));
            Future<String> stock = pool.submit(() -> withContext(ctx, () -> simularStock(pedido.id())));
            Future<String> envio = pool.submit(() -> withContext(ctx, () -> simularEnvio(pedido.cliente())));
            return "LEGACY_FIXED requestId=" + LegacyRequestContext.get()
                    + " | " + get(pago)
                    + " | " + get(stock)
                    + " | " + get(envio);
        } finally {
            LegacyRequestContext.clear();
        }
    }

    private String simularPago(double total) {
        dormir(Duration.ofMillis(180));
        return "pago OK total=" + total + " ctx=" + LegacyRequestContext.get();
    }

    private String simularStock(long pedidoId) {
        dormir(Duration.ofMillis(140));
        return "stock OK pedidoId=" + pedidoId + " ctx=" + LegacyRequestContext.get();
    }

    private String simularEnvio(String cliente) {
        dormir(Duration.ofMillis(220));
        return "envio OK cliente=" + cliente + " ctx=" + LegacyRequestContext.get();
    }

    private static String withContext(String ctx, Callable<String> work) {
        LegacyRequestContext.set(ctx);
        try {
            return work.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            LegacyRequestContext.clear();
        }
    }

    private static void dormir(Duration d) {
        try {
            Thread.sleep(d);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private static <T> T get(Future<T> f) {
        try {
            return f.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        pool.shutdownNow();
    }
}

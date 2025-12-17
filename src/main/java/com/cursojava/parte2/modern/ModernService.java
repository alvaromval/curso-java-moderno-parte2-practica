package com.cursojava.parte2.modern;

import com.cursojava.parte2.domain.Pedido;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.StructuredTaskScope;

public class ModernService {
    public Outcome process(Pedido pedido) {
        String requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8);
        try {
            String message = ScopedValue.where(ModernRequestContext.REQUEST_ID, requestId).call(() -> {
                try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                    var pago = scope.fork(() -> simularPago(pedido.total()));
                    var stock = scope.fork(() -> simularStock(pedido.id()));
                    var envio = scope.fork(() -> simularEnvio(pedido.cliente()));
                    scope.join();
                    scope.throwIfFailed();
                    return "MODERN requestId=" + ModernRequestContext.REQUEST_ID.get()
                            + " | " + pago.get()
                            + " | " + stock.get()
                            + " | " + envio.get();
                }
            });
            return new Outcome.Ok(message);
        } catch (Exception e) {
            return new Outcome.Fail(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private String simularPago(double total) {
        dormir(Duration.ofMillis(180));
        return "pago OK total=" + total + " ctx=" + ModernRequestContext.REQUEST_ID.get();
    }

    private String simularStock(long pedidoId) {
        dormir(Duration.ofMillis(140));
        return "stock OK pedidoId=" + pedidoId + " ctx=" + ModernRequestContext.REQUEST_ID.get();
    }

    private String simularEnvio(String cliente) {
        dormir(Duration.ofMillis(220));
        return "envio OK cliente=" + cliente + " ctx=" + ModernRequestContext.REQUEST_ID.get();
    }

    private static void dormir(Duration d) {
        try {
            Thread.sleep(d);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

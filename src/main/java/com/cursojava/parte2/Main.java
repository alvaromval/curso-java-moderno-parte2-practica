package com.cursojava.parte2;

import com.cursojava.parte2.domain.Pedido;
import com.cursojava.parte2.legacy.LegacyService;
import com.cursojava.parte2.modern.ModernService;
import com.cursojava.parte2.modern.Outcome;
import com.cursojava.parte2.modern.OutcomeFormatter;

public class Main {
    public static void main(String[] args) {
        var pedido = new Pedido(1L, "Ana", 120.50);
        var legacy = new LegacyService();
        try {
            System.out.println(legacy.process(pedido));
        } finally {
            legacy.shutdown();
        }
        var modern = new ModernService();
        Outcome outcome = modern.process(pedido);
        System.out.println(OutcomeFormatter.format(outcome));
    }
}

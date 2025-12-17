package com.cursojava.parte2.modern;

public final class OutcomeFormatter {
    private OutcomeFormatter() {
    }

    public static String format(Outcome outcome) {
        return switch (outcome) {
            case Outcome.Ok(String msg) -> " OK => " + msg;
            case Outcome.Fail(String reason) -> " FAIL => " + reason;
        };
    }
}

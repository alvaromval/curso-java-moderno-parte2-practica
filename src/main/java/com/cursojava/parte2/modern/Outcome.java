package com.cursojava.parte2.modern;

public sealed interface Outcome permits Outcome.Ok, Outcome.Fail {
    record Ok(String message) implements Outcome {
    }

    record Fail(String reason) implements Outcome {
    }
}

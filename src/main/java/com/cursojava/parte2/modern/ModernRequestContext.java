package com.cursojava.parte2.modern;

public final class ModernRequestContext {
    private ModernRequestContext() {
    }

    public static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
}

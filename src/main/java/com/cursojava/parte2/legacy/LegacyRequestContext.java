package com.cursojava.parte2.legacy;

public final class LegacyRequestContext {
    private LegacyRequestContext() {
    }

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    public static void set(String requestId) {
        REQUEST_ID.set(requestId);
    }

    public static String get() {
        return REQUEST_ID.get();
    }

    public static void clear() {
        REQUEST_ID.remove();
    }
}

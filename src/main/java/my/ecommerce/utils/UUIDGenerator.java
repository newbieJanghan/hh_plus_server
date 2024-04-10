package my.ecommerce.utils;

import java.util.UUID;

public class UUIDGenerator {
    public static UUID generate() {
        return UUID.randomUUID();
    }

    public static UUID fromString(String string) {
        return UUID.fromString(string);
    }
}

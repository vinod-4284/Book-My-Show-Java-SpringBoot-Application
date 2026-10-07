package com.example.Book_My_Show.utilitis;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SystemUtility {
    private static final Random RANDOM = new Random();
    private static final LocalDateTime referenceTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);

    public synchronized static String generate(String prefix) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        int random = 1000 + RANDOM.nextInt(9000);

        return prefix + "-" + timestamp + "-" + random;
    }

    public static Long convertShowTimeInSeconds(LocalDateTime time){
        return Duration.between(referenceTime, time).getSeconds();
    }
    
}

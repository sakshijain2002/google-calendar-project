package com.event.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    /**
     * Converts milliseconds to LocalDateTime using the system's default time zone.
     *
     * @param millis the timestamp in milliseconds
     * @return LocalDateTime representation of the timestamp
     */
    public static LocalDateTime fromMillis(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
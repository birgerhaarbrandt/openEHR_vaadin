package org.vaadin.example;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        var parsed = DateTimeFormatter.ISO_DATE_TIME.parseBest(dateTimeStr, LocalDateTime::from, OffsetDateTime::from);
        if (parsed instanceof LocalDateTime local) {
            return local;
        } else if (parsed instanceof OffsetDateTime offset) {
            return offset.toLocalDateTime();
        } else {
            throw new IllegalArgumentException("Invalid date-time string: " + dateTimeStr);
        }
    }
}

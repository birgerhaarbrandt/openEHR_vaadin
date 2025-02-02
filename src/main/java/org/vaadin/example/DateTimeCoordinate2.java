package org.vaadin.example;

import com.github.appreciated.apexcharts.helper.Coordinate;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DateTimeCoordinate2<X> extends Coordinate<Long, X> {
    // Constructor that accepts LocalDateTime for X-axis and generic type X for Y-axis
    public DateTimeCoordinate2(LocalDateTime dateTime, X... yValues) {
        // Convert LocalDateTime to milliseconds since epoch for X-axis
        ZoneId zoneId = ZoneId.systemDefault();
        Long xMillis = dateTime.atZone(zoneId).toInstant().toEpochMilli();
        setX(xMillis);
        setY(yValues);
    }
}

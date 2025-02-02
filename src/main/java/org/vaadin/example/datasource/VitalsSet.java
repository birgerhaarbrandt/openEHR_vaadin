package org.vaadin.example.datasource;

import java.time.LocalDateTime;

public abstract class VitalsSet {
    private final LocalDateTime dateTime;

    protected VitalsSet(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

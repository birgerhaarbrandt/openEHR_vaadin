package org.vaadin.example.datasource;

import java.time.LocalDateTime;

public class BodyHeightSet extends VitalsSet {
    private final Double height;

    public Double getHeight() {
        return height;
    }

    public BodyHeightSet(LocalDateTime dateTime, Double height) {
        super(dateTime);
        this.height = height;
    }
}

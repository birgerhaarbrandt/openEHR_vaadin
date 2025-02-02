package org.vaadin.example.datasource;

import java.time.LocalDateTime;

public class BodyWeightSet extends VitalsSet {
    private final Double weight;

    public Double getWeight() {
        return weight;
    }

    public BodyWeightSet(LocalDateTime dateTime, Double weight) {
        super(dateTime);
        this.weight = weight;
    }
}

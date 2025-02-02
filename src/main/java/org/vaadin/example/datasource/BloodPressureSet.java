package org.vaadin.example.datasource;

import java.time.LocalDateTime;

public class BloodPressureSet extends VitalsSet {
    private final Double systolic;
    private final Double diastolic;

    public BloodPressureSet(LocalDateTime dateTime, Double systolic, Double diastolic) {
        super(dateTime);
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public Double getSystolic() {
        return systolic;
    }

    public Double getDiastolic() {
        return diastolic;
    }
}

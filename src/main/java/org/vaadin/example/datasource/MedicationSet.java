package org.vaadin.example.datasource;

public class MedicationSet {

//    private Double frequency;
//    private String frequencyUnit;

//    private Double dose;
//    private String doseUnit;

    private final String name;
    private final String form;
    private final String date;
    private final String repetition;
    private final String source;

    public MedicationSet(String name, String form, String date, String repetition, String source) {
        this.name = name;
        this.form = form;
        this.date = date;
        this.repetition = repetition;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getForm() {
        return form;
    }

    public String getDate() {
        return date;
    }

    public String getRepetition() {
        return repetition;
    }

    public String getSource() {
        return source;
    }
}

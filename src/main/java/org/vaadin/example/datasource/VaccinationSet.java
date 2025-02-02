package org.vaadin.example.datasource;

public class VaccinationSet {
    private final String name;
    private final String date;
    private final String formattedDate;
    private final String route;
    private final String site;
    private final String sequence;
    private final String source;

    public VaccinationSet(String name, String date, String route, String site, String sequence, String formattedDate, String source) {
        this.name = name;
        this.date = date;
        this.route = route;
        this.site = site;
        this.sequence = sequence;
        this.formattedDate = formattedDate;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getRoute() {
        return route;
    }

    public String getSite() {
        return site;
    }

    public String getSequence() {
        return sequence;
    }

    public String getSource() {
        return source;
    }
}

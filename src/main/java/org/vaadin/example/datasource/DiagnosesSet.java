package org.vaadin.example.datasource;

public class DiagnosesSet {

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getTimeOfOnset() {
        return timeOfOnset;
    }

    public void setTimeOfOnset(String timeofOnset) {
        timeOfOnset = timeofOnset;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDiagnosisCertainty() {
        return diagnosisCertainty;
    }

    public void setDiagnosisCertainty(String diagnosisCertainty) {
        this.diagnosisCertainty = diagnosisCertainty;
    }

    private String diagnosisName;
    private String timeOfOnset;
    private String active;
    private String diagnosisCertainty;


}

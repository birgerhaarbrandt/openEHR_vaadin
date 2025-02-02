package org.vaadin.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.datasource.DataService;
import org.vaadin.example.datasource.DiagnosesSet;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Diagnoses Data")
@Route(value = "Diagnoses", layout = MainView.class)
public class DiagnosesService extends VerticalLayout {

    DataService dataService;
    public DiagnosesService(DataService dataService) {

        this.dataService = dataService;

        Grid<DiagnosesSet> diagnosisGrid = new Grid<>(DiagnosesSet.class);
        diagnosisGrid.setColumns("diagnosisName", "timeOfOnset", "active", "diagnosisCertainty");

        setSizeFull();
        setFlexGrow(1, diagnosisGrid);
        diagnosisGrid.setSizeFull();

        List<DiagnosesSet> diagnosesSet = dataService.fetchDiagnoses();
        diagnosisGrid.setItems(diagnosesSet);

        diagnosisGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        add(diagnosisGrid);

    }



}

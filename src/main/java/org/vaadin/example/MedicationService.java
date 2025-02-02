package org.vaadin.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.datasource.DataService;
import org.vaadin.example.datasource.MedicationSet;

import java.io.Serial;
import java.util.List;

@PageTitle("Medication")
@Route(value = "medication", layout = MainView.class)
public final class MedicationService extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = -5669542818105962712L;

    public MedicationService(DataService dataService) {

        // Create Grid for Medication Data
        Grid<MedicationSet> medicationGrid = new Grid<>(MedicationSet.class);

        setSizeFull();
        setFlexGrow(1.0, medicationGrid);
        medicationGrid.setSizeFull();

        // Set columns to display specific fields
        medicationGrid.setColumns("name", "form", "date", "repetition", "source");

        List<MedicationSet> medicationSets = dataService.fetchMedication();

        medicationGrid.setItems(medicationSets);

        // Add the grid to the layout
        add(medicationGrid);
    }
}

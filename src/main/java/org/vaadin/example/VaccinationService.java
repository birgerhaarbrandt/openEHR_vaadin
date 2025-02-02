package org.vaadin.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.datasource.DataService;
import org.vaadin.example.datasource.VaccinationSet;

import java.io.Serial;
import java.util.List;

@PageTitle("Vaccination Data")
@Route(value = "vaccination", layout = MainView.class)
public final class VaccinationService extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = -5031685160805842681L;

    public VaccinationService(DataService dataService) {


        Grid<VaccinationSet> vaccinationGrid = new Grid<>(VaccinationSet.class);
        vaccinationGrid.setColumns("formattedDate", "name", "route", "site", "sequence", "source");

        setSizeFull();
        setFlexGrow(1.0, vaccinationGrid);
        vaccinationGrid.setSizeFull();

        List<VaccinationSet> vaccinationSets = dataService.fetchVaccination();
        vaccinationGrid.setItems(vaccinationSets);

        vaccinationGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        add(vaccinationGrid);
    }
}

package org.vaadin.example;

import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.datasource.BloodPressureSet;
import org.vaadin.example.datasource.BodyHeightSet;
import org.vaadin.example.datasource.BodyWeightSet;
import org.vaadin.example.datasource.DataService;

import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@PageTitle("Dashboard")
@Route(value = "dashboard", layout = MainView.class)
public final class DashbordService extends VerticalLayout {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy", Locale.UK);

    @Serial
    private static final long serialVersionUID = -8267227857153102223L;

    public DashbordService(DataService dataService) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignSelf(Alignment.START);

        List<BodyWeightSet> weights = dataService.fetchBodyWeight();
        List<BodyHeightSet> heights = dataService.fetchBodyHeight();

        Tile bodyWeight = new Tile("Weight (kg)", VaadinIcon.SCALE,
                                   weights.get(weights.size() - 1).getWeight(),
                                   dateTimeFormatter.format(weights.get(weights.size() - 1).getDateTime()));
        bodyWeight.setTileColor("LightGray");
        //add(bodyWeight);

        Tile bodyHeight = new Tile("Height (cm)", VaadinIcon.ARROW_UP,
                                   heights.get(heights.size() - 1).getHeight(),
                                   dateTimeFormatter.format(heights.get(heights.size() - 1).getDateTime()));
        bodyHeight.setTileColor("Gainsboro");
        //add(bodyHeight);
        horizontalLayout.add(bodyHeight, bodyWeight);
        horizontalLayout.setAlignSelf(Alignment.START);
        add(horizontalLayout);

        setAlignSelf(Alignment.START);

        addVitalsChart(
                dataService.fetchBloodPressureEhrBase(),
                dataService.fetchBodyWeightEhrBase(),
                dataService.fetchBodyHeightEhrBase(),
                "EhrBase");

    }

    private void addVitalsChart(
            List<BloodPressureSet> bloodPressureData,
            List<BodyWeightSet> weights,
            List<BodyHeightSet> heights,
            String nameSuffix) {
        var systolicArray = bloodPressureData.stream()
                .map(measurement -> new DateTimeCoordinate2<>(measurement.getDateTime(), measurement.getSystolic()))
                .toArray(DateTimeCoordinate2[]::new);
        var diastolicArray = bloodPressureData.stream()
                .map(measurement -> new DateTimeCoordinate2<>(measurement.getDateTime(), measurement.getDiastolic()))
                .toArray(DateTimeCoordinate2[]::new);
        var weightArray = weights.stream()
                .map(measurement -> new DateTimeCoordinate2<>(measurement.getDateTime(), measurement.getWeight()))
                .toArray(DateTimeCoordinate2[]::new);
        var heightArray = heights.stream()
                .map(measurement -> new DateTimeCoordinate2<>(measurement.getDateTime(), measurement.getHeight()))
                .toArray(DateTimeCoordinate2[]::new);

        setSizeFull();
        setPadding(true);
        setSpacing(true);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        ApexCharts lineChart = new LineMultiYAxesChartExample(systolicArray, diastolicArray, weightArray, heightArray, nameSuffix).build();
        lineChart.setHeight(500.0f, Unit.PIXELS);
        add(lineChart);
    }
}

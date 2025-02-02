package org.vaadin.example;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.RadialBarBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.radialbar.builder.HollowBuilder;

public class RadialBarChartExample extends ApexChartsBuilder {

    public RadialBarChartExample() {
        withChart(ChartBuilder.get()
                .withType(Type.RADIALBAR)
                .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withRadialBar(RadialBarBuilder.get()
                                .withHollow(HollowBuilder.get()
                                        .withSize("30%")
                                        .build())
                                .build())
                        .build())
                .withSeries(70.0, 90.0)
                .withLabels("Circket", "Test");
    }
}
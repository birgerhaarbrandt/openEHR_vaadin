package org.vaadin.example;

import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.GridBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.config.yaxis.builder.AxisBorderBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import java.util.List;

public final class LineMultiYAxesChartExample extends ApexChartsBuilder {
    public LineMultiYAxesChartExample(
            DateTimeCoordinate2<Double>[] systolic,
            DateTimeCoordinate2<Double>[] diastolic,
            DateTimeCoordinate2<Double>[] weight,
            DateTimeCoordinate2<Double>[] height,
            String nameSuffix
    ) {
        withChart(ChartBuilder.get()
                          .withType(Type.LINE)  // Set chart type to line
                          .withZoom(ZoomBuilder.get().withEnabled(false).build())
                          .build())
                .withStroke(StrokeBuilder.get()
                                    .withCurve(Curve.STRAIGHT)  // Use straight lines for the chart
                                    .withWidthArray(List.of(6.0, 3.0))  // Define width of the lines
                                    .build())
                .withGrid(GridBuilder.get()
                                  .withRow(RowBuilder.get()
                                                   .withColors("#f3f3f3", "transparent")
                                                   .withOpacity(0.5).build()
                                  ).build())
                // X-Axis should show dates
                .withXaxis(XAxisBuilder.get()
                                   .withType(XAxisType.DATETIME)  // Define X-axis as a DATETIME axis
                                   .build())
                // Y-Axis for numeric values
                .withYaxis(YAxisBuilder.get()
                                   .withTitle(TitleBuilder.get().withText("Values " + nameSuffix).build())  // Title for Y-axis
                                   .withAxisBorder(AxisBorderBuilder.get().withShow(true).build())  // Show the border for the axis
                                   .build()
                )
                .withSeries(
                        // Series where X is datetime and Y is the value
                        new Series<>("Systolic", systolic),
                        new Series<>("Diastolic", diastolic),
                        new Series<>("Weight", weight),
                        new Series<>("Height", height)
                );
    }
}

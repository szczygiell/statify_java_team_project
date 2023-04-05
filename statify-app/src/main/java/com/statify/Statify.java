package com.statify;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.util.ArrayList;
import java.util.Arrays;
import org.knowm.xchart.SwingWrapper;
import java.util.List;
import java.util.stream.IntStream;

public class Statify {

    private CategoryChart getBarChart(List<Integer> x_values, List<Float> y_values) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Score Histogram")
                .xAxisTitle("Score")
                .yAxisTitle("Number")
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setLabelsVisible(false);
        chart.getStyler().setPlotGridLinesVisible(false);

        // Series
        chart.addSeries("test 1", x_values, y_values);
        return chart;
    }

    public void getDanceabilityHistogram(List<Float> values) {
        int valuesLen = values.size();
        List<Integer> keys_array = IntStream.range(0, valuesLen).boxed().toList();

        CategoryChart chart = getBarChart(keys_array, values);
        new SwingWrapper<CategoryChart>(chart).displayChart();
    }

    public static void main(String[] args) {
        Statify statify = new Statify();
        statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f));
    }

}

package com.statify;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.XChartPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import javax.swing.JPanel;

public class Statify {

    private CategoryChart getBarChart(List<Float> x_values,
            List<Integer> y_values,
            String title,
            String xAxisTitle,
            String yAxisTitle) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setLabelsVisible(true);
        chart.getStyler().setPlotGridLinesVisible(true);

        // Series
        chart.addSeries("test 1", x_values, y_values);
        return chart;
    }

    public JPanel getDanceabilityHistogram(List<Float> data) {
        // int valuesLen = data.size();
        // List<Integer> keysArray = IntStream.range(0, valuesLen).boxed().toList();

        int binsNum = (int) Math.cbrt(data.size());
        int[] binCounts = new int[binsNum];
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        for (float value : data) {
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }
        float binWidth = (max - min) / binsNum;
        for (float value : data) {
            int binIndex = (int) ((value - min) / binWidth);
            if (binIndex == binsNum) {
                binIndex = binsNum - 1;
            }
            binCounts[binIndex]++;
        }
        List<Float> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < binsNum; i++) {
            float binStart = min + i * binWidth;
            // float binEnd = binStart + binWidth;
            keys.add(binStart);
            values.add(binCounts[i]);
        }
        String title = "Danceability Histogram";
        String xTitle = "Minimal danceability rate";
        String yTitle = "Number of tracks";

        CategoryChart chart = getBarChart(keys, values, title, xTitle, yTitle);
        return new XChartPanel<CategoryChart>(chart);
    }

    public static void main(String[] args) {
        Statify statify = new Statify();
        statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f));
    }

}

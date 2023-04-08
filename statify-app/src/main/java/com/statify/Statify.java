package com.statify;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import javax.swing.JPanel;

public class Statify {

    private CategoryChart getBarChart(List<Double> x_values,
            List<Double> y_values,
            String title,
            String xAxisTitle,
            String yAxisTitle) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .theme(ChartTheme.GGPlot2)
                .title(title)
                .xAxisTitle(xAxisTitle)
                .yAxisTitle(yAxisTitle)
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setLabelsVisible(true);
        chart.getStyler().setPlotGridLinesVisible(true);

        // Series
        try {
            chart.addSeries("playlists", x_values, y_values);
            chart.getStyler().setAvailableSpaceFill(.96);

            return chart;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e);
            chart.addSeries("Error", Arrays.asList(0.0f), Arrays.asList(0));
            return chart;
        }
    }

    public JPanel getDanceabilityHistogram(List<Float> data, int playlists_num) {
        int binsNum = (int) Math.cbrt(data.size());
        String title = String.format("Danceability Histogram of your %o playlists", playlists_num);
        String xTitle = "Mean";
        String yTitle = "Tracks count";

        Histogram histogram = new Histogram(data, binsNum);
        List<Double> ydata = histogram.getyAxisData();
        List<Double> xdata = histogram.getxAxisData();

        for (int i = 0; i < xdata.size(); i++) {
            double newValue = (double) Math.round(xdata.get(i) * 100d) / 100d;
            xdata.set(i, newValue);
        }
        CategoryChart chart = getBarChart(xdata, ydata, title, xTitle, yTitle);

        return new XChartPanel<CategoryChart>(chart);
    }

    public static void main(String[] args) {
        Statify statify = new Statify();
        statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f), 2);
    }

}

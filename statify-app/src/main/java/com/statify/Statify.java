package com.statify;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JFrame;

import java.util.Dictionary;
import java.util.Hashtable;


public class Statify {

    private CategoryChart getBarChart(List<Double> x_values,
            List<Double> y_values,
            String title,
            String seriesName,
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
            chart.addSeries(seriesName, x_values, y_values);
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
        String seriesName = "Tracks in your playlists";
        List<Double> ydata = new ArrayList<>();
        List<Double> xdata = new ArrayList<>();
        try {
            Histogram histogram = new Histogram(data, binsNum);
            ydata = histogram.getyAxisData();
            xdata = histogram.getxAxisData();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        for (int i = 0; i < xdata.size(); i++) {
            double newValue = (double) Math.round(xdata.get(i) * 100d) / 100d;
            xdata.set(i, newValue);
        }
        CategoryChart chart = getBarChart(xdata, ydata, title, seriesName, xTitle, yTitle);

        return new XChartPanel<CategoryChart>(chart);
    }

    public JPanel createTopTracksPanel(int tracksNum, List<Dictionary<String, String>> data) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tracksNum+1, 3, 10, 10)); // n+1 rows, 2 columns, 10px between each row and ech column
        JLabel mainPositionLabel = new JLabel("Position");
            panel.add(mainPositionLabel);
        JLabel mainTitleLabel = new JLabel("Title");
            panel.add(mainTitleLabel);
        JLabel mainArtistLabel = new JLabel("Artist");
            panel.add(mainArtistLabel);
        JLabel mainAlbumLabel = new JLabel("Album");
            panel.add(mainAlbumLabel);
        // Adding 10 string fields to panel
        for (int i = 0; i < tracksNum; i++) {
            JLabel positionLabel = new JLabel(Integer.toString(i+1));
            panel.add(positionLabel);

            JLabel titleLabel = new JLabel(data.get(i).get("name"));
            panel.add(titleLabel);

            JLabel artistLabel = new JLabel(data.get(i).get("artist"));
            panel.add(artistLabel);

            JLabel albumLabel = new JLabel(data.get(i).get("album"));
            panel.add(albumLabel);
        }

        return panel;
    }


    public static void main(String[] args) {
        Statify statify = new Statify();
        // statify.getDanceabilityHistogram(Arrays.asList(1.4f, 5.0f, 6.4f), 2);
        JFrame frame = new JFrame("Song Form");
        List<Dictionary<String, String>> dictionaryList = new ArrayList<>();

        Dictionary<String, String> dictionary1 = new Hashtable<>();
        dictionary1.put("name", "hello");
        dictionary1.put("artist", "adele");
        dictionary1.put("album", "waheteverr");
        dictionaryList.add(dictionary1);

        Dictionary<String, String> dictionary2 = new Hashtable<>();
        dictionary2.put("name", "bebe");
        dictionary2.put("artist", "adadadasele");
        dictionary2.put("album", "verr");
        dictionaryList.add(dictionary2);

        Dictionary<String, String> dictionary3 = new Hashtable<>();
        dictionary3.put("name", "sbglo");
        dictionary3.put("artist", "tet");
        dictionary3.put("album", "wahefgsdteverr");
        dictionaryList.add(dictionary3);

        frame.add(statify.createTopTracksPanel(3, dictionaryList));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

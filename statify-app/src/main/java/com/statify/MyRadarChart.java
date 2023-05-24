package com.statify;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.style.RadarStyler;
import org.knowm.xchart.XChartPanel;
import java.awt.Color;
import java.awt.Font;

public class MyRadarChart {
    public static void main(String[] args) {

        RadarChartBuilder radarChartBuilder = new RadarChartBuilder();
        RadarChart radarChart = radarChartBuilder.build();
        double[] topTrackData = { 0.9, 0.3, 0.8, 0.35345 };
        double[] albumsData = { 0.8, 0.6, 0.5, 0.999 };

        String[] radiiLabels = { "feature", "mood", "daceability", "acousticness" };
        radarChart.setRadiiLabels(radiiLabels);
        radarChart.addSeries("topTracks", topTrackData);
        radarChart.addSeries("Albums", albumsData);
        RadarStyler radarStyler = radarChart.getStyler();
        Color[] colorSeries = { new Color(255, 205, 178, 255), new Color(229, 152,
                155, 150),
                new Color(229, 152, 155) };
        radarStyler.setSeriesColors(colorSeries);
        Font monsterratFont = new Font("Noto Sans CJK JP", Font.PLAIN, 25);
        radarStyler.setBaseFont(monsterratFont);
        radarStyler.setRadiiTitleFont(monsterratFont);
        radarStyler.setLegendFont(monsterratFont);
        XChartPanel<RadarChart> chartPanel = new XChartPanel<RadarChart>(radarChart);
        TopLevelWindow.createChartFrame(chartPanel);
    };

}

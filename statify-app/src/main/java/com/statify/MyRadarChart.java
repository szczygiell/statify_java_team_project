package com.statify;

import java.awt.Color;
import java.awt.Font;

import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.RadarStyler;

public class MyRadarChart {
    public static void main(String[] args) {

        RadarChartBuilder radarChartBuilder = new RadarChartBuilder();
        RadarChart radarChart = radarChartBuilder.build();
        double[] topTrackData = { 0.7702501, 0.17822114, 0.10678018 };
        double[] albumsData = { 0.6341601, 0.18175901, 0.3466027 };

        String[] radiiLabels = { "feature", "mood", "daceability" };
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
